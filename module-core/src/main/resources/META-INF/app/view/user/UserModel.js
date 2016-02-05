Ext.define("app.view.user.UserModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.user",
	requires : [ "app.store.Users" ],
	data : {
		listStore : {
			type : "users"
		}
	}
});