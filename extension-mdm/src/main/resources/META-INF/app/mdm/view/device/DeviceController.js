Ext.define("app.mdm.view.device.DeviceController", {
	extend : "Ext.app.ViewController",
	alias : "controller.device",
	mixins : {
		center : "app.view.main.CenterController",
		cmd : "app.mdm.view.device.CommandController"
	},
	onItemClick : function(grid, item) {

	},

	onAdd : function(button) {
		var code = "deviceform";
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.mdm.view.device.DeviceForm", {
				id : code,
				closable : true,
				title : "新建设备",
				iconCls : "Brick"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowView : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "deviceview" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.mdm.view.device.DeviceView", {
				id : code,
				closable : true,
				title : "查看设备【" + item.get("name") + "】",
				iconCls : "Brick"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowEdit : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "deviceform" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.mdm.view.device.DeviceForm", {
				id : code,
				closable : true,
				title : "编辑设备【" + item.get("name") + "】",
				iconCls : "Brick"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowDelete : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + item.get("name") + "】？", function(choice) {
			if (choice === "yes") {

				var store = this.getViewModel().getStore("list");

				Ext.Ajax.request({
					url : Ext.ctx + "/admin/device/delete",
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
		var deviceForm = button.up("deviceform");
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
		var grid = button.up("device");
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
						url : Ext.ctx + "/admin/device/delete",
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
	},

	onDeviceInformation : function(button) {
		var formPanel = button.up("form");
		var item = formPanel.getRecord();
		this.deviceInformation(item);
	},

	onDeviceLock : function(button) {
		var formPanel = button.up("form");
		var item = formPanel.getRecord();
		this.deviceLock(item);
	},

	onClearPasscode : function(button) {
		var formPanel = button.up("form");
		var item = formPanel.getRecord();
		this.clearPasscode(item);
	},

	onEnableLostMode : function(button) {
		var formPanel = button.up("form");
		var item = formPanel.getRecord();
		this.enableLostMode(item);
	},

	onDisableLostMode : function(button) {
		var formPanel = button.up("form");
		var item = formPanel.getRecord();
		this.disableLostMode(item);
	},

	onDeviceLocation : function(button) {
		var formPanel = button.up("form");
		var item = formPanel.getRecord();
		this.deviceLocation(item);
	},

	onEraseDevice : function(button) {
		var formPanel = button.up("form");
		var item = formPanel.getRecord();
		this.eraseDevice(item);
	}
});