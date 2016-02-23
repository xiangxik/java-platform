Ext.define("app.view.user.UserForm", {
	extend : "Ext.form.Panel",
	alias : "widget.userform",
	requires : [ "app.view.user.UserController", "app.view.user.UserModel", "app.ux.form.htmleditor.Table" ],
	controller : "user",
	viewModel : "user",
	bodyPadding : 5,
	width : 480,
	height : 320,
	url : Ext.ctx + "/admin/user/save",
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
		xtype : "fieldset",
		id : "baseSet",
		title : "基本信息",
		defaultType : "textfield",
		defaults : {
			anchor : "100%"
		},
		layout : "anchor",
		items : [ {
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
		} ]
	}, {
		xtype : "fieldset",
		title : "头像",
		defaults : {
			anchor : "100%"
		},
		layout : "anchor",
		items : [ {
			xtype : "filefield",
			name : "avatar",
			hideLabel : true,
			allowBlank : true,
			buttonText : "选择文件"

		} ]
	}, {
		xtype : "fieldset",
		title : "头像",
		defaults : {
			anchor : "100%"
		},
		layout : "anchor",
		items : [ {
			xtype : "htmleditor",
			plugins : [ {
				ptype : "htmleditortable"
			} ],
			name : "introduce",
			hideLabel : true

		} ]
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
			this.getComponent("baseSet").insert(1, {
				fieldLabel : "密码",
				name : "password",
				inputType : "password",
				allowBlank : false
			});
		}
	}

});