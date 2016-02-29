package com.whenling.centralize.repo;

import java.util.List;

import com.whenling.centralize.model.ExtensionEntity;
import com.whenling.centralize.model.ExtensionEntity.Type;
import com.whenling.module.domain.repository.BaseRepository;

public interface ExtensionRepository extends BaseRepository<ExtensionEntity, Long> {

	List<ExtensionEntity> findByType(Type type);

}
