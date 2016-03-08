package com.whenling.extension.mall.product.web.template;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.mysema.query.types.Predicate;
import com.whenling.extension.mall.product.model.Product;
import com.whenling.extension.mall.product.model.ProductCategory;
import com.whenling.extension.mall.product.model.QProduct;
import com.whenling.module.web.view.EntityFunctionPackage;

@Component("productFun")
public class ProductFunctionPackage extends EntityFunctionPackage<Product, Long> {

	public Iterable<Product> findByProductCategory(ProductCategory productCategory, int size) {
		QProduct qProduct = QProduct.product;
		Predicate predicate = qProduct.productCategory.eq(productCategory);
		return findAll(predicate, new PageRequest(0, size, new Sort(Direction.DESC, "createdDate")));
	}
}
