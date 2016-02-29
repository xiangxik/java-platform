package com.whenling.centralize.web.role.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.whenling.centralize.model.Role;
import com.whenling.module.domain.model.EntityVo;

public class RoleVo extends EntityVo<Long> {

	@NotBlank
	@Length(max = 50)
	private String name;

	@NotBlank
	@Length(max = 50)
	private String code;

	public Role applyTo(Role role) {
		role.setName(name);
		role.setCode(code);
		return role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
