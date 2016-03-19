Ext.define("app.view.menu.MenuList", {
	extend : "Ext.tree.Panel",
	alias : "widget.menu",
	requires : [ "app.view.menu.MenuController", "app.view.menu.MenuModel" ],
	controller : "menu",
	viewModel : "menu",
	rootVisible : false,
	forceFit : true,
	bind : {
		store : "{tree}"
	},
	columns : [ {
		xtype : "treecolumn",
		text : "名称",
		dataIndex : "text",
		flex : 3
	}, {
		text : "代号",
		dataIndex : "code",
		flex : 1
	}, {
		text : "图标样式",
		dataIndex : "iconCls",
		flex : 1
	}, {
		text : "视图",
		dataIndex : "view",
		flex : 2
	}, {
		text : "配置参数",
		dataIndex : "config",
		flex : 2
	}, {
		text : "操作",
		xtype : "actioncolumn",
		flex : 1,
		items : [ {
			iconCls : "Pencil actionColumnIcon",
			tooltip : "编辑",
			handler : "onRowEdit"
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
	} ]
});