package com.whenling.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.core.model.Menu;
import com.whenling.core.repo.MenuRepository;
import com.whenling.core.support.service.BaseService;

@Service
public class MenuService extends BaseService<Menu, Long> {

	@Autowired
	private MenuRepository menuRepository;

	public List<Menu> getRoots() {
		return menuRepository.findByParent(null);
	}
}
