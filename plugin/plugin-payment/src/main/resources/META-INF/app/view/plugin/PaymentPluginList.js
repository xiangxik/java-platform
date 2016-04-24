Ext.define("app.view.plugin.PaymentPluginList", {
	extend : "Ext.grid.Panel",
	alias : "widget.paymentpluginlist",
	requires : [ "app.view.plugin.PaymentPluginController", "app.view.plugin.PaymentPluginModel" ],
	title : "支付插件",
	controller : "paymentplugin",
	viewModel : "paymentplugin",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "版本",
		dataIndex : "version"
	}, {
		text : "排序号",
		dataIndex : "sortNo"
	}, {
		text : "是否已安装",
		dataIndex : "isInstalled",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "是否已启用",
		dataIndex : "isEnabled",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "操作",
		xtype : "actioncolumn",
		width : 80,
		items : [ {
			iconCls : "Applicationadd actionColumnIcon",
			tooltip : "安装",
			handler : "onRowInstall"
		}, {
			iconCls : "Applicationdelete actionColumnIcon",
			tooltip : "卸载",
			handler : "onRowUninstall"
		}, {
			iconCls : "Pencil actionColumnIcon",
			tooltip : "编辑",
			handler : "onRowEdit"
		} ]
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