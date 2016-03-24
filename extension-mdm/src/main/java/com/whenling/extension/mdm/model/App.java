package com.whenling.extension.mdm.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.converter.StringArrayConverter;
import com.whenling.module.domain.model.BizEntity;

@Entity
@Table(name = "mdm_app")
public class App extends BizEntity<User, Long> {

	private static final long serialVersionUID = -8007184313857410832L;

	@Column(nullable = false, length = 200)
	private String name;

	@Column(length = 500)
	private String icon60;

	@Column(length = 500)
	private String icon100;

	@Column(length = 500)
	private String icon512;

	@Convert(converter = StringArrayConverter.class)
	private String[] screenshots;

	@Convert(converter = StringArrayConverter.class)
	private String[] supportedDevices;

	@Column(nullable = false, length = 200)
	private String bundleId;

	private Integer trackId;

	@Column(nullable = false, length = 200)
	private String version;

	private long fileSizeBytes = 0l;

	@Lob
	private String description;

	private boolean gameCenterEnabled = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon60() {
		return icon60;
	}

	public void setIcon60(String icon60) {
		this.icon60 = icon60;
	}

	public String getIcon100() {
		return icon100;
	}

	public void setIcon100(String icon100) {
		this.icon100 = icon100;
	}

	public String getIcon512() {
		return icon512;
	}

	public void setIcon512(String icon512) {
		this.icon512 = icon512;
	}

	public String[] getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(String[] screenshots) {
		this.screenshots = screenshots;
	}

	public String[] getSupportedDevices() {
		return supportedDevices;
	}

	public void setSupportedDevices(String[] supportedDevices) {
		this.supportedDevices = supportedDevices;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public Integer getTrackId() {
		return trackId;
	}

	public void setTrackId(Integer trackId) {
		this.trackId = trackId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getFileSizeBytes() {
		return fileSizeBytes;
	}

	public void setFileSizeBytes(long fileSizeBytes) {
		this.fileSizeBytes = fileSizeBytes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isGameCenterEnabled() {
		return gameCenterEnabled;
	}

	public void setGameCenterEnabled(boolean gameCenterEnabled) {
		this.gameCenterEnabled = gameCenterEnabled;
	}

}
