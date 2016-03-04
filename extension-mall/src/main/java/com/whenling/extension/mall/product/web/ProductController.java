package com.whenling.extension.mall.product.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.extension.mall.product.model.Goods;
import com.whenling.extension.mall.product.model.Product;
import com.whenling.extension.mall.product.service.GoodsService;
import com.whenling.extension.mall.product.service.ProductService;
import com.whenling.module.domain.model.Result;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private GoodsService goodsService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<Product> list(Pageable pageable) {
		return productService.findAll(pageable);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Product get(@RequestParam(value = "id", required = false) Product product) {

		return product == null ? productService.newEntity() : product;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute @Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError(bindingResult.getAllErrors());
		}

		Goods goods = product.isNew() ? goodsService.newEntity() : product.getGoods();
		List<Product> products = new ArrayList<>();

		product.setGoods(goods);
		product.setSpecifications(null);
		product.setSpecificationValues(null);
		products.add(product);

		goods.getProducts().clear();
		goods.getProducts().addAll(products);
		goodsService.save(goods);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") Product product) {
		productService.delete(product);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") Product[] products) {
		for (Product product : products) {
			productService.delete(product);
		}

		return Result.success();
	}
}
