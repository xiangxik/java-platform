package com.whenling.centralize.support.web.extjs.image;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "sys_image")
public class ImageInfo extends SortEntity<User, Long> {

	private static final long serialVersionUID = 3704020796840155701L;

	private String name;

	/** 原图片 */
	private String source;

	/** 缩略图 */
	private String thumbnail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

}
