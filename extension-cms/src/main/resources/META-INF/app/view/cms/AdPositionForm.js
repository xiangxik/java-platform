Ext.define("app.view.cms.AdPositionForm", {
	extend : "Ext.form.Panel",
	alias : "widget.adpositionform",
	requires : [ "app.view.cms.AdPositionController", "app.view.cms.AdModel" ],
	controller : "adPosition",
	viewModel : "adPosition",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/adPosition/save",
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
		fieldLabel : "宽度",
		name : "width",
		xtype : "numberfield",
		minValue : 0
	}, {
		fieldLabel : "高度",
		name : "height",
		xtype : "numberfield",
		minValue : 0
	}, {
		fieldLabel : "描述",
		name : "description"
	}, {
		fieldLabel : "模板",
		name : "template",
		xtype : "textarea"
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});