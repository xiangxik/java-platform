Ext.define("app.view.module.ModuleList", {
	extend : "Ext.grid.Panel",
	alias : "widget.userlist",
	requires : [ "app.view.module.ModuleModel" ],
	title : "模块列表",
	viewModel : "module",
	forceFit : true,
	columns : [ {
		xtype : 'rownumberer'
	}, {
		text : "模块名称",
		dataIndex : "name"
	}, {
		text : "代号",
		dataIndex : "code"
	}, {
		text : "版本",
		dataIndex : "version"
	}, {
		text : "类型",
		width : 60,
		renderer : function(data, metadata, record, rowIndex, columnIndex, store) {
			return record.get("type") === "Application" ? "主应用" : (record.get("type") === "Extension" ? "扩展" : "模块");
		}
	}, {
		text : "创建人",
		dataIndex : "createdBy"
	}, {
		text : "创建时间",
		xtype : 'datecolumn',
		format : 'Y年m月d日',
		dataIndex : "createdDate"
	}, {
		text : "修改人",
		dataIndex : "lastModifiedBy"
	}, {
		text : "修改时间",
		xtype : 'datecolumn',
		format : 'Y年m月d日',
		dataIndex : "lastModifiedDate"
	} ],
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