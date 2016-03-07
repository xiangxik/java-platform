Ext.define("app.view.mall.product.Product", {
	extend : "Ext.grid.Panel",
	alias : "widget.product",
	requires : [ "app.view.mall.product.ProductController", "app.view.mall.product.ProductModel" ],
	title : "产品列表",
	controller : "product",
	viewModel : "product",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "编号",
		dataIndex : "sn"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "商品分类",
		dataIndex : "productCategoryName"
	}, {
		text : "销售价",
		dataIndex : "price"
	}, {
		text : "成本价",
		dataIndex : "cost"
	}, {
		text : "库存",
		dataIndex : "stock"
	}, {
		text : "是否上架",
		dataIndex : "isMarketable",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "创建日期",
		xtype : 'datecolumn',
		format : 'Y年m月d日 H:i:s',
		dataIndex : "createdDate",
		width: 140
	}, {
		text : "操作",
		xtype : "actioncolumn",
		width : 80,
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