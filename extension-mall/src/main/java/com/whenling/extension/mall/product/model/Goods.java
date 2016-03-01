package com.whenling.extension.mall.product.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "xx_goods")
public class Goods extends BaseEntity<Long> {

	private static final long serialVersionUID = -1816731963854654942L;

	/** 商品 */
	@OneToMany(mappedBy = "goods", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Product> products = new HashSet<Product>();

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
