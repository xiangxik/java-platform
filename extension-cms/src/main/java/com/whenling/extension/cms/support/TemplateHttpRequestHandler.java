package com.whenling.extension.cms.support;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.web.WebRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.google.common.base.Objects;

public class TemplateHttpRequestHandler extends ResourceHttpRequestHandler {
	private static final Log logger = LogFactory.getLog(TemplateHttpRequestHandler.class);

	@Autowired
	private BeetlGroupUtilConfiguration beetlConfig;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Supported methods and required session
		checkRequest(request);

		// Check whether a matching resource exists
		Resource resource = getResource(request);
		if (resource == null) {
			logger.trace("No matching resource found - returning 404");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// Check the resource's media type
		MediaType mediaType = getMediaType(resource);
		if (mediaType != null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Determined media type '" + mediaType + "' for " + resource);
			}

			if (Objects.equal(mediaType, MediaType.TEXT_HTML)) {
				WebRender render = new WebRender(beetlConfig.getGroupTemplate());
				if (resource instanceof ServletContextResource) {
					render.render(((ServletContextResource) resource).getPath(), request, response);
				}

				return;
			}

		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("No media type found for " + resource + " - not sending a content-type header");
			}
		}

		// Header phase
		if (new ServletWebRequest(request, response).checkNotModified(resource.lastModified())) {
			logger.trace("Resource not modified - returning 304");
			return;
		}

		// Apply cache settings, if any
		prepareResponse(response);

		// Content phase
		if (METHOD_HEAD.equals(request.getMethod())) {
			setHeaders(response, resource, mediaType);
			logger.trace("HEAD request - skipping content");
			return;
		}

		if (request.getHeader(HttpHeaders.RANGE) == null) {
			setETagHeader(request, response);
			setHeaders(response, resource, mediaType);
			writeContent(response, resource);
		} else {
			writePartialContent(request, response, resource, mediaType);
		}
	}

}
