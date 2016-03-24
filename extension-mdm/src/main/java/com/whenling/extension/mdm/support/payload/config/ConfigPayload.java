package com.whenling.extension.mdm.support.payload.config;

import com.whenling.extension.mdm.support.payload.BasePayload;

public class ConfigPayload extends BasePayload {

	private String payloadType;

	private int payloadVersion = 1;

	private String payloadUUID = randomUUID();

	private String payloadOrganization = "whenling";

	private String payloadIdentifier = this.getClass().getName() + uniqueNum();

	private String payloadDisplayName = this.getClass().getName();

	private String payloadDescription;

	public ConfigPayload(String payloadType) {
		this.payloadType = payloadType;
	}

	public String getPayloadType() {
		return payloadType;
	}

	public int getPayloadVersion() {
		return payloadVersion;
	}

	public String getPayloadUUID() {
		return payloadUUID;
	}

	public String getPayloadOrganization() {
		return payloadOrganization;
	}

	public String getPayloadIdentifier() {
		return payloadIdentifier;
	}

	public String getPayloadDisplayName() {
		return payloadDisplayName;
	}

	public void setPayloadType(String payloadType) {
		this.payloadType = payloadType;
	}

	public void setPayloadVersion(int payloadVersion) {
		this.payloadVersion = payloadVersion;
	}

	public void setPayloadUUID(String payloadUUID) {
		this.payloadUUID = payloadUUID;
	}

	public void setPayloadOrganization(String payloadOrganization) {
		this.payloadOrganization = payloadOrganization;
	}

	public void setPayloadIdentifier(String payloadIdentifier) {
		this.payloadIdentifier = payloadIdentifier;
	}

	public void setPayloadDisplayName(String payloadDisplayName) {
		this.payloadDisplayName = payloadDisplayName;
	}

	public String getPayloadDescription() {
		return payloadDescription;
	}

	public void setPayloadDescription(String payloadDescription) {
		this.payloadDescription = payloadDescription;
	}

}
