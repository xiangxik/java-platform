Ext.define("app.view.login.Login", {
	extend : "Ext.window.Window",
	xtype : "login",
	requires : [ "app.view.login.LoginController" ],
	controller : "login",
	title : "登录窗口v1.1",
	width : 422,
	height : 260,
	closable : false,
	resizable : false,
	draggable : true,
	modal : true,
	iconCls : "Lockkey",
	layout : "border",
	bodyPadding : 5,
	buttonAlign : "center",
	autoShow : true,
	plain : false,
	items : [ {
		xtype : "image",
		src : Ext.ctx + "/assets/platform/images/login_header.jpg",
		region : "north",
		height : 60
	}, {
		region : "center",
		xtype : "form",
		url : Ext.ctx + "/admin",
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
		baseParams : {
			task : "login"
		},
		items : [ {
			xtype : 'textfield',
			name : 'username',
			fieldLabel : '用户名',
			allowBlank : false
		}, {
			xtype : 'textfield',
			name : 'password',
			inputType : 'password',
			fieldLabel : '密码',
			allowBlank : false
		}, {
			xtype : "fieldcontainer",
			fieldLabel : '验证码',
			layout : {
				type : "hbox",
				align : "left"
			},
			items : [ {
				xtype : 'textfield',
				name : 'validateCode'
			} ]
		}, {
			xtype : 'checkbox',
			name : 'rememberMe',
			fieldLabel : "记住我"
		} ]
	} ],
	buttons : [ {
		text : "登录",
		handler : "onLoginSubmit"
	}, {
		text : "找回密码"
	} ]
});