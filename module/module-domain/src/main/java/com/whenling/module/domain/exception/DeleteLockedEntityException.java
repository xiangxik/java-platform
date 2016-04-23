package com.whenling.module.domain.exception;

/**
 * 删除锁定对象异常
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:50:42
 */
public class DeleteLockedEntityException extends RuntimeException {

	private static final long serialVersionUID = -3883345474360984375L;

	public DeleteLockedEntityException() {
		super("不能删除已锁定的对象");
	}
}
