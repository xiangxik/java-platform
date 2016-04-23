package com.whenling.module.web.controller;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.module.domain.model.Tree;
import com.whenling.module.domain.model.TreeEntity;
import com.whenling.module.domain.service.TreeService;

public class TreeController<T extends TreeEntity<?, I, T>, I extends Serializable> extends EntityController<T, I> {

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public Tree<T> tree() {
		Tree<T> tree = getTreeService().findTree(null);
		onPostTree(tree);
		return tree;
	}

	protected void onPostTree(Tree<T> tree) {

	}

	protected TreeService<T, I> getTreeService() {
		return (TreeService<T, I>) baseService;
	}

}
