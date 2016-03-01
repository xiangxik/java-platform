package com.whenling.module.web.multipart;

import java.io.IOException;

import javax.servlet.http.Part;

import org.springframework.web.multipart.MultipartFile;

/**
 * 附件服务
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:09:49
 */
public interface MultipartService {
	public String upload(MultipartFile multipartFile) throws IOException;

	public String upload(Part part) throws IOException;

}
