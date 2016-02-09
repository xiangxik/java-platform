package com.whenling.core.support.integration;

public abstract class Module {

	public abstract void init(Application app, boolean isNew, boolean isUpdate);

	public abstract Integer getVersion();

	public abstract String getAuthor();

	public String getName() {
		return getClass().getSimpleName();
	};

	public String getCode() {
		return getClass().getName();
	}

}
