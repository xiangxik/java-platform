Ext.define("app.store.Products", {
	extend : "app.store.PageStore",
	alias : "store.products",
	model : "app.model.Product",
	url : Ext.ctx + "/admin/product/list"
});