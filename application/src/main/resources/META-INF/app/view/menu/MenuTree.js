Ext.define("app.view.menu.MenuTree", {
	extend : "Ext.tree.Panel",
	alias : "widget.menutree",
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
	} ],
	listeners : {
		itemclick : 'onItemClick'
	},
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