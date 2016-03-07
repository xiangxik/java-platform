Ext.define("app.model.ProductCategory", {
	extend : "app.model.Entity",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "iconPath",
		type : "string"
	}, {
		name : "sortNo",
		type : "int"
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
		name : "parent",
		mapping : "parent.id",
		type : "int"
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.model.ProductCategory.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/productCategory/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});