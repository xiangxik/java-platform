package com.whenling.extension.mall.product.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Embeddable
public class ProductImage implements Serializable, Comparable<ProductImage> {

	private static final long serialVersionUID = -5183512534977745702L;

	/** 标题 */
	@Length(max = 200)
	private String title;

	/** 原图片 */
	private String source;

	/** 大图片 */
	private String large;

	/** 中图片 */
	private String medium;

	/** 缩略图 */
	private String thumbnail;

	/** 排序 */
	private Integer sortNo;

	/** 文件 */
	@Transient
	private MultipartFile file;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public boolean isEmpty() {
		return (getFile() == null || getFile().isEmpty())
				&& (StringUtils.isEmpty(getSource()) || StringUtils.isEmpty(getLarge())
						|| StringUtils.isEmpty(getMedium()) || StringUtils.isEmpty(getThumbnail()));
	}

	@Override
	public int compareTo(ProductImage o) {
		return new CompareToBuilder().append(getSortNo(), o.getSortNo()).toComparison();
	}

}
