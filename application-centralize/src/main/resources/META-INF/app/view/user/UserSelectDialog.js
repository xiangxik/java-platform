Ext.define("app.view.user.UserSelectDialog", {
	extend : "Ext.window.Window",
	alias : "widget.userselectdialog",
	requires : [ "app.view.role.RoleController", "app.view.role.RoleModel" ],
	controller : "role",
	viewModel : "role",
	title : "选择用户",
	iconCls : "User",
	modal : true,
	width : 640,
	height : 480,
	layout : "fit",
	items : {
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
		bind : {
			store : "{userselectlist}"
		},
		dockedItems : [ {
			xtype : "pagingtoolbar",
			dock : "bottom",
			displayInfo : true,
			bind : {
				store : "{userselectlist}"
			}
		} ]
	},
	buttons : [ {
		text : "确定",
		formBind : true,
		handler : "onUserSelect"
	} ]
});