Ext.define("app.store.Roles", {
	extend : "app.store.PageStore",
	alias : "store.roles",
	model : "app.model.Role",
	url : Ext.ctx + "/admin/role/list"
});