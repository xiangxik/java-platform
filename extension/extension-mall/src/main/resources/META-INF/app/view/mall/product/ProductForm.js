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
				maxLength: 100,
				allowBlank : false
			}, {
				fieldLabel : "简要描述",
				xtype:"textareafield",
				name : "description",
				maxLength: 250
			}, {
				fieldLabel : "编号",
				maxLength: 50,
				name : "sn"
			}, {
				fieldLabel : "销售价",
				name : "price",
				xtype: "numberfield",
				minValue: 0,
				allowBlank : false
			}, {
				fieldLabel : "成本价",
				xtype: "numberfield",
				minValue: 0,
				name : "cost"
			}, {
				fieldLabel : "市场价",
				xtype: "numberfield",
				minValue: 0,
				name : "marketPrice"
			}, {
				fieldLabel : "展示图片",
				xtype : "imagefield",
				name : "image"
			}, {
				fieldLabel : "单位",
				name : "unit",
				maxLength: 100
			}, {
				fieldLabel : "重量",
				name : "weight",
				xtype: "numberfield",
				minValue: 0
			}, {
				fieldLabel : "库存",
				name : "stock",
				xtype: "numberfield",
				minValue: 0
			}, {
				fieldLabel : "库存备注",
				name : "stockMemo",
				maxLength: 100
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
				name : "point",
				xtype: "numberfield",
				allowBlank : false,
				minValue: 0
			}, {
				fieldLabel : "备注",
				name : "memo",
				maxLength: 100
			}, {
				fieldLabel : "搜索关键字",
				name : "keyword",
				maxLength: 100
			}, {
				fieldLabel : "页面标题",
				name : "seoTitle",
				maxLength: 100
			}, {
				fieldLabel : "页面关键字",
				name : "seoKeywords",
				maxLength: 100
			}, {
				fieldLabel : "页面描述",
				name : "seoDescription",
				maxLength: 100
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