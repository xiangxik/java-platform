Ext.define("app.view.user.DepartmentTree", {
	extend : "Ext.tree.Panel",
	alias : "widget.departmenttree",
	title : "部门",
	store : Ext.create("app.store.Departments"),
	rootVisible : false,
	tools : [ {
		type : 'expand',
		reference : 'expandtool',
		handler : 'expandTreeMenu',
		tooltip : '展开所有菜单项'
	}, {
		type : 'collapse',
		reference : 'collapsetool',
		handler : 'collapseTreeMenu',
		tooltip : '折叠所有菜单项'
	} ],
	dockedItems : [ {
		xtype : "toolbar",
		dock : "left",
		items : [ {
			xtype : "button",
			text : "添加同级"
		}, {
			xtype : "button",
			text : "添加子级"
		}, {
			xtype : "button",
			text : "修改"
		}, {
			xtype : "button",
			text : "删除"
		} ]
	} ]
});