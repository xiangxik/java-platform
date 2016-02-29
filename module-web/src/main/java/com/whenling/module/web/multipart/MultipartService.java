package com.whenling.module.web.multipart;

import java.io.IOException;

import javax.servlet.http.Part;

import org.springframework.web.multipart.MultipartFile;

import com.whenling.module.web.multipart.model.FileEntity;

public interface MultipartService {
	public FileEntity upload(MultipartFile multipartFile) throws IOException;

	public FileEntity upload(Part part) throws IOException;

}
