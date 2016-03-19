Ext.define("app.view.user.UserForm", {
	extend : "Ext.form.Panel",
	alias : "widget.userform",
	requires : [ "app.view.user.UserController", "app.view.user.UserModel" ],
	controller : "user",
	viewModel : "user",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/user/saveBy",
	border : false,
	frame : false,
	scrollable : true,
	layout : "auto",
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	defaultType : "textfield",
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		fieldLabel : "账号",
		name : "username",
		allowBlank : false
	}, {
		fieldLabel : "姓名",
		name : "name",
		allowBlank : false
	}, {
		fieldLabel : "邮箱",
		name : "email",
		vtype : "email"
	}, {
		fieldLabel : "手机",
		name : "mobile",
		regex : /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|13\d{9})$/
	}, {
		fieldLabel : "性别",
		xtype : "fieldcontainer",
		defaultType : "radiofield",
		layout : "hbox",
		items : [ {
			boxLabel : "男",
			name : "sex",
			inputValue : "male",
			margin : "0 12 0 0"
		}, {
			boxLabel : "女",
			name : "sex",
			inputValue : "female"
		} ]
	}, {
		fieldLabel : "生日",
		name : "birthday",
		xtype : "datefield",
		format : "Y-m-d",
		maxValue : new Date()
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});