Ext.define("app.mdm.view.device.DeviceSelection", {
	extend : "Ext.window.Window",
	requires : [ "app.ux.grid.SearchPanel" ],
	layout : "fit",
	width : 960,
	height : 480,
	modal : true,
	initComponent : function() {
		var me = this;
		var store = Ext.create("app.mdm.store.Devices");
		me.items = [ {
			xtype : "gridpanel",
			forceFit : true,
			border : false,
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
			} ],
			selType : "checkboxmodel",
			multiSelect : true,
			store : store,
			dockedItems : [ {
				xtype : "searchpanel",
				dock : "top",
				store : store,
				queryItems : [ {
					fieldLabel : "设备名称",
					xtype : "textfield",
					name : "name"
				}, {
					fieldLabel : "UDID",
					xtype : "textfield",
					name : "udid"
				}, {
					fieldLabel : "手机号码",
					xtype : "textfield",
					name : "phoneNumber"
				} ]
			} ]
		} ];

		this.callParent(arguments);
	},
	buttons : [ {
		text : "确定",
		handler : function() {
			var win = this.up("window");
			var records = win.down("gridpanel").getSelection();
			if (records.length == 0) {
				Ext.Msg.alert("提示", "您最少要选择一条数据");
			} else {
				win.handler(win, records);
			}
		}
	}, {
		text : "取消",
		handler : function() {
			this.up("window").close();
		}
	} ]

});