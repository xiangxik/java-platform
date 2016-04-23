package com.whenling.extension.mall.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.extension.mall.product.model.ProductCategory;
import com.whenling.module.domain.model.Tree;
import com.whenling.module.web.controller.TreeController;

@Controller
@RequestMapping("/admin/productCategory")
public class ProductCategoryController extends TreeController<ProductCategory, Long> {
	@Override
	protected void onPostTree(Tree<ProductCategory> tree) {
		tree.makeExpandAll();
	}
}
