Ext.define("app.mdm.view.device.CommandController", {
	extend : "Ext.Mixin",
	deviceInformation : function(device) {
		Ext.Ajax.request({
			url : Ext.ctx + "/admin/command/information",
			params : {
				id : device.get("id")
			},
			method : "POST",
			success : function(response) {
				Ext.Msg.info("提示", "操作成功");
			}
		});
	},

	deviceLock : function(device) {
		var deviceLockWin = Ext.create("app.mdm.view.device.DeviceLock");
		deviceLockWin.setDevice(device);
		deviceLockWin.show();
	},

	clearPasscode : function(device) {

		Ext.Msg.confirm("提示", "您确定要清除密码？", function(choice) {
			if (choice === "yes") {
				Ext.Ajax.request({
					url : Ext.ctx + "/admin/command/clearPasscode",
					params : {
						id : device.get("id")
					},
					method : "POST",
					success : function(response) {
						Ext.Msg.info("提示", "操作成功");
					}
				});
			}
		});

	},

	enableLostMode : function(device) {
		Ext.Msg.confirm("提示", "您确定要启用丢失模式？", function(choice) {
			if (choice === "yes") {
				Ext.Ajax.request({
					url : Ext.ctx + "/admin/command/enableLostMode",
					params : {
						id : device.get("id")
					},
					method : "POST",
					success : function(response) {
						Ext.Msg.info("提示", "操作成功");
					}
				});
			}
		});

	},

	disableLostMode : function(device) {
		Ext.Msg.confirm("提示", "您确定要禁用丢失模式？", function(choice) {
			if (choice === "yes") {
				Ext.Ajax.request({
					url : Ext.ctx + "/admin/command/disableLostMode",
					params : {
						id : device.get("id")
					},
					method : "POST",
					success : function(response) {
						Ext.Msg.info("提示", "操作成功");
					}
				});
			}
		});

	},

	deviceLocation : function(device) {
		Ext.Ajax.request({
			url : Ext.ctx + "/admin/command/location",
			params : {
				id : device.get("id")
			},
			method : "POST",
			success : function(response) {
				Ext.Msg.info("提示", "操作成功");
			}
		});

	},

	eraseDevice : function(device) {
		Ext.Msg.confirm("提示", "您确定要擦除设备？", function(choice) {
			if (choice === "yes") {
				Ext.Msg.confirm("提示", "擦除设备将无法恢复，确定继续？", function(choice) {
					if (choice === "yes") {
						Ext.Ajax.request({
							url : Ext.ctx + "/admin/command/erase",
							params : {
								id : device.get("id")
							},
							method : "POST",
							success : function(response) {
								Ext.Msg.info("提示", "操作成功");
							}
						});
					}
				});
			}
		});
	}
});