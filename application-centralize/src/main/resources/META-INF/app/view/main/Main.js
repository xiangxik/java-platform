Ext.define("app.view.main.Main", {
	extend : "Ext.container.Viewport",
	xtype : "app-main",
	layout : "border",
	requires : [ "app.view.main.MainController", "app.view.main.MainModel", "app.view.main.Center", "app.view.main.Header", "app.view.main.Footer",
			"app.view.main.WestNav" ],
	controller : "main",
	viewModel : "main",
	items : [ {
		region : "north",
		xtype : "mainheader",
		margin : "5 5 0 5"
	}, {
		region : "south",
		xtype : "mainfooter",
		margin : "0 5 0 5"
	}, {
		region : "west",
		xtype : "mainwestnav",
		margin : "5 0 0 5",
		width : 200,
		collapsible : true,
		split : true
	}, {
		region : "center",
		xtype : "maincenter",
		margin : "5 5 0 0"
	} ],
	initComponent : function() {
		this.callParent(arguments);

		this.getController().loadUserReference();
	}
});