package com.whenling.centralize.repo;

import com.whenling.centralize.model.Menu;
import com.whenling.module.domain.repository.TreeRepository;

/**
 * 菜单仓库
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:26:53
 */
public interface MenuRepository extends TreeRepository<Menu, Long> {
	Menu findByCode(String code);
}
