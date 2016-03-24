package com.whenling.extension.mdm.support.payload.command;

public class ManagedApplicationFeedback extends CommandPayload {

	private String[] identifiers;

	private Boolean deleteFeedback;

	public ManagedApplicationFeedback() {
		super("ManagedApplicationFeedback");
	}

	public String[] getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(String[] identifiers) {
		this.identifiers = identifiers;
	}

	public Boolean getDeleteFeedback() {
		return deleteFeedback;
	}

	public void setDeleteFeedback(Boolean deleteFeedback) {
		this.deleteFeedback = deleteFeedback;
	}

}
