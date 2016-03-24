package com.whenling.extension.mdm.support.payload.command;

public class ManagedApplicationConfiguration extends CommandPayload {

	private String[] identifiers;
	
	public ManagedApplicationConfiguration() {
		super("ManagedApplicationConfiguration");
	}

	public String[] getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(String[] identifiers) {
		this.identifiers = identifiers;
	}

}
