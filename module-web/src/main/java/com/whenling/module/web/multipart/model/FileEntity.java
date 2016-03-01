package com.whenling.module.web.multipart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.whenling.module.domain.model.BizEntity;
import com.whenling.module.domain.model.User;

/**
 * 文件实体
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:09:13
 */
@Entity
@Table(name = "sys_file_entity")
public class FileEntity extends BizEntity<User, Long> {

	private static final long serialVersionUID = 3355988161641862644L;

	@Column(nullable = false, length = 100)
	private String filename;

	@Column(length = 50)
	private String contentType;

	@Column(nullable = false, length = 100)
	private String storePath;

	private long fileSize = 0l;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

}
