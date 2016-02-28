Ext.define("app.view.role.RoleModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.role",
	stores : {
		list : Ext.create("app.store.Roles"),
		userlist : Ext.create("app.store.Users"),
		userselectlist : Ext.create("app.store.Users")
	}
});