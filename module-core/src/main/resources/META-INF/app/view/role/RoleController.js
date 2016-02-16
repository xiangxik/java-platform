Ext.define("app.view.role.RoleController", {
	extend : "Ext.app.ViewController",
	alias : "controller.role",
	onConfirm : function(choice) {
		if (choice === 'yes') {
			//
		}
	},

	refs : [ {
		ref : "list",
		xtype : "gridpanel"
	} ],

	onRoleAdd : function() {
		Ext.create("app.model.Role").load(1, {
			success : function(role) {
				var form = Ext.create("app.view.role.RoleForm");
				// form.getForm().setValues(role.data)
				Ext.create("Ext.window.Window", {
					title : "新建角色",
					modal : true,
					layout : "fit",
					items : form
				}).show();
			}
		})

	},
	onFormSave : function(button) {

		var form = button.up('form').getForm();
		var window = button.up("window");
		var store = this.getStore("page");
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.alert('Success', action.result.msg);
					window.close();
					store.load();

				},
				failure : function(form, action) {
					Ext.Msg.alert('Failed', action.result.msg);
				}
			});
		}

	}
});