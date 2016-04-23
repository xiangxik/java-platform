package com.whenling.extension.mall.product.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.extension.mall.product.model.Product;
import com.whenling.extension.mall.product.model.ProductCategory;
import com.whenling.extension.mall.product.model.QProduct;
import com.whenling.module.domain.repository.BaseRepository;

public interface ProductRepository
		extends BaseRepository<Product, Long>, ProductRepositoryCustom, QuerydslBinderCustomizer<QProduct> {

	Page<Product> findByProductCategory(ProductCategory productCategory, Pageable pageable);

	@Override
	default void customize(QuerydslBindings bindings, QProduct root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
