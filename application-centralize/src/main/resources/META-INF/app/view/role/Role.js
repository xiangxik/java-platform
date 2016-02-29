Ext.define("app.view.role.Role", {
	extend : "Ext.panel.Panel",
	alias : "widget.role",
	layout : "border",
	requires : [ "app.view.role.RoleController", "app.view.role.RoleModel", "app.view.role.RoleList" ],
	controller : "role",
	viewModel : "role",
	title : "角色管理",
	border : false,
	frame : false,
	items : [ {
		region : "center",
		xtype : "rolelist",
		border : false,
		frame : false
	} ]
});