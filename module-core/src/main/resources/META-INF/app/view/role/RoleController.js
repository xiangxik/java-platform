Ext.define("app.view.role.RoleController", {
	extend : "Ext.app.ViewController",
	alias : "controller.role",

	showEditWindow : function(role) {
		if (!this.formWindow) {
			this.formWindow = Ext.create("Ext.window.Window", {
				iconCls : "Userkey",
				modal : true,
				layout : "fit",
				closeAction : "hide"
			});
		}

		this.formWindow.setTitle((role ? "编辑" : "新建") + "角色");
		this.formWindow.removeAll(true);

		var form = Ext.create("app.view.role.RoleForm");

		if (role) {
			form.loadRecord(role);
		}
		this.formWindow.add(form);

		this.formWindow.show();
	},

	onAdd : function() {
		this.showEditWindow();
	},

	onRowEdit : function(grid, rowIndex, colIndex) {
		var role = grid.getStore().getAt(rowIndex);
		this.showEditWindow(role);
	},

	onRowAuthorize : function(grid, rowIndex, colIndex) {
		var role = grid.getStore().getAt(rowIndex);

		var rolePanel = grid.up("role");
		var eastPanel = rolePanel.getComponent("eastPanel");
		if (eastPanel) {
			rolePanel.remove(eastPanel, true)
		}

		var authForm = Ext.create("app.view.role.AuthForm", {
			region : "east",
			id : "eastPanel",
			width : 300,
			role : role,
			title : "为【" + role.get("name") + "】赋权",
			collapsible : true,
			collapsed : false,
			split : true
		});
		rolePanel.add(authForm);
	},

	onRowUser : function(grid, rowIndex, colIndex) {
		var role = grid.getStore().getAt(rowIndex);

		var rolePanel = grid.up("role");
		var eastPanel = rolePanel.getComponent("eastPanel");
		if (eastPanel) {
			rolePanel.remove(eastPanel, true)
		}

		var userList = Ext.create("app.view.role.UserList", {
			region : "east",
			id : "eastPanel",
			width : 600,
			role : role,
			title : "【" + role.get("name") + "】角色的用户",
			collapsible : true,
			collapsed : false,
			split : true
		});
		rolePanel.add(userList);
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
	},

	onAuthSave : function(button) {
		var authForm = button.up("authform");
		var menuAuth = authForm.down("treepanel#menuAuth");
		var form = authForm.getForm();

		var id = authForm.getRole() ? authForm.getRole().get("id") : "";

		var menus = [];
		var menuChecked = menuAuth.getChecked();
		Ext.each(menuChecked, function(record, index, array) {
			menus.push(record.id);
		});

		if (form.isValid()) {
			form.submit({
				params : {
					id : id,
					menus : menus
				},
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},

	onUserAdd : function(button) {
		if (!this.userWindow) {
			this.userWindow = Ext.create("app.view.user.UserSelectDialog", {
				closeAction : "hide"
			});
		}

		this.userWindow.show();
	},

	onUserRemove : function(button) {

	},

	onUserSelect : function(button) {
		var window = button.up("userselectdialog");
		var grid = window.child("grid");console.log(grid);

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
					id : "",
					users : ids
				},
				method : "POST",
				timeout : 10000,
				success : function(response) {
					Ext.Msg.info("提示", "操作成功");
				}
			});
		}
	}

});