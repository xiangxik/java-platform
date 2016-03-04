package com.whenling.extension.mall.product.repo;

import com.whenling.extension.mall.product.model.Product;
import com.whenling.module.domain.repository.BaseRepository;

public interface ProductRepository extends BaseRepository<Product, Long>, ProductRepositoryCustom {

}
