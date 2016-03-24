Ext.define("app.mdm.view.app.AppList", {
	extend : "Ext.grid.Panel",
	alias : "widget.app",
	requires : [ "app.mdm.view.app.AppController", "app.mdm.view.app.AppModel", "app.ux.grid.SearchPanel" ],
	title : "应用列表",
	controller : "app",
	viewModel : "app",
	forceFit : true,
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "应用名称",
		dataIndex : "name"
	}, {
		text : "包标识",
		dataIndex : "bundleId"
	}, {
		text : "trackId",
		dataIndex : "trackId"
	}, {
		text : "版本",
		dataIndex : "version"
	}, {
		text : "文件大小",
		dataIndex : "fileSizeBytes"
	}, {
		text : "创建日期",
		xtype : 'datecolumn',
		format : 'Y年m月d日 H:i:s',
		dataIndex : "createdDate",
		width : 140
	}, {
		text : "操作",
		xtype : "actioncolumn",
		width : 80,
		items : [ {
			iconCls : "Pencil actionColumnIcon",
			tooltip : "编辑",
			handler : "onRowEdit"
		},{
			iconCls : "Applicationviewdetail actionColumnIcon",
			tooltip : "安装",
			handler : "onRowInstall"
		}, {
			iconCls : "Delete actionColumnIcon",
			tooltip : "删除",
			handler : "onRowDelete"
		} ]
	} ],
	tbar : [ {
		xtype : "button",
		text : "添加企业应用",
		iconCls : "Add",
		handler : "onAddEnterpriseApp"
	}, {
		xtype : "button",
		text : "添加AppStore应用",
		iconCls : "Add",
		handler : "onAddAppStoreApp"
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
		xtype : "searchpanel",
		dock : "top",
		bind : {
			store : "{list}"
		},
		queryItems : [ {
			fieldLabel : "应用名称",
			xtype : "textfield",
			name : "name"
		}, {
			fieldLabel : "包标识",
			xtype : "textfield",
			name : "bundleId"
		}, {
			fieldLabel : "trackId",
			xtype : "textfield",
			name : "trackId"
		} ]
	}, {
		xtype : "pagingtoolbar",
		dock : "bottom",
		displayInfo : true,
		bind : {
			store : "{list}"
		}
	} ]
});