package com.whenling.extension.mdm.support.payload;

public class CustomerCertificate extends BasePayload {

	private String pushCertRequestCSR;
	private String pushCertCertificateChain;
	private String pushCertSignature;

	public CustomerCertificate(String pushCertRequestCSR, String pushCertCertificateChain, String pushCertSignature) {
		this.pushCertRequestCSR = pushCertRequestCSR;
		this.pushCertCertificateChain = pushCertCertificateChain;
		this.pushCertSignature = pushCertSignature;
	}

	public String getPushCertRequestCSR() {
		return pushCertRequestCSR;
	}

	public String getPushCertCertificateChain() {
		return pushCertCertificateChain;
	}

	public String getPushCertSignature() {
		return pushCertSignature;
	}

	public void setPushCertRequestCSR(String pushCertRequestCSR) {
		this.pushCertRequestCSR = pushCertRequestCSR;
	}

	public void setPushCertCertificateChain(String pushCertCertificateChain) {
		this.pushCertCertificateChain = pushCertCertificateChain;
	}

	public void setPushCertSignature(String pushCertSignature) {
		this.pushCertSignature = pushCertSignature;
	}
}
