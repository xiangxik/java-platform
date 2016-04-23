package com.whenling.extension.mall.product.service;

import org.springframework.stereotype.Service;

import com.whenling.extension.mall.product.model.Goods;
import com.whenling.module.domain.service.BaseService;

@Service
public class GoodsService extends BaseService<Goods, Long> {

//	@Autowired
//	private ProductRepository productRepository;

	@Override
	public Goods save(Goods goods) {
//		if (goods.isNew()) {
//			if (goods.getProducts() != null) {
//				for (Product product : goods.getProducts()) {
//					setValue(product);
//				}
//			}
//		} else {
//			Set<Product> excludes = new HashSet<Product>();
//			excludes.addAll(Collections2.filter(goods.getProducts(), (product) -> {
//				return product != null && !product.isNew();
//			}));
//			List<Product> products = productRepository.findByGoodsWithExcludes(goods, excludes);
//			System.out.println(products.size());
//		}

		return super.save(goods);
	}

//	private void setValue(Product product) {
//		if (product == null) {
//			return;
//		}
//		if (Strings.isNullOrEmpty(product.getSn())) {
//
//		}
//
//		StringBuffer fullName = new StringBuffer(product.getName());
//		product.setFullName(fullName.toString());
//	}

}
