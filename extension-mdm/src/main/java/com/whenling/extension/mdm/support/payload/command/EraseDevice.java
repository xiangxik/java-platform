package com.whenling.extension.mdm.support.payload.command;

public class EraseDevice extends CommandPayload {

	/**
	 * TheFindMyMacPIN.Mustbe6characterslong.Availability:Available
	 * inOSX10.8andlater.
	 */
	private String PIN;

	public EraseDevice() {
		super("EraseDevice");
	}

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

}
