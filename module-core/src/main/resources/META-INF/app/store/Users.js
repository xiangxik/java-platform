Ext.define("app.store.Users", {
	extend : "app.store.PageStore",
	alias : "store.users",
	model : "app.model.User",
	url : Ext.ctx + "/admin/user/list"
});