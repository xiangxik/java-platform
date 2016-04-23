Ext.define("app.view.mall.product.ProductCategoryModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.productcategory",
	stores : {
		tree : Ext.create("app.store.ProductCategories")
	}
});