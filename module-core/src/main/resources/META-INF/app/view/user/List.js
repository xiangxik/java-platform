Ext.define("app.view.user.List", {
	extend : "Ext.grid.Panel",
	requires : [ 'app.store.Users' ],
	title : "用户列表",
	columns : [ {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "邮箱",
		dataIndex : "email"
	}, {
		text : "手机",
		dataIndex : "mobile"
	}, {
		text : "性别",
		dataIndex : "sex"
	} ],
	tbar : [ {
		xtype : "button",
		text : "添加",
		iconCls : 'fa fa-building'
	}, {
		xtype : "button",
		text : "删除",
		iconCls : 'fa fa-building'
	}, {
		xtype : "button",
		text : "修改",
		iconCls : 'fa fa-edit'
	}, {
		xtype : "button",
		text : "查看",
		iconCls : 'fa fa-eye'
	} ],
	selType : "checkboxmodel",
	multiSelect : true,
	initComponent : function() {
		this.store = Ext.create("app.store.Users");
		this.dockedItems = [ {
			xtype : "pagingtoolbar",
			dock : "bottom",
			displayInfo : true,
			store : this.store
		} ];

		this.callParent(arguments);
	}
});