Ext.define("app.view.menu.MenuForm", {
	extend : "Ext.form.Panel",
	alias : "widget.menuform",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/menu/save",
	border : false,
	frame : false,
	layout : "anchor",
	defaults : {
		anchor : "96%"
	},
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 60
	},
	defaultType : "textfield",
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		fieldLabel : "名称",
		name : "text",
		allowBlank : false
	}, {
		fieldLabel : "代号",
		name : "code",
		allowBlank : false
	}, {
		fieldLabel : "图标样式",
		name : "iconCls"
	}, {
		fieldLabel : "视图",
		name : "view"
	}, {
		fieldLabel : "配置参数",
		name : "config"
	} ],
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});