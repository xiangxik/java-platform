package com.whenling.extension.mall.product.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.whenling.extension.mall.product.model.Product;
import com.whenling.module.domain.service.BaseService;

@Service
public class ProductService extends BaseService<Product, Long> {

	@Override
	public Product newEntity() {
		Product product = super.newEntity();
		product.setFullName(null);
		product.setAllocatedStock(0);
		product.setScore(0F);
		product.setTotalScore(0L);
		product.setScoreCount(0L);
		product.setHits(0L);
		product.setWeekHits(0L);
		product.setMonthHits(0L);
		product.setSales(0L);
		product.setWeekSales(0L);
		product.setMonthSales(0L);
		Date now = new Date();
		product.setWeekHitsDate(now);
		product.setMonthHitsDate(now);
		product.setWeekSalesDate(now);
		product.setMonthSalesDate(now);
		product.setReviews(null);
		product.setConsultations(null);
		product.setFavoriteUsers(null);
		product.setPromotions(null);
		product.setCartItems(null);
		product.setOrderItems(null);
		product.setGiftItems(null);
		product.setProductNotifies(null);
		return product;
	}

}
