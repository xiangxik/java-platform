package com.whenling.module.domain.model;

import java.util.List;

/**
 * 树形
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:02:14
 * @param <T>
 */
public interface Treeable<T> extends Sortable {

	public T getParent();

	public void setParent(T parent);

	public List<T> getChildren();

	public void setChildren(List<T> children);
}
