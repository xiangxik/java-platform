package com.whenling.extension.mall.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "mall_parameter")
public class Parameter extends SortEntity<User, Long> {

	private static final long serialVersionUID = -8409630476592372781L;

	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, length = 200)
	private String name;

	/** 参数组 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private ParameterGroup parameterGroup;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParameterGroup getParameterGroup() {
		return parameterGroup;
	}

	public void setParameterGroup(ParameterGroup parameterGroup) {
		this.parameterGroup = parameterGroup;
	}

}
