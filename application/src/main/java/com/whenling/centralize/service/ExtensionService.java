package com.whenling.centralize.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.centralize.model.ExtensionEntity;
import com.whenling.centralize.model.ExtensionEntity.Type;
import com.whenling.centralize.repo.ExtensionRepository;
import com.whenling.module.domain.service.BaseService;

/**
 * 模块扩展服务
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:29:26
 */
@Service
public class ExtensionService extends BaseService<ExtensionEntity, Long> {

	@Autowired
	private ExtensionRepository extensionRepository;

	public ExtensionEntity findApplication() {
		List<ExtensionEntity> entities = extensionRepository.findByType(Type.Application);
		if (entities == null || entities.size() == 0) {
			return null;
		} else if (entities.size() == 1) {
			return entities.get(0);
		}
		throw new RuntimeException();
	}

	public List<ExtensionEntity> findAllExtension() {
		return extensionRepository.findByType(Type.Extension);
	}

}
