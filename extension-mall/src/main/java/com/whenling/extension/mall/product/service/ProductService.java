package com.whenling.extension.mall.product.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.io.Files;
import com.whenling.centralize.support.util.QrCodeHelper;
import com.whenling.extension.mall.product.model.Product;
import com.whenling.extension.mall.product.model.ProductCategory;
import com.whenling.extension.mall.product.repo.ProductRepository;
import com.whenling.module.domain.service.BaseService;
import com.whenling.module.web.storage.StorageService;

@Service
public class ProductService extends BaseService<Product, Long> {

	public static final String STORE_PATH_PATTERN = "/upload/qrcode/product/";

	@Autowired
	public ProductRepository productRepository;

	@Autowired
	private StorageService storageService;

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

	@Override
	public Product save(Product entity) {
		boolean isNew = entity.isNew();

		entity = super.save(entity);
		if (isNew) {
			String qrCodePath = STORE_PATH_PATTERN + UUID.randomUUID() + ".png";
			File qrCodeFile = storageService.getFile(qrCodePath);
			try {
				Files.createParentDirs(qrCodeFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			QrCodeHelper.writeToFile("/wap/product/detail?id=" + entity.getId(), 240, 240,
					storageService.getFile(qrCodePath));
			entity.setQrCode(qrCodePath);
			save(entity);
		}

		return entity;
	}

	public Page<Product> findByProductCategory(ProductCategory productCategory, Pageable pageable) {
		return productRepository.findByProductCategory(productCategory, pageable);
	}

}
