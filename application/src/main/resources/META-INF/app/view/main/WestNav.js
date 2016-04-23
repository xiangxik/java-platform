Ext.define("app.view.main.WestNav", {
	extend : "Ext.panel.Panel",
	alias : "widget.mainwestnav",
	title : "导航栏",
	layout : {
		type : "accordion",
		titleCollapse : false,
		animate : true
	},
	defaults : {
		xtype : "treepanel"
	}
});