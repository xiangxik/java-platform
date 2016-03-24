package com.whenling.extension.mdm.support.apns;

import com.whenling.extension.mdm.model.Device;
import com.whenling.extension.mdm.support.payload.command.CommandPayload;

public class CommandContext {

	private String uuid;
	private Device device;
	private CommandPayload payload;
	private CommandCallback callback;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public CommandPayload getPayload() {
		return payload;
	}

	public void setPayload(CommandPayload payload) {
		this.payload = payload;
	}

	public CommandCallback getCallback() {
		return callback;
	}

	public void setCallback(CommandCallback callback) {
		this.callback = callback;
	}

}
