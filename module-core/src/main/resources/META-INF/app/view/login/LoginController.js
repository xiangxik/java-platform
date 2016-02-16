Ext.define('app.view.login.LoginController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.login',

	onLoginSubmit : function(button) {

		var form = button.up('window').down("form").getForm();
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.alert('Success', action.result.msg);
					this.getView().destroy();

					Ext.create({
						xtype : 'app-main'
					});

				},
				failure : function(form, action) {
					Ext.Msg.alert('Failed', action.result.msg);
				}
			});
		} else {
			Ext.Msg.error("错误", "请正确填写");
		}
	}
});