package com.whenling.extension.mdm.support.payload.config;

import com.whenling.extension.mdm.support.payload.TextData;

public class Pkcs1CertificateConfig extends ConfigPayload {

	private String payloadCertificateFileName;
	private TextData payloadContent;

	public Pkcs1CertificateConfig(String payloadCertificateFileName, String payloadContent) {
		super("com.apple.security.pkcs1");

		this.payloadCertificateFileName = payloadCertificateFileName;
		this.payloadContent = new TextData(payloadContent);
	}

	public String getPayloadCertificateFileName() {
		return payloadCertificateFileName;
	}

	public TextData getPayloadContent() {
		return payloadContent;
	}

	public void setPayloadCertificateFileName(String payloadCertificateFileName) {
		this.payloadCertificateFileName = payloadCertificateFileName;
	}

	public void setPayloadContent(TextData payloadContent) {
		this.payloadContent = payloadContent;
	}

}
