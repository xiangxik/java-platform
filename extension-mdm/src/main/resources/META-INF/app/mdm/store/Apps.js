Ext.define("app.mdm.store.Apps", {
	extend : "app.store.PageStore",
	alias : "store.apps",
	model : "app.mdm.model.App",
	url : Ext.ctx + "/admin/app/list"
});