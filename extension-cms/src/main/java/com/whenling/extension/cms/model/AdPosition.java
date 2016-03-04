package com.whenling.extension.cms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.BizEntity;

/**
 * 广告位
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月2日 下午4:17:01
 */
@Entity
@Table(name = "cms_ad_position")
public class AdPosition extends BizEntity<User, Long> {

	private static final long serialVersionUID = 2497239650086582736L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 宽度 */
	@NotNull
	@Min(1)
	@Column(nullable = false)
	private Integer width;

	/** 高度 */
	@NotNull
	@Min(1)
	@Column(nullable = false)
	private Integer height;

	/** 描述 */
	@Length(max = 200)
	private String description;

	/** 模板 */
	@NotEmpty
	@Lob
	@Column(nullable = false)
	private String template;

	/** 广告 */
	@OneToMany(mappedBy = "adPosition", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@OrderBy("sortNo asc")
	private Set<Ad> ads = new HashSet<Ad>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Set<Ad> getAds() {
		return ads;
	}

	public void setAds(Set<Ad> ads) {
		this.ads = ads;
	}
}
