package com.whenling.extension.mdm.support.payload.command;

import com.whenling.extension.mdm.support.payload.BasePayload;

public abstract class CommandPayload extends BasePayload {

	private String requestType;

	public CommandPayload(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestType() {
		return requestType;
	}

	public boolean neverReturnNotNow() {
		return true;
	}

}
