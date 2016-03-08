package com.whenling.extension.mall.product.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.whenling.module.domain.model.BaseEntity;

/**
 * 货品
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月2日 下午4:48:34
 */
@Entity
@Table(name = "mall_goods")
public class Goods extends BaseEntity<Long> {

	private static final long serialVersionUID = -1816731963854654942L;

	/** 商品 */
	@OneToMany(mappedBy = "goods", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<Product> products = new HashSet<Product>();

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	/**
	 * 获取规格值
	 * 
	 * @return 规格值
	 */
	@Transient
	public Set<SpecificationValue> getSpecificationValues() {
		Set<SpecificationValue> specificationValues = new HashSet<SpecificationValue>();
		if (getProducts() != null) {
			for (Product product : getProducts()) {
				specificationValues.addAll(product.getSpecificationValues());
			}
		}
		return specificationValues;
	}

}
