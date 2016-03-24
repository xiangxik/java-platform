Ext.define("app.mdm.view.device.DeviceList", {
	extend : "Ext.grid.Panel",
	alias : "widget.device",
	requires : [ "app.mdm.view.device.DeviceController", "app.mdm.view.device.DeviceModel", "app.ux.grid.SearchPanel" ],
	title : "设备列表",
	controller : "device",
	viewModel : "device",
	forceFit : true,
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "设备名称",
		dataIndex : "name"
	}, {
		text : "UDID",
		dataIndex : "udid",
		width : 250
	}, {
		text : "手机号码",
		dataIndex : "phoneNumber"
	}, {
		text : "SIM运营商",
		dataIndex : "sIMCarrierNetwork"
	}, {
		text : "产品名称",
		dataIndex : "productName"
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
		}, {
			iconCls : "Applicationviewdetail actionColumnIcon",
			tooltip : "查看",
			handler : "onRowView"
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
		xtype : "searchpanel",
		dock : "top",
		bind : {
			store : "{list}"
		},
		queryItems : [ {
			fieldLabel : "设备名称",
			xtype : "textfield",
			name : "name"
		}, {
			fieldLabel : "UDID",
			xtype : "textfield",
			name : "udid"
		},{
			fieldLabel : "手机号码",
			xtype : "textfield",
			name : "phoneNumber"
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