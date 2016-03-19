Ext.define("app.view.cms.AdList", {
	extend : "Ext.grid.Panel",
	alias : "widget.ad",
	requires : [ "app.view.cms.AdController", "app.view.cms.AdModel" ],
	title : "广告列表",
	controller : "ad",
	viewModel : "ad",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "标题",
		dataIndex : "title"
	}, {
		text : "广告位",
		dataIndex : "adPositionName"
	}, {
		text : "类型",
		width : 60,
		renderer : function(data, metadata, record, rowIndex, columnIndex, store) {
			return record.get("type") === "text" ? "文本" : (record.get("type") === "image" ? "图片" : (record.get("type") === "flash" ? "动画" : ""));
		}
	}, {
		text : "开始时间",
		xtype : 'datecolumn',
		format : 'Y年m月d日',
		dataIndex : "beginDate"
	}, {
		text : "结束时间",
		xtype : 'datecolumn',
		format : 'Y年m月d日',
		dataIndex : "endDate"
	}, {
		text : "排序号",
		dataIndex : "sortNo"
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