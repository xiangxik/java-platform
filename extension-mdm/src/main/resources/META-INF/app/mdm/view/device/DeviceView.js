Ext.define("app.mdm.view.device.DeviceView", {
	extend : "Ext.form.Panel",
	requires : [ "app.mdm.view.device.DeviceController", "app.mdm.view.device.DeviceModel" ],
	controller : "device",
	viewModel : "device",
	border : false,
	frame : false,
	layout : "fit",
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	items : [ {
		xtype : "tabpanel",
		layout : "fit",
		border : false,
		frame : false,
		dockedItems : [ {
			xtype : "toolbar",
			items : [ {
				xtype : "button",
				text : "更新设备",
				iconCls : "Arrowrefresh",
				handler : "onDeviceInformation"
			}, {
				xtype : "button",
				text : "锁定设备",
				iconCls : "Lock",
				handler : "onDeviceLock"
			}, {
				xtype : "button",
				text : "清除密码",
				iconCls : "Keydelete",
				handler : "onClearPasscode"
			}, {
				xtype : "button",
				text : "擦写设备",
				iconCls : "Drivedelete",
				handler : "onEraseDevice"
			} ]
		} ],
		items : [ {
			title : "基本信息",
			bodyPadding : 5,
			border : false,
			frame : false,
			scrollable : true,
			layout : "anchor",
			defaults : {
				anchor : "90%"
			},
			defaultType : "displayfield",
			items : [ {
				xtype : "hiddenfield",
				name : "id"
			}, {
				fieldLabel : "设备名称",
				name : "name"
			}, {
				fieldLabel : "产品名称",
				name : "productName"
			}, {
				fieldLabel : "UDID",
				name : "udid"
			}, {
				fieldLabel : "操作系统版本",
				name : "oSVersion"
			}, {
				fieldLabel : "手机号码",
				name : "phoneNumber"
			}, {
				fieldLabel : "可用容量",
				name : "availableDeviceCapacity"
			}, {
				fieldLabel : "设备容量",
				name : "deviceCapacity"
			}, {
				fieldLabel : "运营商",
				name : "sIMCarrierNetwork"
			}, {
				fieldLabel : "IMEI",
				name : "imei"
			}, {
				fieldLabel : "序列号",
				name : "serialNumber"
			}, {
				fieldLabel : "构建版本",
				name : "buildVersion"
			}, {
				fieldLabel : "创建时间",
				name : "createdDate"
			}, {
				fieldLabel : "修改时间",
				name : "lastModifiedDate"
			} ]
		}, {
			title : "设备应用",
			border : false,
			frame : false,
			layout : "fit",
			items : [ {} ]
		}, {
			title : "设备配置",
			border : false,
			frame : false,
			layout : "fit",
			items : [ {} ]
		} ]
	} ]
});