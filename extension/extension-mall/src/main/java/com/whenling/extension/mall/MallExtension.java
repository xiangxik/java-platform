package com.whenling.extension.mall;

import org.springframework.stereotype.Component;

import com.whenling.centralize.Application;
import com.whenling.centralize.Extension;
import com.whenling.centralize.model.Menu;
import com.whenling.extension.mall.MallSetting.RoundType;

@Component
public class MallExtension extends Extension {

	@Override
	public void init(Application app, boolean isNew, boolean isUpdate, Integer historyVersion) {
		if (isNew) {
			Menu productMenu = app.addMenu("商品管理", "productmanager", "Brick", null, null, null);
			app.addMenu("商品分类", "productcategory", "Bricklink", "app.view.mall.product.ProductCategoryList", null,
					productMenu);
			app.addMenu("商品管理", "product", "Brick", "app.view.mall.product.ProductList", null,
					app.findMenuByCode("productmanager"));
		}

		if (isUpdate) {

		}

		app.addSetting("mallConfig", "启用计税", MallSetting.KEY_IS_TAX_PRICE_ENABLED, false);
		app.addSetting("mallConfig", "税率", MallSetting.KEY_TAX_RATE, 0.07);
		app.addSetting("mallConfig", "价格进位类型", MallSetting.KEY_PRICE_ROUND_TYPE, RoundType.roundDown);
		app.addSetting("mallConfig", "价格小数位", MallSetting.KEY_PRICE_SCALE, 2);
	}

	@Override
	public Integer getVersion() {
		return 3;
	}

	@Override
	public String getAuthor() {
		return "ken";
	}

}
