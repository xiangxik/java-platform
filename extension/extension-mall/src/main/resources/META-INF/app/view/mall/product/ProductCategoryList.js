Ext.define("app.view.mall.product.ProductCategoryList", {
	extend : "Ext.tree.Panel",
	alias : "widget.productcategory",
	requires : [ "app.view.mall.product.ProductCategoryController", "app.view.mall.product.ProductCategoryModel" ],
	controller : "productcategory",
	viewModel : "productcategory",
	rootVisible : false,
	forceFit : true,
	bind : {
		store : "{tree}"
	},
	columns : [ {
		xtype : "treecolumn",
		text : "名称",
		dataIndex : "name",
		flex : 3
	}, {
		xtype : "gridcolumn",
		text : "图标",
		dataIndex : "iconPath",
		renderer : function(value) {
			return "<img src='" + value + "' height='10px'>";
		},
		flex : 1
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