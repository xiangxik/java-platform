package com.whenling.core.support.entity;

public interface Lockedable {
	public boolean getLocked();

	public void setLocked(boolean locked);

	/**
	 * 标识为已锁定
	 */
	public void markLocked();
}
