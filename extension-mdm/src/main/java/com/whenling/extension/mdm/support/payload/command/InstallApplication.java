package com.whenling.extension.mdm.support.payload.command;

import java.util.Map;

import com.whenling.extension.mdm.support.payload.BasePayload;

public class InstallApplication extends CommandPayload {

	private Integer iTunesStoreID;
	private String identifier;
	private Options options;
	private String manifestURL;
	private Integer managementFlags;

	private Map<String, Object> configuration;
	private Map<String, Object> attributes;

	private String changeManagementState;

	public InstallApplication() {
		super("InstallApplication");
	}

	public Integer getiTunesStoreID() {
		return iTunesStoreID;
	}

	public void setiTunesStoreID(Integer iTunesStoreID) {
		this.iTunesStoreID = iTunesStoreID;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public String getManifestURL() {
		return manifestURL;
	}

	public void setManifestURL(String manifestURL) {
		this.manifestURL = manifestURL;
	}

	public Integer getManagementFlags() {
		return managementFlags;
	}

	public void setManagementFlags(Integer managementFlags) {
		this.managementFlags = managementFlags;
	}

	public Map<String, Object> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Map<String, Object> configuration) {
		this.configuration = configuration;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getChangeManagementState() {
		return changeManagementState;
	}

	public void setChangeManagementState(String changeManagementState) {
		this.changeManagementState = changeManagementState;
	}

	public static class Options extends BasePayload {
		private Boolean notManaged;
		private Integer purchaseMethod;

		public Boolean getNotManaged() {
			return notManaged;
		}

		public void setNotManaged(Boolean notManaged) {
			this.notManaged = notManaged;
		}

		public Integer getPurchaseMethod() {
			return purchaseMethod;
		}

		public void setPurchaseMethod(Integer purchaseMethod) {
			this.purchaseMethod = purchaseMethod;
		}

	}
}
