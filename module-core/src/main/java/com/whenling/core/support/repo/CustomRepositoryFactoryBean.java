package com.whenling.core.support.repo;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.ClassUtils;

public class CustomRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> {
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new CustomRepository<>(entityManager);
	}

	private static class CustomRepository<T, I extends Serializable> extends JpaRepositoryFactory {

		private final EntityManager entityManager;

		public CustomRepository(EntityManager entityManager) {
			super(entityManager);

			this.entityManager = entityManager;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected Object getTargetRepository(RepositoryInformation information) {
			return isBaseRepository(information.getRepositoryInterface())
					? (isTreeRepository(information.getRepositoryInterface())
							? new TreeRepositoryImpl(getEntityInformation(information.getDomainType()), entityManager)
							: new BaseRepositoryImpl<>(getEntityInformation(information.getDomainType()),
									entityManager))
					: super.getTargetRepository(information);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return isBaseRepository(metadata.getRepositoryInterface())
					? (isTreeRepository(metadata.getRepositoryInterface()) ? TreeRepositoryImpl.class
							: BaseRepositoryImpl.class)
					: super.getRepositoryBaseClass(metadata);
		}

		private boolean isBaseRepository(Class<?> repositoryInterface) {
			return ClassUtils.isAssignable(BaseRepository.class, repositoryInterface);
		}

		private boolean isTreeRepository(Class<?> repositoryInterface) {
			return ClassUtils.isAssignable(TreeRepository.class, repositoryInterface);
		}

	}
}
