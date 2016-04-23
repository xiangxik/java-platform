package com.whenling.centralize.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public interface Areable {
	Area getArea();

	void setArea(Area area);

	String getAreaName();

	void setAreaName(String areaName);

	@PrePersist
	default void prePersist() {
		if (getArea() != null) {
			setAreaName(getArea().getFullName());
		}
	}

	@PreUpdate
	default void preUpdate() {
		if (getArea() != null) {
			setAreaName(getArea().getFullName());
		}
	}
}
