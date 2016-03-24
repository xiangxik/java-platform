package com.whenling.extension.mdm;

import org.springframework.stereotype.Component;

import com.whenling.centralize.Application;
import com.whenling.centralize.Extension;
import com.whenling.centralize.model.Menu;

@Component
public class MovingCastleExtension extends Extension {

	@Override
	public void init(Application app, boolean isNew, boolean isUpdate, Integer historyVersion) {

		if (isNew) {
			Menu mc = app.addMenu("移动城堡", "movingcastle", "Applicationhome", null, null, null);

			Menu deviceManagement = app.addMenu("设备管理", "devicemanagent", null, null, null, mc);
			app.addMenu("设备列表", "device", null, "app.mdm.view.device.DeviceList", null, deviceManagement);
		}

		if (isUpdate) {
			Menu appManagement = app.addMenu("应用管理", "appmanagent", "Application", null, null,
					app.findMenuByCode("movingcastle"));
			app.addMenu("应用列表", "app", "Application", "app.mdm.view.app.AppList", null, appManagement);
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
