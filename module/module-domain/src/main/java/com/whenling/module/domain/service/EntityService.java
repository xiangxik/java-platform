package com.whenling.module.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;

@Component
public class EntityService {

	@Autowired(required = false)
	private List<BaseService<?, ?>> services;

	public BaseService<?, ?> getService(Class<?> entityClass) {
		if (services != null) {
			for (BaseService<?, ?> service : services) {
				if (Objects.equal(service.getEntityClass(), entityClass)) {
					return service;
				}
			}
		}
		return null;
	}
}
