Ext.define("app.view.role.RoleModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.role",
	stores : {
		page : Ext.create("app.store.Roles")
	},
	data : {
		name : "dd"
	}
});
