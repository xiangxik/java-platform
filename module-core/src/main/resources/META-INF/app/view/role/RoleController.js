Ext.define("app.view.role.RoleController", {
	extend : "Ext.app.ViewController",
	alias : "controller.role",
	onConfirm : function(choice) {
		if (choice === 'yes') {
			//
		}
	},
	onRoleAdd : function() {
		var form = Ext.create("app.view.role.RoleForm");
		form.getForm().setValues({
			name : "abcde",
			code : "dddwww"
		})
		Ext.create("Ext.window.Window", {
			title : "新建角色",
			layout : "fit",
			items : form
		}).show();
	}
});