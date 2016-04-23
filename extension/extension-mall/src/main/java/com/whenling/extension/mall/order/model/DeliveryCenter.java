package com.whenling.extension.mall.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.Area;
import com.whenling.centralize.model.Areable;
import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.BizEntity;

/**
 * 发货点
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月2日 下午4:19:51
 */
@Entity
@Table(name = "mall_delivery_center")
public class DeliveryCenter extends BizEntity<User, Long> implements Areable {

	private static final long serialVersionUID = 551037005088030336L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 联系人 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String contact;

	/** 地区名称 */
	@Column(nullable = false)
	private String areaName;

	/** 地址 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String address;

	/** 邮编 */
	@Length(max = 200)
	private String zipCode;

	/** 电话 */
	@Length(max = 200)
	private String phone;

	/** 手机 */
	@Length(max = 200)
	private String mobile;

	/** 备注 */
	@Length(max = 200)
	private String memo;

	/** 是否默认 */
	@NotNull
	@Column(nullable = false)
	private Boolean isDefault;

	/** 地区 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Area area;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}
