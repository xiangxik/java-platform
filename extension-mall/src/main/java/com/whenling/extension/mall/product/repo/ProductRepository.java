package com.whenling.extension.mall.product.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whenling.extension.mall.product.model.Product;
import com.whenling.extension.mall.product.model.ProductCategory;
import com.whenling.module.domain.repository.BaseRepository;

public interface ProductRepository extends BaseRepository<Product, Long>, ProductRepositoryCustom {

	Page<Product> findByProductCategory(ProductCategory productCategory, Pageable pageable);
}
