package com.whenling.extension.mall.product.repo;

import java.util.List;
import java.util.Set;

import com.whenling.extension.mall.product.model.Goods;
import com.whenling.extension.mall.product.model.Product;

public interface ProductRepositoryCustom {
	List<Product> findByGoodsWithExcludes(Goods goods, Set<Product> excludes);
}
