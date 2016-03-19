Ext.define("app.view.cms.AdPosition", {
	extend : "Ext.grid.Panel",
	alias : "widget.adPosition",
	requires : [ "app.view.cms.AdPositionController", "app.view.cms.AdPositionModel" ],
	title : "广告位列表",
	controller : "adPosition",
	viewModel : "adPosition",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "宽度",
		dataIndex : "width"
	}, {
		text : "高度",
		dataIndex : "height"
	}, {
		text : "描述",
		dataIndex : "description"
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