package com.whenling.centralize.service;

import org.springframework.stereotype.Service;

import com.whenling.centralize.model.Menu;
import com.whenling.module.domain.service.TreeService;

@Service
public class MenuService extends TreeService<Menu, Long> {

}
