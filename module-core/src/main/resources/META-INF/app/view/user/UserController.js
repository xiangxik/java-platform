Ext.define('app.view.user.UserController', {
	extend : 'Ext.app.ViewController',

	alias : 'controller.user',

	onRowEdit : function(grid, rowIndex, colIndex) {
		console.log(rowIndex);
		console.log(colIndex);
	},

	onUserAdd : function(button) {
		Ext.create('Ext.window.Window', {
			title : 'Hello',
			layout : 'fit',
			items : Ext.create("app.view.user.UserForm", {})
		}).show();
	},

	onResetUserForm : function(button) {console.log(button);
		button.up('form').getForm().reset();
	}
});