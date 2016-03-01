package com.whenling.extension.mall.product.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "mall_parameter_group")
public class ParameterGroup extends SortEntity<User, Long> {

	private static final long serialVersionUID = 5280025736010774707L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, length = 200)
	private String name;

	/** 绑定分类 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private ProductCategory productCategory;

	/** 参数 */
	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "parameterGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("order asc")
	private List<Parameter> parameters = new ArrayList<Parameter>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

}
