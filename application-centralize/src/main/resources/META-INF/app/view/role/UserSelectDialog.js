Ext.define("app.view.role.UserSelectDialog", {
	extend : "Ext.window.Window",
	alias : "widget.userselectdialog",
	requires : [ "app.view.role.RoleController", "app.view.role.RoleModel", "app.ux.grid.SearchPanel" ],
	controller : "role",
	viewModel : "role",
	title : "选择用户",
	iconCls : "User",
	modal : true,
	width : 640,
	height : 480,
	layout : "fit",
	buttons : [ {
		text : "确定",
		formBind : true,
		handler : "onUserSelect"
	} ],
	getUserList : function() {
		return this.userList;
	},
	setUserList : function(userList) {
		this.userList = userList;
	},
	listeners : {
		beforerender : function(win, opts) {
			var role = win.getUserList().getRole();
			var store = Ext.create("app.store.PageStore", {
				model : "app.model.User",
				url : Ext.ctx + "/admin/role/userSelect",
				extraParams : {
					id : role.get("id")
				}
			});
			win.add({
				xtype : "grid",
				border : false,
				columns : [ {
					xtype : 'rownumberer'
				}, {
					text : "账号",
					dataIndex : "username"
				}, {
					text : "名称",
					dataIndex : "name"
				}, {
					text : "邮箱",
					dataIndex : "email",
					width : 140
				}, {
					text : "手机",
					dataIndex : "mobile"
				}, {
					text : "性别",
					width : 60,
					renderer : function(data, metadata, record, rowIndex, columnIndex, store) {
						return record.get("sex") === "male" ? "男" : (record.get("sex") === "female" ? "女" : "");
					}
				}, {
					text : "生日",
					xtype : 'datecolumn',
					format : 'Y年m月d日',
					dataIndex : "birthday"
				}, {
					text : "超级管理员",
					xtype : "booleancolumn",
					width : 80,
					dataIndex : "superAdmin",
					align : "center",
					trueText : "是",
					falseText : "否"
				} ],
				selType : "checkboxmodel",
				multiSelect : true,
				store : store,
				dockedItems : [ {
					xtype : "searchpanel",
					dock : "top",
					store : store,
					queryItems : [ {
						fieldLabel : "账号",
						xtype : "textfield",
						name : "username",
						columnWidth : .5
					}, {
						fieldLabel : "名称",
						xtype : "textfield",
						name : "name",
						columnWidth : .5
					}, {
						fieldLabel : "邮箱",
						xtype : "textfield",
						name : "email",
						columnWidth : .5
					}, {
						fieldLabel : "手机",
						xtype : "textfield",
						name : "mobile",
						columnWidth : .5
					} ]
				}, {
					xtype : "pagingtoolbar",
					dock : "bottom",
					displayInfo : true,
					store : store
				} ]
			});
		}
	}
});