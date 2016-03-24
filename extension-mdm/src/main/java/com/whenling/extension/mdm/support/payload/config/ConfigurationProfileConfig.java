package com.whenling.extension.mdm.support.payload.config;

import java.util.Date;

import com.whenling.extension.mdm.support.payload.BasePayload;

public class ConfigurationProfileConfig extends ConfigPayload {

	private ConsentText consentText;
	private Boolean hasRemovalPasscode;
	private Boolean isEncrypted;

	private ConfigPayload[] payloadContent;
	private Boolean payloadRemovalDisallowed;
	private Boolean payloadScope;
	private Date removalDate;
	private Double durationUntilRemoval;

	public ConfigurationProfileConfig() {
		super("Configuration");
	}

	public ConsentText getConsentText() {
		return consentText;
	}

	public void setConsentText(ConsentText consentText) {
		this.consentText = consentText;
	}

	public Boolean getHasRemovalPasscode() {
		return hasRemovalPasscode;
	}

	public void setHasRemovalPasscode(Boolean hasRemovalPasscode) {
		this.hasRemovalPasscode = hasRemovalPasscode;
	}

	public Boolean getIsEncrypted() {
		return isEncrypted;
	}

	public void setIsEncrypted(Boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
	}

	public ConfigPayload[] getPayloadContent() {
		return payloadContent;
	}

	public void setPayloadContent(ConfigPayload[] payloadContent) {
		this.payloadContent = payloadContent;
	}

	public Boolean getPayloadRemovalDisallowed() {
		return payloadRemovalDisallowed;
	}

	public void setPayloadRemovalDisallowed(Boolean payloadRemovalDisallowed) {
		this.payloadRemovalDisallowed = payloadRemovalDisallowed;
	}

	public Boolean getPayloadScope() {
		return payloadScope;
	}

	public void setPayloadScope(Boolean payloadScope) {
		this.payloadScope = payloadScope;
	}

	public Date getRemovalDate() {
		return removalDate;
	}

	public void setRemovalDate(Date removalDate) {
		this.removalDate = removalDate;
	}

	public Double getDurationUntilRemoval() {
		return durationUntilRemoval;
	}

	public void setDurationUntilRemoval(Double durationUntilRemoval) {
		this.durationUntilRemoval = durationUntilRemoval;
	}

	public static class ConsentText extends BasePayload {
		private String _default;

		public ConsentText(String _default) {
			this._default = _default;
		}

		public String getDefault() {
			return _default;
		}

		public void setDefault(String _default) {
			this._default = _default;
		}
	}

}
