package com.whenling.extension.mdm.support.payload.command;

import com.whenling.extension.mdm.support.payload.TextData;

public class ClearPasscode extends CommandPayload {

	private TextData unlockToken;

	public ClearPasscode(TextData unlockToken) {
		super("ClearPasscode");

		this.unlockToken = unlockToken;
	}

	public TextData getUnlockToken() {
		return unlockToken;
	}

	public void setUnlockToken(TextData unlockToken) {
		this.unlockToken = unlockToken;
	}

}
