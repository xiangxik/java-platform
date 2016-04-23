package com.whenling.extension.mall.product.service;

import org.springframework.stereotype.Service;

import com.whenling.extension.mall.product.model.ProductCategory;
import com.whenling.module.domain.service.TreeService;

@Service
public class ProductCategoryService extends TreeService<ProductCategory, Long> {

}
