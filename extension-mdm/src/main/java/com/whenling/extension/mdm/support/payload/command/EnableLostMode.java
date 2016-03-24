package com.whenling.extension.mdm.support.payload.command;

public class EnableLostMode extends CommandPayload {

	private String message;
	private String phoneNumber;
	private String footnote;

	public EnableLostMode() {
		super("EnableLostMode");
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

	public String getFootnote() {
		return footnote;
	}

	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

}
