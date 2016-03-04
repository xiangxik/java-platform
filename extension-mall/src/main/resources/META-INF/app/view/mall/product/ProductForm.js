Ext.define("app.view.mall.product.ProductForm", {
	extend : "Ext.form.Panel",
	alias : "widget.productform",
	requires : [ "app.view.mall.product.ProductController", "app.view.mall.product.ProductModel", "Ext.ux.TreePicker",
			"app.ux.form.htmleditor.Table", "app.ux.form.htmleditor.Image" ],
	controller : "product",
	viewModel : "product",
	url : Ext.ctx + "/admin/product/save",
	bodyPadding : 5,
	border : false,
	frame : false,
	scrollable : true,
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	defaultType : "textfield",
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		xtype : "fieldset",
		title : "基本信息",
		defaultType : "textfield",
		collapsible : true,
		items : [ {
			fieldLabel : "商品分类",
			name : "productCategory",
			xtype : "treepicker",
			displayField : "name",
			valueField : "id",
			rootVisible : false,
			allowBlank : false,
			store : Ext.create("app.store.ProductCategories", {
				rootVisible : false
			})
		}, {
			fieldLabel : "名称",
			name : "name",
			allowBlank : false
		}, {
			fieldLabel : "编号",
			name : "sn"
		}, {
			fieldLabel : "销售价",
			name : "price",
			allowBlank : false
		}, {
			fieldLabel : "成本价",
			name : "cost"
		}, {
			fieldLabel : "市场价",
			name : "marketPrice"
		}, {
			fieldLabel : "展示图片",
			name : "image"
		}, {
			fieldLabel : "单位",
			name : "unit"
		}, {
			fieldLabel : "重量",
			name : "weight"
		}, {
			fieldLabel : "库存",
			name : "stock"
		}, {
			fieldLabel : "库存备注",
			name : "stockMemo"
		}, {
			fieldLabel : "赠送积分",
			name : "point"
		}, {
			xtype : "fieldcontainer",
			fieldLabel : "设置",
			layout : {
				type : "hbox",
				align : "left"
			},
			defaultType : "checkbox",
			items : [ {
				boxLabel : "是否上架",
				hideLabel : true,
				name : "isMarketable"
			}, {
				boxLabel : "是否列出",
				hideLabel : true,
				name : "isList"
			}, {
				boxLabel : "是否置顶",
				hideLabel : true,
				name : "isTop"
			}, {
				boxLabel : "是否赠品",
				hideLabel : true,
				name : "isGift"
			} ]
		}, {
			fieldLabel : "备注",
			name : "memo"
		}, {
			fieldLabel : "搜索关键字",
			name : "keyword"
		}, {
			fieldLabel : "页面标题",
			name : "seoTitle"
		}, {
			fieldLabel : "页面关键字",
			name : "seoKeywords"
		}, {
			fieldLabel : "页面描述",
			name : "seoDescription"
		} ]
	}, {
		xtype : "fieldset",
		title : "商品介绍",
		collapsible : true,
		items : [ {
			xtype : "htmleditor",
			name : "introduction",
			hideLabel : true,
			plugins : [ {
				ptype : "htmleditortable"
			}, {
				ptype : "htmleditorimage"
			} ]
		} ]
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});