Ext.define("app.view.mall.product.ProductModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.product",
	stores : {
		list : Ext.create("app.store.Products")
	}
});