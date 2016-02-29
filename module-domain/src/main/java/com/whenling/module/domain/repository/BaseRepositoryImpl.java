package com.whenling.module.domain.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.whenling.module.domain.exception.DeleteLockedEntityException;
import com.whenling.module.domain.model.Lockedable;

public class BaseRepositoryImpl<T, I extends Serializable> extends SimpleJpaRepository<T, I>
		implements BaseRepository<T, I> {

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}

	@Override
	public void delete(T entity) {
		if (entity instanceof Lockedable) {
			if (((Lockedable) entity).getLocked()) {
				throw new DeleteLockedEntityException();
			}
		}
		
		
		super.delete(entity);
	}
}
