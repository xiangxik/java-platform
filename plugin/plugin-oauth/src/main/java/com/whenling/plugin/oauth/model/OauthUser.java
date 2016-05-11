package com.whenling.plugin.oauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "plugin_oauth_user")
public class OauthUser extends BaseEntity<Long> {

	private static final long serialVersionUID = -3724865400156658638L;

	@Column(nullable = false, updatable = false, length = 100)
	private String oauthPluginId;

	private String userId;

	@Column(length = 200)
	private String username;

	@Column(length = 200)
	private String name;

	@Column(length = 500)
	private String avatarUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	public String getOauthPluginId() {
		return oauthPluginId;
	}

	public void setOauthPluginId(String oauthPluginId) {
		this.oauthPluginId = oauthPluginId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
