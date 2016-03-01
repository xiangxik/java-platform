package com.whenling.module.web.multipart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Files;

/**
 * 附件服务实现类
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:09:55
 */
@Component
public class MultipartServiceImpl implements MultipartService {

	@Value("${multipart.saveDir?:/upload}")
	private String saveDir;

	@Autowired
	private ServletContext servletContext;

	@Override
	public String upload(MultipartFile multipartFile) throws IOException {
		Assert.notNull(multipartFile);

		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		String storePath = saveDir + "/" + randomString() + (Strings.isNullOrEmpty(extension) ? "" : "." + extension);
		store(multipartFile.getInputStream(), storePath);
		return servletContext.getContextPath() + storePath;

	}

	@Override
	public String upload(Part part) throws IOException {
		Assert.notNull(part);

		String extension = FilenameUtils.getExtension(part.getSubmittedFileName());
		String storePath = saveDir + "/" + randomString() + (Strings.isNullOrEmpty(extension) ? "" : "." + extension);
		store(part.getInputStream(), storePath);

		return servletContext.getContextPath() + storePath;
	}

	protected String randomString() {
		return RandomStringUtils.randomAlphanumeric(20);
	}

	protected void store(InputStream inputStream, String path) {
		File file = new File(servletContext.getRealPath(path));

		OutputStream outputStream = null;
		try {
			Files.createParentDirs(file);
			outputStream = new FileOutputStream(file);
			ByteStreams.copy(inputStream, outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			Closeables.closeQuietly(inputStream);
		}
	}

}
