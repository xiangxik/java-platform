Ext.define("app.view.user.Login", {
	extend : "Ext.window.Window",
	title : "登录",
	layout : "fit",
	items : {
		xtype : "form",
		url : 'save-form.php',

		bodyPadding : 5,
		// Fields will be arranged vertically, stretched to full width
		layout : 'anchor',
		defaults : {
			anchor : '100%'
		},

		// The fields
		defaultType : 'textfield',
		items : [ {
			xtype : "fieldset",
			collapsible : false,
			defaultType : "textfield",
			bodyPadding : 5,
			defaults : {
				anchor : "95%",
				msgTarget : "side"
			},
			layout : "anchor",
			items : [ {
				fieldLabel : 'First Name',
				name : 'first',
				allowBlank : false
			}, {
				fieldLabel : 'Last Name',
				name : 'last',
				allowBlank : false
			} ]
		} ],

		// Reset and Submit buttons
		buttons : [ {
			text : 'Reset',
			handler : function() {
				this.up('form').getForm().reset();
			}
		}, {
			text : 'Submit',
			formBind : true, // only enabled once the form is valid
			disabled : true,
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
	}
});