package com.whenling.extension.mall.product.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.whenling.extension.mall.product.model.Goods;
import com.whenling.extension.mall.product.model.Product;
import com.whenling.extension.mall.product.repo.ProductRepository;
import com.whenling.module.domain.service.BaseService;

@Service
public class GoodsService extends BaseService<Goods, Long> {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Goods save(Goods goods) {
		if (goods.isNew()) {
			if (goods.getProducts() != null) {
				for (Product product : goods.getProducts()) {
					setValue(product);
				}
			}
		} else {
			Set<Product> excludes = new HashSet<Product>();
			excludes.addAll(Collections2.filter(goods.getProducts(), (product) -> {
				return product != null && !product.isNew();
			}));
			List<Product> products = productRepository.findByGoodsWithExcludes(goods, excludes);
			System.out.println(products.size());
		}

		return super.save(goods);
	}

	private void setValue(Product product) {
		if (product == null) {
			return;
		}
		if (Strings.isNullOrEmpty(product.getSn())) {

		}

		StringBuffer fullName = new StringBuffer(product.getName());
		product.setFullName(fullName.toString());
	}

}
