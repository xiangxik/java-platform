package com.whenling.extension.cms;

import org.springframework.stereotype.Component;

import com.whenling.centralize.Application;
import com.whenling.centralize.Extension;
import com.whenling.centralize.model.Menu;

/**
 * CMS扩展
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:34:59
 */
@Component
public class CmsExtension extends Extension {

	@Override
	public void init(Application app, boolean isNew, boolean isUpdate, Integer historyVersion) {
		if (isNew) {
			Menu contentMenu = app.addMenu("内容管理", "content", null, null, null, null);
			app.addMenu("模板管理", "template", "Page", "app.view.cms.TemplateList", null, contentMenu);
		}
		
		if(isUpdate) {
			app.addMenu("广告位管理", "adPosition", "Images", "app.view.cms.AdPositionList", null, app.findMenuByCode("content"));
			app.addMenu("广告管理", "ad", "Image", "app.view.cms.AdList", null, app.findMenuByCode("content"));
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
