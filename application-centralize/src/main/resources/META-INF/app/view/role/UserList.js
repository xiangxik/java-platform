Ext.define("app.view.role.UserList", {
	extend : "Ext.grid.Panel",
	alias : "widget.roleuserlist",
	title : "用户列表",
	forceFit : true,
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
	tbar : [ {
		xtype : "button",
		text : "添加用户",
		iconCls : "Add",
		handler : "onUserAdd"
	}, {
		xtype : "button",
		text : "移除用户",
		iconCls : "Delete",
		handler : "onUserRemove"
	} ],
	selType : "checkboxmodel",
	multiSelect : true,
	getRole : function() {
		return this.role;
	},
	initComponent : function() {
		this.callParent(arguments);

		var store = Ext.create("app.store.PageStore", {
			model : "app.model.User",
			url : Ext.ctx + "/admin/role/user",
			extraParams : {
				id : this.getRole().get("id")
			}
		});
		this.setStore(store);
		this.addDocked({
			xtype : "pagingtoolbar",
			dock : "bottom",
			displayInfo : true,
			store : store
		});
	}
});