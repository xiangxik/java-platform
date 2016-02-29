Ext.define("app.view.user.PasswordForm", {
	extend : "Ext.form.Panel",
	alias : "widget.passwordform",
	requires : [ "app.view.user.UserController", "app.view.user.UserModel" ],
	controller : "user",
	viewModel : "user",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/user/changePassword",
	border : false,
	frame : false,
	layout : "anchor",
	defaults : {
		anchor : "100%"
	},
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 60
	},
	defaultType : "textfield",
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		fieldLabel : "旧密码",
		name : "oldPassword",
		inputType : "password",
		allowBlank : false
	}, {
		fieldLabel : "新密码",
		name : "newPassword",
		inputType : "password",
		allowBlank : false
	}, {
		fieldLabel : "重复密码",
		name : "repeatPassword",
		inputType : "password",
		allowBlank : false
	} ],
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onPasswordChange"
	} ]
});