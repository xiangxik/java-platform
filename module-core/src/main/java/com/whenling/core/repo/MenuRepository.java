package com.whenling.core.repo;

import java.util.List;

import com.whenling.core.model.Menu;
import com.whenling.core.support.repo.BaseRepository;

public interface MenuRepository extends BaseRepository<Menu, Long> {

	List<Menu> findByParent(Menu parent);

}
