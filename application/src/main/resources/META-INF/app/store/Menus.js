Ext.define("app.store.Menus", {
	extend : "app.store.TreeStore",
	alias : "store.menus",
	model : "app.model.Menu",
	url : Ext.ctx + "/admin/menu/tree"
});