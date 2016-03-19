Ext.define("app.view.user.UserList", {
	extend : "Ext.grid.Panel",
	alias : "widget.userlist",
	requires : [ "app.view.user.UserController", "app.view.user.UserModel", "app.ux.grid.SearchPanel" ],
	title : "用户列表",
	controller : "user",
	viewModel : "user",
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
	}, {
		text : "操作",
		xtype : "actioncolumn",
		width : 80,
		items : [ {
			iconCls : "Pencil actionColumnIcon",
			tooltip : "编辑",
			handler : "onRowEdit"
		}, {
			iconCls : "Key actionColumnIcon",
			tooltip : "修改密码",
			handler : "onRowChangPassword"
		}, {
			iconCls : "Delete actionColumnIcon",
			tooltip : "删除",
			handler : "onRowDelete"
		} ]
	} ],
	tbar : [ {
		xtype : "button",
		text : "添加",
		iconCls : "Add",
		handler : "onAdd"
	}, {
		xtype : "button",
		text : "删除",
		iconCls : "Delete",
		handler : "onDelete"
	} ],
	selType : "checkboxmodel",
	multiSelect : true,
	bind : {
		store : "{list}"
	},
	dockedItems : [ {
		xtype : "searchpanel",
		dock : "top",
		bind : {
			store : "{list}"
		},
		items : [ {
			fieldLabel : "账号",
			xtype : "textfield",
			name : "username",
			columnWidth : .25
		}, {
			fieldLabel : "名称",
			xtype : "textfield",
			name : "name",
			columnWidth : .25
		}, {
			fieldLabel : "邮箱",
			xtype : "textfield",
			name : "email",
			columnWidth : .25
		}, {
			fieldLabel : "手机",
			xtype : "textfield",
			name : "mobile",
			columnWidth : .25
		} ]
	}, {
		xtype : "pagingtoolbar",
		dock : "bottom",
		displayInfo : true,
		bind : {
			store : "{list}"
		}
	} ]
});