Ext.define("app.view.menu.Menu", {
	extend : "Ext.panel.Panel",
	alias : "widget.menuview",
	layout : "border",
	requires : [ "app.view.menu.MenuController", "app.view.menu.MenuModel", "app.view.menu.MenuTree", "app.view.menu.MenuForm" ],
	controller : "menu",
	viewModel : "menu",
	border : false,
	frame : false,
	items : [ {
		region : "center",
		xtype : "menutree",
		border : false,
		frame : false
	}, {
		region : "east",
		xtype : "menuform",
		title : "菜单详情",
		width : 300,
		border : false,
		frame : false,
		collapsible : true,
		collapsed : true,
		split : true
	} ]
});