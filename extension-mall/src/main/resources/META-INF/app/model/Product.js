Ext.define("app.model.Product", {
	extend : "app.model.Entity",
	fields : [ {
		name : "sn",
		type : "string"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "fullName",
		type : "string"
	}, {
		name : "price",
		type : "number"
	}, {
		name : "cost",
		type : "number"
	}, {
		name : "introduction",
		type : "string"
	}, {
		name : "stock",
		type : "int"
	}, {
		name : "stockMemo",
		type : "string"
	}, {
		name : "memo",
		type : "string"
	}, {
		name : "image",
		type : "string"
	}, {
		name : "unit",
		type : "string"
	}, {
		name : "weight",
		type : "int"
	}, {
		name : "marketPrice",
		type : "number"
	}, {
		name : "point",
		type : "int"
	}, {
		name : "isMarketable",
		type : "boolean"
	}, {
		name : "isList",
		type : "boolean"
	}, {
		name : "isTop",
		type : "boolean"
	}, {
		name : "isGift",
		type : "boolean"
	}, {
		name : "createdDate",
		type : "date"
	}, {
		name : "lastModifiedDate",
		type : "date"
	}, {
		name : "keyword",
		type : "string"
	}, {
		name : "seoTitle",
		type : "string"
	}, {
		name : "seoKeywords",
		type : "string"
	}, {
		name : "seoDescription",
		type : "string"
	}, {
		name : "productCategoryName",
		mapping : "productCategory.name"
	}, {
		name : "productCategory",
		mapping : "productCategory.id",
		reference : "app.model.ProductCategory"
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.model.Product.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/product/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});