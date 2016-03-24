package com.whenling.extension.mdm.support.payload.command;

public class DeviceLock extends CommandPayload {

	public DeviceLock() {
		super("DeviceLock");

	}

	private String PIN;
	private String message;
	private String phoneNumber;

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
