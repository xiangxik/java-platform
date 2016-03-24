package com.whenling.extension.mdm.support.payload.config;

import com.whenling.extension.mdm.support.payload.TextData;

public class Pkcs12CertificateConfig extends ConfigPayload {

	private String payloadCertificateFileName;
	private TextData payloadContent;
	private String password;

	public Pkcs12CertificateConfig(String payloadCertificateFileName, String payloadContent, String password) {
		super("com.apple.security.pkcs12");
		this.payloadCertificateFileName = payloadCertificateFileName;
		this.payloadContent = new TextData(payloadContent);
		this.password = password;
	}

	public String getPayloadCertificateFileName() {
		return payloadCertificateFileName;
	}

	public TextData getPayloadContent() {
		return payloadContent;
	}

	public String getPassword() {
		return password;
	}

	public void setPayloadCertificateFileName(String payloadCertificateFileName) {
		this.payloadCertificateFileName = payloadCertificateFileName;
	}

	public void setPayloadContent(TextData payloadContent) {
		this.payloadContent = payloadContent;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
