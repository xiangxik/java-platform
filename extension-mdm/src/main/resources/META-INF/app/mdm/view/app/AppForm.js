Ext.define("app.mdm.view.app.AppForm", {
	extend : "Ext.form.Panel",
	alias : "widget.mdmappform",
	requires : [ "app.mdm.view.app.AppController", "app.mdm.view.app.AppModel", "app.ux.form.Image" ],
	controller : "app",
	viewModel : "app",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/app/save",
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
		fieldLabel : "应用名称",
		name : "name",
		allowBlank : false
	}, {
		fieldLabel : "图标60x60",
		xtype : "imagefield",
		name : "icon60"
	}, {
		fieldLabel : "图标100x100",
		xtype : "imagefield",
		name : "icon100"
	}, {
		fieldLabel : "图标512x512",
		xtype : "imagefield",
		name : "icon512"
	}, {
		fieldLabel : "包标识",
		name : "bundleId"
	}, {
		fieldLabel : "版本",
		name : "version"
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});