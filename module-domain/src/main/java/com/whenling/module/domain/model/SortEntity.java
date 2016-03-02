package com.whenling.module.domain.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SortEntity<U, I extends Serializable> extends BizEntity<U, I> implements Sortable {

	private static final long serialVersionUID = 3634027694647556275L;

	private Integer sortNo;

	@Override
	public Integer getSortNo() {
		return sortNo;
	}

	@Override
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
}
