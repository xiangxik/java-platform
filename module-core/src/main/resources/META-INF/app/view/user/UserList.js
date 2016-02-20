Ext.define("app.view.user.UserList", {
	extend : "Ext.grid.Panel",
	alias : "widget.userlist",
	requires : [ "app.view.user.UserController", "app.view.user.UserModel" ],
	title : "用户列表",
	controller : "user",
	viewModel : "user",
	columns : [ {
		xtype : 'rownumberer'
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "邮箱",
		dataIndex : "email"
	}, {
		text : "手机",
		dataIndex : "mobile"
	}, {
		text : "生日",
		xtype : 'datecolumn',
		format : 'Y年m月d日',
		dataIndex : "birthday"
	}, {
		text : "超级管理员",
		xtype : "booleancolumn",
		dataIndex : "superAdmin",
		trueText : "是",
		falseText : "不是"
	}, {
		text : "性别",
		xtype : "templatecolumn",
		tpl : "用户的性别是{sex}"
	}, {
		text : "操作",
		xtype : "actioncolumn",
		items : [ {
			iconCls : "Applicationformedit",
			handler : "onRowEdit"
		}, {
			iconCls : "Delete"
		} ]
	} ],
	tbar : [ {
		xtype : "button",
		text : "添加",
		iconCls : 'Add',
		handler : "onAdd"
	}, {
		xtype : "button",
		text : "删除",
		iconCls : 'Delete'
	} ],
	selType : "checkboxmodel",
	multiSelect : true,
	bind : {
		store : "{list}"
	},
	dockedItems : [ {
		xtype : "pagingtoolbar",
		dock : "bottom",
		displayInfo : true,
		bind : {
			store : "{list}"
		}
	} ]
});