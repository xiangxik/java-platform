Ext.define("app.view.user.Page", {
	extend : "Ext.panel.Panel",
	alias : "widget.userpage",
	xtype: 'userpage',
	requires : [ "app.view.user.UserList", "app.view.user.DepartmentTree" ],
	layout : "border",
	title : "用户",
	iconCls : "User",
	items : [ {
		region : "west",
		xtype : "departmenttree",
		collapsible : true,
		border : false,
		split : true,
		width : 240,
		layout : "fit"
	}, {
		xtype : "userlist",
		region : "center",
		layout : "fit",
		border : false
	} ]
});