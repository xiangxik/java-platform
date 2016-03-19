package com.whenling.extension.mall;

import org.springframework.stereotype.Component;

import com.whenling.centralize.Application;
import com.whenling.centralize.Extension;
import com.whenling.centralize.model.Menu;

@Component
public class MallExtension extends Extension {

	@Override
	public void init(Application app, boolean isNew, boolean isUpdate, Integer historyVersion) {
		if (isNew) {
			Menu productMenu = app.addMenu("商品管理", "productmanager", "Brick", null, null, null);
			app.addMenu("商品分类", "productcategory", "Bricklink", "app.view.mall.product.ProductCategoryList", null,
					productMenu);
		}

		if (isUpdate) {
			app.addMenu("商品管理", "product", "Brick", "app.view.mall.product.ProductList", null,
					app.findMenuByCode("productmanager"));
		}
	}

	@Override
	public Integer getVersion() {
		return 2;
	}

	@Override
	public String getAuthor() {
		return "ken";
	}

}
