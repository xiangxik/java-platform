Ext.define("app.mdm.view.device.DeviceLock", {
	extend : "Ext.window.Window",
	xtype : "widget.devicelock",
	title : "锁定设备",
	width : 422,
	height : 220,
	closable : true,
	resizable : false,
	draggable : true,
	modal : true,
	iconCls : "Lock",
	layout : "fit",
	items : [ {
		xtype : "form",
		url : Ext.ctx + "/admin/command/lock",
		border : false,
		layout : 'anchor',
		defaults : {
			anchor : '90%',
			msgTarget : "side"
		},
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 60
		},
		bodyPadding : 5,
		waitMsgTarget : true,
		items : [ {
			xtype : 'textfield',
			name : 'pin',
			fieldLabel : 'PIN码'
		}, {
			xtype : 'textarea',
			name : 'message',
			fieldLabel : '显示信息'
		}, {
			xtype : 'textfield',
			name : 'phoneNumber',
			fieldLabel : '手机号码'
		} ],
		buttonAlign : "center",
		buttons : [ {
			text : "确定",
			formBind : true,
			handler : function() {
				var formPanel = this.up("form");
				var form = formPanel.getForm();
				var win = formPanel.up("window");

				if (form.isValid()) {
					form.submit({
						params : {
							id : win.getDevice().get("id")
						},
						success : function(form, action) {
							Ext.Msg.info("提示", "操作成功");
							win.close();
						},
						failure : function(form, action) {
							Ext.Msg.error("提示", "操作失败");
						}
					});
				}
			}
		} ]
	} ],
	plain : false,
	setDevice : function(device) {
		this.device = device;
	},
	getDevice : function() {
		return this.device;
	}
});