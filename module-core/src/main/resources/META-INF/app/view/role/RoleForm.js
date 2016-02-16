Ext.define("app.view.role.RoleForm", {
	extend : "Ext.form.Panel",
	alias : "widget.roleform",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/role/save",
	requires : [ "app.view.role.RoleController", "app.view.role.RoleModel" ],
	controller : "role",
	viewModel : "role",
	border : false,
	frame : false,
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
	buttons : [ {
		text : "保存",
		formBind : true,
		disabled : true,
		handler : "onFormSave"
	} ]
});