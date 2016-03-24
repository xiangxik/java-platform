package com.whenling.extension.mdm.support.payload.command;

public class ManagedApplicationAttributes extends CommandPayload {

	private String[] identifiers;
	
	public ManagedApplicationAttributes() {
		super("ManagedApplicationAttributes");
	}

	public String[] getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(String[] identifiers) {
		this.identifiers = identifiers;
	}

}
