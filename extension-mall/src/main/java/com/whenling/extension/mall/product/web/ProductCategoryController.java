package com.whenling.extension.mall.product.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.extension.mall.product.model.ProductCategory;
import com.whenling.extension.mall.product.service.ProductCategoryService;
import com.whenling.module.domain.model.Result;
import com.whenling.module.domain.model.Tree;

@Controller
@RequestMapping("/admin/productCategory")
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public Tree<ProductCategory> tree() {
		Tree<ProductCategory> tree = productCategoryService.findTree(null);
		tree.makeExpandAll();
		return tree;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public ProductCategory get(@RequestParam(value = "id", required = false) ProductCategory productCategory) {

		return productCategory == null ? productCategoryService.newEntity() : productCategory;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute @Valid ProductCategory productCategory, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError(bindingResult.getAllErrors());
		}

		productCategoryService.save(productCategory);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") ProductCategory productCategory) {
		productCategoryService.delete(productCategory);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") ProductCategory[] productCategories) {
		for (ProductCategory productCategory : productCategories) {
			productCategoryService.delete(productCategory);
		}

		return Result.success();
	}

}
