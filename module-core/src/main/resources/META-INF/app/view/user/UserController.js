Ext.define("app.view.user.UserController", {
	extend : "Ext.app.ViewController",
	alias : "controller.user",

	onAdd : function() {
		if (!this.formWindow) {
			this.formWindow = Ext.create("Ext.window.Window", {
				title : "新建用户",
				modal : true,
				layout : "fit",
				closeAction : "hide"
			});
		}

		this.formWindow.removeAll(true);
		this.formWindow.add(Ext.create("app.view.user.UserForm"));
		this.formWindow.show();
	},

	onFormSave : function(button) {
		var form = button.up("form").getForm();
		var window = button.up("window");

		var store = this.getViewModel().getStore("list");

		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					window.hide();
					store.reload();
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	}

});