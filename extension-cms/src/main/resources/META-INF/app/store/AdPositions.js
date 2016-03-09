Ext.define("app.store.AdPositions", {
	extend : "app.store.PageStore",
	alias : "store.adPositions",
	model : "app.model.AdPosition",
	url : Ext.ctx + "/admin/adPosition/list"
});