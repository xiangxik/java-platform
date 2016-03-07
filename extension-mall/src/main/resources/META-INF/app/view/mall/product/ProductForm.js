Ext.define("app.view.mall.product.ProductForm", {
	extend : "Ext.form.Panel",
	alias : "widget.productform",
	requires : [ "app.view.mall.product.ProductController", "app.view.mall.product.ProductModel", "Ext.ux.TreePicker",
			"app.ux.form.htmleditor.Table", "app.ux.form.htmleditor.Image", "app.ux.form.Image" ],
	controller : "product",
	viewModel : "product",
	border : false,
	frame : false,
	url : Ext.ctx + "/admin/product/save",
	layout : "fit",
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	items : [ {
		xtype : "tabpanel",
		border : false,
		frame : false,
		layout : "fit",
		items : [ {
			title : "基本信息",
			bodyPadding : 5,
			border : false,
			frame : false,
			scrollable : true,
			layout : 'anchor',
			defaults : {
				anchor : '90%'
			},
			defaultType : "textfield",
			items : [ {
				xtype : "hiddenfield",
				name : "id"
			}, {
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
				xtype : "imagefield",
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
				fieldLabel : "赠送积分",
				name : "point"
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
			title : "商品介绍",
			border : false,
			frame : false,
			bodyPadding : 5,
			layout : "fit",
			items : [ {
				xtype : "htmleditor",
				name : "introduction",
				border : false,
				frame : false,
				hideLabel : true,
				plugins : [ {
					ptype : "htmleditortable"
				}, {
					ptype : "htmleditorimage"
				} ]
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