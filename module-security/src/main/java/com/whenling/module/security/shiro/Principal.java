package com.whenling.module.security.shiro;

import java.io.Serializable;

/**
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:08:19
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 6297374459381416557L;

	private Long id;
	private String username;

	public Principal() {
	}

	public Principal(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
