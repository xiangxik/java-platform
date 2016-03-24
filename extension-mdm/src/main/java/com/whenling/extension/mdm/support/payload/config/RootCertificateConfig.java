package com.whenling.extension.mdm.support.payload.config;

import com.whenling.extension.mdm.support.payload.TextData;

public class RootCertificateConfig extends ConfigPayload {

	private String payloadCertificateFileName;
	private TextData payloadContent;

	public RootCertificateConfig(String payloadCertificateFileName, String payloadContent) {
		super("com.apple.security.root");

		this.payloadCertificateFileName = payloadCertificateFileName;
		this.payloadContent = new TextData(payloadContent);
	}

	public String getPayloadCertificateFileName() {
		return payloadCertificateFileName;
	}

	public void setPayloadCertificateFileName(String payloadCertificateFileName) {
		this.payloadCertificateFileName = payloadCertificateFileName;
	}

	public TextData getPayloadContent() {
		return payloadContent;
	}

	public void setPayloadContent(TextData payloadContent) {
		this.payloadContent = payloadContent;
	}

}
