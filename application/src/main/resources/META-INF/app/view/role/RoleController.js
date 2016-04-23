Ext.define("app.view.role.RoleController", {
	extend : "Ext.app.ViewController",
	alias : "controller.role",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onAdd : function(button) {
		var code = "roleform";
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.role.RoleForm", {
				id : code,
				closable : true,
				title : "新建角色",
				iconCls : "Userkey"
			});

			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowEdit : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "roleform" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.role.RoleForm", {
				id : code,
				closable : true,
				title : "编辑角色【" + item.get("name") + "】",
				iconCls : "Userkey"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowAuthorize : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "roleauthform" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.role.AuthForm", {
				id : code,
				closable : true,
				title : "编辑角色【" + item.get("name") + "】的权限",
				iconCls : "Lockkey"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowUser : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "roleuserlist" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.role.UserList", {
				id : code,
				closable : true,
				title : "编辑角色【" + item.get("name") + "】的用户",
				iconCls : "Usergo"
			});
			view.setRole(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowDelete : function(grid, rowIndex, colIndex) {
		var role = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + role.get("name") + "】？", function(choice) {
			if (choice === "yes") {

				var store = this.getViewModel().getStore("list");

				Ext.Ajax.request({
					url : Ext.ctx + "/admin/role/delete",
					params : {
						id : role.id
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
		var grid = button.up("rolelist");
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
						url : Ext.ctx + "/admin/role/delete",
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
		var formPanel = button.up("roleform");
		var form = formPanel.getForm();
		var store = this.getViewModel().getStore("list");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					store.reload();
					me.closeTab(formPanel);
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},

	onAuthSave : function(button) {
		var me = this;

		var formPanel = button.up("authform");
		var menuAuth = formPanel.down("treepanel#menuAuth");
		var role = formPanel.getRecord();

		var menus = [];
		var menuChecked = menuAuth.getChecked();
		Ext.each(menuChecked, function(record, index, array) {
			if (record.get("id") != "root") {
				menus.push(record.get("id"));
			}
		});

		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
				params : {
					id : role.get("id"),
					menus : menus
				},
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					me.closeTab(formPanel);
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},
	onUserAdd : function(button) {
		var roleUserList = button.up("roleuserlist");
		var dialog = Ext.create("app.view.role.UserSelectDialog");
		dialog.setUserList(roleUserList);
		dialog.show();
	},

	onUserRemove : function(button) {
		var roleUserList = button.up("roleuserlist");
		var data = roleUserList.getSelection();
		if (data.length == 0) {
			Ext.Msg.alert("提示", "您最少要选择一条数据");
		} else {
			var ids = [];
			Ext.each(data, function(record, index, array) {
				ids.push(record.id);
			});

			Ext.Ajax.request({
				url : Ext.ctx + "/admin/role/userRemove",
				params : {
					id : roleUserList.getRole().get("id"),
					users : ids
				},
				method : "POST",
				timeout : 10000,
				success : function(response) {
					Ext.Msg.info("提示", "操作成功");
					roleUserList.getStore().reload();
				}
			});
		}
	},

	onUserSelect : function(button) {
		var dialog = button.up("userselectdialog");
		var roleUserList = dialog.getUserList();
		var grid = dialog.child("grid");

		var data = grid.getSelection();
		if (data.length == 0) {
			Ext.Msg.alert("提示", "您最少要选择一条数据");
		} else {
			var ids = [];
			Ext.each(data, function(record, index, array) {
				ids.push(record.id);
			});

			Ext.Ajax.request({
				url : Ext.ctx + "/admin/role/user",
				params : {
					id : roleUserList.getRole().get("id"),
					users : ids
				},
				method : "POST",
				timeout : 10000,
				success : function(response) {
					Ext.Msg.info("提示", "操作成功");
					roleUserList.getStore().reload();
					dialog.close();
				}
			});
		}
	}
});