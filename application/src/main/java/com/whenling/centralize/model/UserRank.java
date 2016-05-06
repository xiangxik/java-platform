package com.whenling.centralize.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import com.whenling.module.domain.model.SortEntity;

@Entity
@Table(name = "sys_user_rank")
public class UserRank extends SortEntity<User, Long> {

	private static final long serialVersionUID = 655694670265736317L;

	@Column(nullable = false, unique = true, length = 50)
	private String code;

	@Column(nullable = false, length = 200)
	private String name;

	private Boolean isDefault;

	/** 属性 */
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "sys_user_rank_attribute")
	@MapKeyColumn(name = "name", length = 100)
	private Map<String, String> attributes = new HashMap<String, String>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

}
