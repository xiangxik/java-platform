package com.whenling.core.support.repo.exception;

public class DeleteLockedEntityException extends RuntimeException {

	private static final long serialVersionUID = -3883345474360984375L;

	public DeleteLockedEntityException() {
		super("不能删除已锁定的对象");
	}
}
