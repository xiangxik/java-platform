Ext.define("app.view.user.UserController", {
	extend : "Ext.app.ViewController",
	alias : "controller.user",

	showEditWindow : function(user) {
		if (!this.formWindow) {
			this.formWindow = Ext.create("Ext.window.Window", {
				modal : true,
				layout : "fit",
				closeAction : "hide"
			});
		}

		this.formWindow.setTitle((user ? "编辑" : "新建") + "用户");
		this.formWindow.removeAll(true);

		var form = Ext.create("app.view.user.UserForm");

		if (user) {
			form.loadRecord(user);
		}

		this.formWindow.add(form);
		this.formWindow.show();
	},

	onAdd : function() {
		this.showEditWindow();
	},
	onRowEdit : function(grid, rowIndex, colIndex) {
		var user = grid.getStore().getAt(rowIndex);
		this.showEditWindow(user);
	},

	onRowDelete : function(grid, rowIndex, colIndex) {
		var user = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + user.get("name") + "】？", function(choice) {
			if (choice === "yes") {

				var store = this.getViewModel().getStore("list");

				Ext.Ajax.request({
					url : Ext.ctx + "/admin/user/delete",
					params : {
						id : user.id
					},
					method : "POST",
					timeout : 10000,
					success : function(response) {
						Ext.Msg.info("提示", "操作成功");
						store.reload();
					}
				});
			}
		}, this);
	},

	onDelete : function(button) {
		var grid = button.up("userlist");
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
						url : Ext.ctx + "/admin/user/delete",
						params : {
							ids : ids
						},
						method : "POST",
						timeout : 10000,
						success : function(response) {
							Ext.Msg.info("提示", "操作成功");
							store.reload();
						}
					});
				}
			}, this);
		}
	},

	onFormSave : function(button) {
		var form = button.up("form").getForm();
		var window = button.up("window");

		var store = this.getViewModel().getStore("list");

		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					window.hide();
					store.reload();
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	}

});