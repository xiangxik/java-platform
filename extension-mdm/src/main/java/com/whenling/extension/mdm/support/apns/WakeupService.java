package com.whenling.extension.mdm.support.apns;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.whenling.extension.mdm.model.Device;

@Component
public class WakeupService {

	private ThreadPoolExecutor pool;
	private Resource customerP12;

	public WakeupService() {
		pool = new ThreadPoolExecutor(2, Integer.MAX_VALUE, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

		customerP12 = new ClassPathResource("/cert/customer.p12");
	}

	public void revive(Device... devices) {
		for (Device device : devices) {
			try {
				WakeupTask wakeupTask = new WakeupTask(customerP12.getInputStream(), "asd123", device.getPushMagic(),
						device.getToken());
				if (!pool.getQueue().contains(wakeupTask)) {
					pool.execute(wakeupTask);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	static class WakeupTask implements Runnable {

		private InputStream certStream;
		private String password;

		private String pushMagic;
		private String token;

		public WakeupTask(InputStream certStream, String password, String pushMagic, String token) {
			this.certStream = certStream;
			this.password = password;
			this.pushMagic = pushMagic;
			this.token = token;
		}

		@Override
		public void run() {
			ApnsService apnsService = APNS.newService().withCert(certStream, password).withProductionDestination()
					.build();
			String wakeupPayload = APNS.newPayload().mdm(pushMagic).build();
			apnsService.push(new String(Hex.encodeHex(Base64.decodeBase64(token))), wakeupPayload);
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(pushMagic).append(token).build();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (obj == this) {
				return true;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}

			WakeupTask other = (WakeupTask) obj;
			return new EqualsBuilder().append(pushMagic, other.pushMagic).append(token, other.token).build();
		}

	}
}
