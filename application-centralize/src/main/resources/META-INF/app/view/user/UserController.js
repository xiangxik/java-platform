Ext.define("app.view.user.UserController", {
	extend : "Ext.app.ViewController",
	alias : "controller.user",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onAdd : function(button) {
		var code = "userform";
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.user.UserForm", {
				id : code,
				closable : true,
				title : "新建用户",
				iconCls : "User"
			});

			view.insert(2, {
				fieldLabel : "密码",
				name : "password",
				inputType : "password",
				allowBlank : false
			});

			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowEdit : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "userform" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.user.UserForm", {
				id : code,
				closable : true,
				title : "编辑用户【" + item.get("name") + "】",
				iconCls : "User"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowChangPassword : function(grid, rowIndex, colIndex) {

		if (!this.passwordWindow) {
			this.passwordWindow = Ext.create("Ext.window.Window", {
				modal : true,
				title : "修改密码",
				iconCls : "Key",
				layout : "fit",
				closeAction : "hide"
			});
		}

		this.passwordWindow.removeAll(true);

		var form = Ext.create("app.view.user.PasswordForm");

		var user = grid.getStore().getAt(rowIndex);
		if (user) {
			form.loadRecord(user);
			this.passwordWindow.setTitle("修改用户【" + user.get("name") + "】的密码");
		}

		this.passwordWindow.add(form);
		this.passwordWindow.show();
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
		var userForm = button.up("userform");
		var form = userForm.getForm();
		var store = this.getViewModel().getStore("list");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					store.reload();
					me.closeTab(userForm);
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},
	onPasswordChange : function(button) {
		var form = button.up("form").getForm();
		var window = button.up("window");
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					window.hide();
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	}

});