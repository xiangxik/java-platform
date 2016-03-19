Ext.define("app.store.Modules", {
	extend : "app.store.PageStore",
	alias : "store.modules",
	model : "app.model.Module",
	url : Ext.ctx + "/admin/module/list"
});