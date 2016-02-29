package com.whenling.module.domain.model;

public interface Lockedable {
	public boolean getLocked();

	public void setLocked(boolean locked);

	/**
	 * 标识为已锁定
	 */
	public void markLocked();
}
