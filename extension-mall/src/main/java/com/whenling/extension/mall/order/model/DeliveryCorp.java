package com.whenling.extension.mall.order.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "xx_delivery_corp")
public class DeliveryCorp extends SortEntity<User, Long> {

	private static final long serialVersionUID = -3801676722604675533L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 网址 */
	@Length(max = 200)
	private String url;

	/** 代码 */
	@Length(max = 200)
	private String code;

	/** 配送方式 */
	@OneToMany(mappedBy = "defaultDeliveryCorp", fetch = FetchType.LAZY)
	private Set<ShippingMethod> shippingMethods = new HashSet<ShippingMethod>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<ShippingMethod> getShippingMethods() {
		return shippingMethods;
	}

	public void setShippingMethods(Set<ShippingMethod> shippingMethods) {
		this.shippingMethods = shippingMethods;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<ShippingMethod> shippingMethods = getShippingMethods();
		if (shippingMethods != null) {
			for (ShippingMethod shippingMethod : shippingMethods) {
				shippingMethod.setDefaultDeliveryCorp(null);
			}
		}
	}
}
