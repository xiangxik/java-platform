Ext.define("app.store.Roles", {
	extend : "app.ux.PageStore",
	alias : "store.roles",
	model : "app.model.Role",
	url : Ext.ctx + "/admin/role/list",
	extraParams : {
		paths : [ "id", "name", "code" ]
	}
})