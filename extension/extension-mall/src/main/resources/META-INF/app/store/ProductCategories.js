Ext.define("app.store.ProductCategories", {
	extend : "app.store.TreeStore",
	alias : "store.menus",
	model : "app.model.ProductCategory",
	url : Ext.ctx + "/admin/productCategory/tree"
});