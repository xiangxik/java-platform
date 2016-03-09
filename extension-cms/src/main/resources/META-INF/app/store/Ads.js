Ext.define("app.store.Ads", {
	extend : "app.store.PageStore",
	alias : "store.ads",
	model : "app.model.Ad",
	url : Ext.ctx + "/admin/ad/list"
});