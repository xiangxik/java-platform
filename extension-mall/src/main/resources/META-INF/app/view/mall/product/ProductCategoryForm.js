Ext.define("app.view.mall.product.ProductCategoryForm", {
	extend : "Ext.form.Panel",
	alias : "widget.productcategoryform",
	requires : [ "app.view.mall.product.ProductCategoryController", "app.view.mall.product.ProductCategoryModel", "Ext.ux.TreePicker" ],
	controller : "productcategory",
	viewModel : "productcategory",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/productCategory/save",
	border : false,
	frame : false,
	layout : "auto",
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	defaultType : "textfield",
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		fieldLabel : "名称",
		name : "name",
		allowBlank : false
	}, {
		fieldLabel : "上级分类",
		name : "parent",
		xtype : "treepicker",
		displayField : "name",
		valueField : "id",
		rootVisible : false,
		store : Ext.create("app.store.ProductCategories", {
			rootVisible : false
		})
	}, {
		fieldLabel : "seo标题",
		name : "seoTitle"
	}, {
		fieldLabel : "seo关键字",
		name : "seoKeywords"
	}, {
		fieldLabel : "seo描述",
		name : "seoDescription"
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});