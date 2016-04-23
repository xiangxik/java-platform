package com.whenling.module.domain.model;

import java.util.Comparator;

import com.google.common.base.MoreObjects;

public class SortNoComparator implements Comparator<Sortable> {

	public static final Comparator<Sortable> COMPARATOR = new SortNoComparator();

	@Override
	public int compare(Sortable o1, Sortable o2) {
		Integer sortNo1 = MoreObjects.firstNonNull(o1.getSortNo(), 0);
		Integer sortNo2 = MoreObjects.firstNonNull(o2.getSortNo(), 0);
		return sortNo1 - sortNo2;
	}

}
