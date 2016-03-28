package com.whenling.module.domain.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import com.whenling.module.base.SpringContext;
import com.whenling.module.domain.event.PostInsertEvent;
import com.whenling.module.domain.event.PostUpdateEvent;
import com.whenling.module.domain.event.PreInsertEvent;
import com.whenling.module.domain.event.PreUpdateEvent;
import com.whenling.module.domain.exception.DeleteLockedEntityException;
import com.whenling.module.domain.model.Lockedable;

/**
 * 基础仓库实现类
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:03:10
 * @param
 * @param <I>
 */
public class BaseRepositoryImpl<T, I extends Serializable> extends QueryDslJpaRepository<T, I>
		implements BaseRepository<T, I> {

	private ApplicationEventPublisher applicationEventPublisher;
	private JpaEntityInformation<T, I> entityInformation;
	private EntityManager entityManager;

	public BaseRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}

	@Override
	public <S extends T> S save(S entity) {
		if (entityInformation.isNew(entity)) {
			getApplicationEventPublisher().publishEvent(new PreInsertEvent(this, entity));

			entityManager.persist(entity);

			getApplicationEventPublisher().publishEvent(new PostInsertEvent(this, entity));
			return entity;
		} else {
			getApplicationEventPublisher().publishEvent(new PreUpdateEvent(this, entity));

			S result = entityManager.merge(entity);

			getApplicationEventPublisher().publishEvent(new PostUpdateEvent(this, entity));
			return result;
		}
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

	public ApplicationEventPublisher getApplicationEventPublisher() {
		if (applicationEventPublisher == null) {
			applicationEventPublisher = SpringContext.getApplicationContext();
		}
		return applicationEventPublisher;
	}

}
