package com.whenling.module.domain.model;

import java.util.List;
import java.util.Set;

/**
 * 树
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:02:05
 * @param <T>
 */
public interface Tree<T extends Treeable<T>> {

	List<Node<T>> getRoots();

	Set<T> getChecked();

	void setChecked(Set<T> checked);

	boolean isCheckable();

	boolean isExpandAll();

	void makeCheckable();

	void makeExpandAll();
}
