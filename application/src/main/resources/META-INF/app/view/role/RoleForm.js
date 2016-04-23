Ext.define("app.view.role.RoleForm", {
	extend : "Ext.form.Panel",
	alias : "widget.roleform",
	requires : [ "app.view.role.RoleController", "app.view.role.RoleModel" ],
	controller : "role",
	viewModel : "role",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/role/save",
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
		fieldLabel : "名称",
		name : "name",
		allowBlank : false
	}, {
		fieldLabel : "代号",
		name : "code",
		allowBlank : false
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]

});