package com.whenling.extension.mall.product.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "mall_attribute")
public class Attribute extends SortEntity<User, Long> {
	private static final long serialVersionUID = -2044042263776941889L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(length = 200, nullable = false)
	private String name;

	/** 属性序号 */
	@Column(nullable = false, updatable = false)
	private Integer propertyIndex;

	/** 绑定分类 */
	@NotNull(groups = Save.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private ProductCategory productCategory;

	/** 可选项 */
	@NotEmpty
	@ElementCollection
	@CollectionTable(name = "mall_attribute_option")
	private List<String> options = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPropertyIndex() {
		return propertyIndex;
	}

	public void setPropertyIndex(Integer propertyIndex) {
		this.propertyIndex = propertyIndex;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}
}
