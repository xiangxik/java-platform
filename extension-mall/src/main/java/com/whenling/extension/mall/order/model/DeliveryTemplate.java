package com.whenling.extension.mall.order.model;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.BizEntity;

public class DeliveryTemplate extends BizEntity<User, Long> {

	private static final long serialVersionUID = -6209123182805019824L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 内容 */
	@NotEmpty
	@Lob
	@Column(nullable = false)
	private String content;

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

	/** 偏移量X */
	@NotNull
	@Column(nullable = false)
	private Integer offsetX;

	/** 偏移量Y */
	@NotNull
	@Column(nullable = false)
	private Integer offsetY;

	/** 背景图 */
	@Length(max = 200)
	private String background;

	/** 是否默认 */
	@NotNull
	@Column(nullable = false)
	private Boolean isDefault;

	/** 备注 */
	@Length(max = 200)
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Integer getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(Integer offsetX) {
		this.offsetX = offsetX;
	}

	public Integer getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(Integer offsetY) {
		this.offsetY = offsetY;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
