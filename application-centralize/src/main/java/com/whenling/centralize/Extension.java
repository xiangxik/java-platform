package com.whenling.centralize;

public abstract class Extension {

	public abstract void init(Application app, boolean isNew, boolean isUpdate, Integer historyVersion);

	public abstract Integer getVersion();

	public abstract String getAuthor();

	public String getName() {
		return getClass().getSimpleName();
	};

	public String getCode() {
		return getClass().getName();
	}

}
