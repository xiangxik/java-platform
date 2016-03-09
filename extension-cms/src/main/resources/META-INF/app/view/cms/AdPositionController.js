Ext.define("app.view.cms.AdPositionController", {
	extend : "Ext.app.ViewController",
	alias : "controller.adPosition",
	mixins : {
		center : "app.view.main.CenterController"
	},
	onItemClick : function(grid, item) {

	},

	onAdd : function(button) {
		var code = "adpositionform";
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.cms.AdPositionForm", {
				id : code,
				closable : true,
				title : "新建广告位",
				iconCls : "Images"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowEdit : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "adpositionform" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.cms.AdPositionForm", {
				id : code,
				closable : true,
				title : "编辑广告位【" + item.get("name") + "】",
				iconCls : "Images"
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
					url : Ext.ctx + "/admin/adPosition/delete",
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
		var adpositionForm = button.up("adpositionform");
		var form = adpositionForm.getForm();
		var store = this.getViewModel().getStore("list");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					store.reload();
					me.closeTab(adpositionForm);
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},

	onDelete : function(button) {
		var grid = button.up("adPosition");
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
						url : Ext.ctx + "/admin/adPosition/delete",
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