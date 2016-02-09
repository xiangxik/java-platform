Ext.define("app.view.role.RoleList", {
	extend : "Ext.grid.Panel",
	alias : "widget.rolelist",
	requires : [ "app.view.role.RoleController", "app.view.role.RoleModel" ],
	title : "角色列表",
	iconCls : "Userkey",
	controller : "role",
	viewModel : "role",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "代号",
		dataIndex : "code"
	}, {
		text : "操作",
		xtype : "actioncolumn",
		items : [ {
			iconCls : "Applicationedit",
			tooltip : "编辑"
		}, {
			iconCls : "Applicationdelete",
			tooltip : "删除"
		} ]
	} ],
	tbar : [ {
		xtype : "button",
		text : "添加",
		iconCls : "Add",
		handler : "onRoleAdd"
	}, {
		xtype : "button",
		text : "删除",
		iconCls : "Delete",
		handler : "onRoleDelete"
	} ],
	selType : "checkboxmodel",
	multiSelect : true,
	initComponent : function() {
		var store = this.getViewModel().getStore("page");
		this.store = store;
		this.dockedItems = [ {
			xtype : "pagingtoolbar",
			dock : "bottom",
			displayInfo : true,
			store : store
		} ],

		this.callParent(arguments);
	}
});