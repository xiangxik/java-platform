package com.whenling.extension.mdm.support.certificate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.whenling.extension.mdm.support.openssl.OpenSSLExecutor;
import com.whenling.extension.mdm.support.payload.BasePayload;
import com.whenling.extension.mdm.support.payload.CustomerCertificate;
import com.whenling.extension.mdm.support.plist.PayloadStream;

public class CustomerSignature {

	private OpenSSLExecutor executor;

	public CustomerSignature(File baseDir) {
		executor = new OpenSSLExecutor(baseDir);
	}

	public void generatePrivateKeyFile(String privateKey, String outPrivateKeyFile) {
		executor.exec(String.format("genrsa -des3 -passout pass:%s -out %s 2048", privateKey, outPrivateKeyFile));
	}

	public void generateCsrFile(String inPrivateKeyFile, String outCsrFile, String privateKey) {
		executor.exec(String.format(
				"req -new -key %s -out %s -passin pass:%s -subj \"/CN=whenling.com/OU=whenling/O=whenling/L=GZ/ST=GD/C=CN\"",
				inPrivateKeyFile, outCsrFile, privateKey));
	}

	public void generateDerFile(String inCsrFile, String outDerFile) {
		executor.exec(String.format("req -inform pem -outform der -in %s -out %s", inCsrFile, outDerFile));
	}

	public BasePayload signature(byte[] derBytes, InputStream vendor, String vendorAlias, char[] vendorPassword,
			InputStream mdmPem, InputStream intermediatePem, InputStream rootPem) {

		// 取得vendor的私钥
		PrivateKey vendorPrivateKey = null;
		try {
			KeyStore pkcs12 = KeyStore.getInstance("PKCS12");
			pkcs12.load(vendor, vendorPassword);
			vendorPrivateKey = (PrivateKey) pkcs12.getKey(vendorAlias, vendorPassword);
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException
				| UnrecoverableKeyException e) {
			throw new RuntimeException(e);
		}

		// 对 DER 进行自签名
		byte[] signBytes = null;
		try {
			Signature sign = Signature.getInstance("SHA1WithRSA");
			sign.initSign(vendorPrivateKey);
			sign.update(derBytes);
			signBytes = sign.sign();
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw new RuntimeException(e);
		}

		// 对签名后的内容进行 BASE64 编码
		String certSignature = Base64.encodeBase64String(signBytes);
		String certRequestCSR = Base64.encodeBase64String(derBytes);
		String certCertificateChain = null;
		try {
			certCertificateChain = IOUtils.toString(mdmPem) + IOUtils.toString(intermediatePem)
					+ IOUtils.toString(rootPem);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return new CustomerCertificate(certRequestCSR, certCertificateChain, certSignature);
	}

	public void p12(String privateKey, String inPrivateKeyFile, String inCustomSignatureFile, String outP12File) {
		executor.exec(String.format("pkcs12 -export -in %s -inkey %s -passin pass:%s -passout pass:%s -out %s",
				inCustomSignatureFile, inPrivateKeyFile, privateKey, privateKey, outP12File));
	}

	public static void main(String[] args) {
		CustomerSignature customerSignature = new CustomerSignature(new File("E:/tt"));

		customerSignature.generatePrivateKeyFile("asd123", "customer.key.pem");
		customerSignature.generateCsrFile("customer.key.pem", "customer.csr", "asd123");
		customerSignature.generateDerFile("customer.csr", "customer.der");

		try {
			BasePayload result = customerSignature
					.signature(IOUtils.toByteArray(new FileInputStream(new File("E:/tt/customer.der"))),
							new FileInputStream(new File(
									"E:/java/workspace_mars1/movingcastle/src/main/resources/cert/vendor.p12")),
					"www.whenling.com", "asd123".toCharArray(),

							new FileInputStream(
									new File("E:/java/workspace_mars1/movingcastle/src/main/resources/cert/mdm.pem")),

							new FileInputStream(new File(
									"E:/java/workspace_mars1/movingcastle/src/main/resources/cert/intermediate.pem")),

							new FileInputStream(
									new File("E:/java/workspace_mars1/movingcastle/src/main/resources/cert/root.pem")));
			PayloadStream payloadStream = new PayloadStream();

			String xml = payloadStream.toXML(result);
			System.out.println(xml);

			IOUtils.write(Base64.encodeBase64(xml.getBytes()), new FileOutputStream(new File("E:/tt/signature.plist")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		customerSignature.p12("asd123", "customer.key.pem", "customer_certificate.pem", "customer.p12");
	}
}
