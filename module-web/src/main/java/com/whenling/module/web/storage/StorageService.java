package com.whenling.module.web.storage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	/**
	 * 附件存储
	 * 
	 * @param path
	 * @param file
	 * @param contentType
	 */
	void store(String path, MultipartFile multipartFile) throws IOException;

	/**
	 * 文件存储
	 * 
	 * @param path
	 * @param file
	 * @throws IOException
	 */
	void store(String path, File file) throws IOException;

	/**
	 * 根据路径获得文件
	 * 
	 * @param path
	 * @return
	 */
	File getFile(String path);

	/**
	 * 获取访问URL
	 * 
	 * @param path
	 * @return
	 */
	String getUrl(String path);

	/**
	 * 文件浏览
	 * 
	 * @param path
	 * @return
	 */
	List<FileInfo> browser(String path);
}
