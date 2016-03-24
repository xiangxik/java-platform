package com.whenling.extension.mdm.support.plist;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.base.Objects;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.thoughtworks.xstream.io.xml.Dom4JReader;
import com.thoughtworks.xstream.io.xml.Dom4JWriter;
import com.whenling.extension.mdm.support.payload.BasePayload;

public class PayloadStream extends XStream {

	public PayloadStream() {
		super(new PayloadDriver());

		registerConverter(new PayloadConverter());

		alias("plist", BasePayload.class, BasePayload.class);
	}

	@Override
	public Object fromXML(String xml) {
		try {
			xml = StringUtils.replace(xml, new String("".getBytes(), "UTF-8"), " ");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return super.fromXML(xml);
	}

	static class PayloadDriver extends Dom4JDriver {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PayloadWriter(out);
		}

		@Override
		public HierarchicalStreamReader createReader(Reader text) {
			try {
				SAXReader reader = new SAXReader();
				reader.setEntityResolver(new EntityResolver() {
					@Override
					public InputSource resolveEntity(String publicId, String systemId)
							throws SAXException, IOException {
						if (Objects.equal(publicId, "-//Apple//DTD PLIST 1.0//EN")
								&& Objects.equal(systemId, "http://www.apple.com/DTDs/PropertyList-1.0.dtd")) {
							InputSource inputSource = new InputSource(systemId);
							inputSource.setPublicId(publicId);
							inputSource.setByteStream(new ClassPathResource(
									"/com/whenling/extension/mdm/support/plist/PropertyList-1.0.dtd").getInputStream());
							inputSource.setEncoding("UTF-8");
							return inputSource;
						}
						return null;
					}
				});
				reader.setValidation(false);

				Document document = reader.read(text);
				return new Dom4JReader(document, getNameCoder());
			} catch (DocumentException e) {
				throw new StreamException(e);
			}
		}
	}

	static class PayloadWriter extends Dom4JWriter {
		private Writer out;

		public PayloadWriter(Writer out) {
			super(DocumentFactory.getInstance());

			this.out = out;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void flush() {
			Document xmlDoc = DocumentFactory.getInstance().createDocument();
			xmlDoc.addDocType("plist", "-//Apple//DTD PLIST 1.0//EN", "http://www.apple.com/DTDs/PropertyList-1.0.dtd");

			for (Element element : (List<Element>) getTopLevelNodes()) {
				element.setName("plist");
				element.addAttribute("version", "1.0");
				xmlDoc.add(element);
			}

			try {
				out.write(xmlDoc.asXML());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
