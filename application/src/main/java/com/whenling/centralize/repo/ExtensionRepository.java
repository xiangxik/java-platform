package com.whenling.centralize.repo;

import java.util.List;

import com.whenling.centralize.model.ExtensionEntity;
import com.whenling.centralize.model.ExtensionEntity.Type;
import com.whenling.module.domain.repository.BaseRepository;

/**
 * 模块扩展仓库
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:26:37
 */
public interface ExtensionRepository extends BaseRepository<ExtensionEntity, Long> {

	List<ExtensionEntity> findByType(Type type);

}
