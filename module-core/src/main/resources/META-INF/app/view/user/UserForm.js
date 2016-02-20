Ext.define("app.view.user.UserForm", {
	extend : "Ext.form.Panel",
	alias : "widget.userform",
	requires : [ "app.view.user.UserController", "app.view.user.UserModel" ],
	controller : "user",
	viewModel : "user",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/user/save",
	border : false,
	frame : false,
	layout : "anchor",
	defaults : {
		anchor : "96%"
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
		fieldLabel : "账号",
		name : "username",
		allowBlank : false
	}, {
		fieldLabel : "邮箱",
		name : "email",
		vtype : "email"
	}, {
		fieldLabel : "手机",
		name : "mobile"
	}, {
		fieldLabel : "姓名",
		name : "name",
		allowBlank : false
	} ],
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ],
	initComponent : function() {
		var me = this;
		this.callParent(arguments);

		if (this.modelId) {
			app.model.User.load(this.modelId, {
				success : function(user) {
					me.getForm().setValues(user.data);
				}
			});
		} else {
			this.insert(2, {
				fieldLabel : "密码",
				name : "password",
				inputType : "password",
				allowBlank : false
			});
		}
	}

});