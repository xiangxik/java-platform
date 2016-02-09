Ext.define("app.view.user.UserForm", {
	extend : "Ext.form.Panel",
	alias : "widget.userform",
	bodyPadding : 5,
	url : "ab",

	requires : [ "app.view.user.UserController", "app.view.user.UserModel" ],
	controller : 'user',
	viewModel : 'user',

	border : false,
	frame : false,
	// The fields
	defaultType : 'textfield',
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		fieldLabel : 'First Name',
		name : 'first',
		allowBlank : false
	}, {
		fieldLabel : 'Last Name',
		name : 'last',
		allowBlank : false
	}, {
		xtype : "htmleditor",
		name : "html"
	}, {
		xtype : "displayfield",
		name : "nnn",
		value : "whenling.com",
		fieldLabel : "网站"
	}, {
		xtype : "fieldset",
		title : "组合",
		collapsible : true,
		checkboxToggle : true,
		defaultType : "textfield",
		defaults : {
			anchor : "95%",
			msgTarget : "side"
		},
		layout : "anchor",
		items : [ {
			fieldLabel : 'Last Name',
			name : 'last1',
			allowBlank : false
		}, {
			fieldLabel : 'Last Name',
			name : 'last2',
			allowBlank : false
		}, {
			xtype : "fieldcontainer",
			combineLabels : true,
			combineErrors : true,
			layout : {
				type : "hbox",
				align : "left"
			},
			defaultType : "textfield",
			items : [ {
				fieldLabel : 'test',
				name : 'last2',
				allowBlank : false
			}, {
				fieldLabel : 'test',
				name : 'last3',
				allowBlank : false
			} ]
		}, {
			xtype : "filefield",
			fieldLabel : "文件",
			name : "filess",
			buttonText : "选择文件"
		} ]
	} ],

	// Reset and Submit buttons
	buttons : [ {
		text : 'Reset',
		handler : "onResetUserForm"
	}, {
		text : 'Submit',
		formBind : true, // only enabled once the form is valid
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				form.submit({
					success : function(form, action) {
						Ext.Msg.alert('Success', action.result.msg);
					},
					failure : function(form, action) {
						Ext.Msg.alert('Failed', action.result.msg);
					}
				});
			}
		}
	} ]
});