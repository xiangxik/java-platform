package com.whenling.extension.mdm.support.payload;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BasePayload implements Payload {
	protected static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	private static AtomicLong unique_num = new AtomicLong(0l);

	protected static long uniqueNum() {
		return unique_num.decrementAndGet();
	}

}
