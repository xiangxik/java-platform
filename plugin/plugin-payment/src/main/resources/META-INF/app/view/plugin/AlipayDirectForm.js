Ext.define("app.view.plugin.AlipayDirectForm", {
	extend : "Ext.form.Panel",
	requires : [ "app.view.plugin.PaymentPluginController", "app.view.plugin.PaymentPluginModel", "app.ux.form.Image" ],
	controller : "paymentplugin",
	viewModel : "paymentplugin",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/payment/alipay_direct/update",
	border : false,
	frame : false,
	scrollable : true,
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
		fieldLabel : "排序号",
		xtype : "numberfield",
		allowDecimals : false,
		name : "sortNo"
	}, , {
		fieldLabel : "启用",
		name : "isEnabled",
		xtype : "checkbox"
	} ],
	listeners : {
		beforerender : function(form, opts) {
			var record = form.getRecord();
			var attributes = record.get("attributes");
			form.insert(1, [ {
				fieldLabel : "名称",
				name : "attributes['paymentName']",
				allowBlank : false,
				value : attributes.paymentName
			}, {
				fieldLabel : "合作者身份",
				name : "attributes['partner']",
				allowBlank : false,
				value : attributes.partner
			}, {
				fieldLabel : "安全校验码",
				name : "attributes['key']",
				allowBlank : false,
				value : attributes.key
			}, {
				fieldLabel : "手续费类型",
				xtype : "fieldcontainer",
				defaultType : "radiofield",
				allowBlank : false,
				layout : "hbox",
				items : [ {
					boxLabel : "按比例收费",
					name : "attributes['feeType']",
					inputValue : "scale",
					margin : "0 12 0 0",
					checked : attributes.feeType == "scale"
				}, {
					boxLabel : "固定收费",
					name : "attributes['feeType']",
					inputValue : "fixed",
					checked : attributes.feeType == "fixed"
				} ]
			}, {
				fieldLabel : "手续费",
				name : "attributes['fee']",
				allowBlank : false,
				value : attributes.fee
			}, {
				fieldLabel : "logo",
				xtype : "imagefield",
				name : "attributes['logo']",
				value : attributes.logo
			}, {
				fieldLabel : "描述",
				xtype : "textareafield",
				name : "attributes['description']",
				maxLength : 250,
				value : attributes.description
			} ]);
		}
	},
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});