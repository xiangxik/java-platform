package com.whenling.extension.mdm.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.BizEntity;

@Entity
@Table(name = "mdm_device")
public class Device extends BizEntity<User, Long> {

	private static final long serialVersionUID = -7642327143999141614L;

	private String name;

	private Double availableDeviceCapacity;
	private Double deviceCapacity;
	private String phoneNumber;
	private String SIMCarrierNetwork;

	private String buildVersion;
	private String imei;
	private String OSVersion;
	private String productName;
	private String serialNumber;
	private String pushMagic;

	@Lob
	private String token;

	private String udid;

	@Lob
	private String unlockToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBuildVersion() {
		return buildVersion;
	}

	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}

	public String getOSVersion() {
		return OSVersion;
	}

	public void setOSVersion(String oSVersion) {
		OSVersion = oSVersion;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPushMagic() {
		return pushMagic;
	}

	public void setPushMagic(String pushMagic) {
		this.pushMagic = pushMagic;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUnlockToken() {
		return unlockToken;
	}

	public void setUnlockToken(String unlockToken) {
		this.unlockToken = unlockToken;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public Double getAvailableDeviceCapacity() {
		return availableDeviceCapacity;
	}

	public void setAvailableDeviceCapacity(Double availableDeviceCapacity) {
		this.availableDeviceCapacity = availableDeviceCapacity;
	}

	public Double getDeviceCapacity() {
		return deviceCapacity;
	}

	public void setDeviceCapacity(Double deviceCapacity) {
		this.deviceCapacity = deviceCapacity;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSIMCarrierNetwork() {
		return SIMCarrierNetwork;
	}

	public void setSIMCarrierNetwork(String sIMCarrierNetwork) {
		SIMCarrierNetwork = sIMCarrierNetwork;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
