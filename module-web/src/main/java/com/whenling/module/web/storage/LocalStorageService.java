package com.whenling.module.web.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;

@Component
public class LocalStorageService implements StorageService {

	@Autowired
	private ServletContext servletContext;

	@Override
	public void store(String path, MultipartFile multipartFile) throws IOException {
		File destFile = new File(servletContext.getRealPath(path));
		Files.createParentDirs(destFile);
		Files.write(multipartFile.getBytes(), destFile);
		// multipartFile.transferTo(destFile);
	}

	@Override
	public void store(String path, File file) throws IOException {
		File destFile = new File(servletContext.getRealPath(path));
		Files.createParentDirs(destFile);
		Files.copy(file, destFile);
	}

	@Override
	public void store(String path, InputStream inputStream) throws IOException {
		File destFile = new File(servletContext.getRealPath(path));
		Files.createParentDirs(destFile);
		Files.write(IOUtils.toByteArray(inputStream), destFile);
	}

	@Override
	public File getFile(String path) {
		return new File(servletContext.getRealPath(path));
	}

	@Override
	public String getUrl(String path) {
		return servletContext.getContextPath() + path;
	}

	@Override
	public List<FileInfo> browser(String path) {
		return null;
	}

}
