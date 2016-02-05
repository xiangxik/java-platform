Ext.define("app.store.Users", {
	extend : "app.ux.PageStore",
	alias : "store.users",
	model : "app.model.User",
	url : Ext.ctx + "/admin/user/list",
	extraParams : {
		paths : [ "name", "email" ]
	}
})