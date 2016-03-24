Ext.define("app.mdm.view.app.AppController", {
	extend : "Ext.app.ViewController",
	alias : "controller.app",
	mixins : {
		center : "app.view.main.CenterController"
	},
	onItemClick : function(grid, item) {

	},

	onAddEnterpriseApp : function(button) {
		var code = "appform";
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.mdm.view.app.AppForm", {
				id : code,
				closable : true,
				title : "新建应用",
				iconCls : "Application"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onAddAppStoreApp : function(button) {

		var store = this.getViewModel().getStore("list");

		Ext.create("app.mdm.view.app.AppleStoreAppSelection", {
			callback : function(win, record) {
				if (record) {

					Ext.Ajax.request({
						url : Ext.ctx + "/admin/app/save",
						params : {
							name : record.get("trackName"),
							icon60 : record.get("artworkUrl60"),
							icon100 : record.get("artworkUrl100"),
							icon512 : record.get("artworkUrl512"),
							screenshots : record.get("screenshotUrls"),
							supportedDevices : record.get("supportedDevices"),
							bundleId : record.get("bundleId"),
							trackId : record.get("trackId"),
							version : record.get("version"),
							fileSizeBytes : record.get("fileSizeBytes"),
							description : record.get("description"),
							gameCenterEnabled : record.get("isGameCenterEnabled")
						},
						method : "POST",
						success : function(response) {
							Ext.Msg.info("提示", "操作成功");
							store.reload();
							win.close();
						}
					});

				}
			}
		}).show();
	},

	onRowEdit : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "appform" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.mdm.view.app.AppForm", {
				id : code,
				closable : true,
				title : "编辑应用【" + item.get("name") + "】",
				iconCls : "Application"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowInstall : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);

		Ext.create("app.mdm.view.device.DeviceSelection", {
			title : "选择设备安装应用【" + item.get("name") + "】",
			handler : function(win, records) {

				var ids = [];
				Ext.each(records, function(record, index, array) {
					ids.push(record.id);
				});

				Ext.Ajax.request({
					url : Ext.ctx + "/admin/command/installAppForDevices",
					params : {
						id : item.get("id"),
						devices : ids
					},
					method : "POST",
					success : function(response) {
						Ext.Msg.info("提示", "操作成功");
					}
				});

				win.close();
			}
		}).show();
	},

	onRowDelete : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + item.get("name") + "】？", function(choice) {
			if (choice === "yes") {

				var store = this.getViewModel().getStore("list");

				Ext.Ajax.request({
					url : Ext.ctx + "/admin/app/delete",
					params : {
						id : item.id
					},
					method : "POST",
					success : function(response) {
						Ext.Msg.info("提示", "操作成功");
						store.remove(item);
					}
				});
			}
		}, this);
	},

	onFormSave : function(button) {
		var appForm = button.up("appform");
		var form = productForm.getForm();
		var store = this.getViewModel().getStore("list");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					store.reload();
					me.closeTab(productForm);
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},

	onDelete : function(button) {
		var grid = button.up("app");
		var data = grid.getSelection();
		if (data.length == 0) {
			Ext.Msg.alert("提示", "您最少要选择一条数据");
		} else {
			Ext.Msg.confirm("提示", "您确定要删除所选数据？", function(choice) {
				if (choice === "yes") {
					var ids = [];
					Ext.each(data, function(record, index, array) {
						ids.push(record.id);
					});

					var store = this.getViewModel().getStore("list");

					Ext.Ajax.request({
						url : Ext.ctx + "/admin/app/delete",
						params : {
							ids : ids
						},
						method : "POST",
						success : function(response) {
							Ext.Msg.info("提示", "操作成功");
							store.remove(data);
						}
					});
				}
			}, this);
		}
	}

});