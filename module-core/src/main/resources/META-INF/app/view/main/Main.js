Ext.define('app.view.main.Main', {
	extend : 'Ext.container.Viewport',
	xtype : 'app-main',
	layout : "border",
	requires : [ 'Ext.plugin.Viewport', 'Ext.window.MessageBox', 'app.view.main.MainController', 'app.view.main.MainModel',
			"app.view.main.region.Header", "app.view.main.region.Footer", "app.view.main.region.WestNav", "app.view.main.region.Center" ],

	controller : 'main',
	viewModel : 'main',

	items : [ {
		region : 'north', // position for region
		xtype : 'mainheader',
		margin : '5 5 0 5'
	}, {
		region : 'south', // position for region
		xtype : 'mainfooter',
		margin : '0 5 0 5'
	}, {
		title : '菜单',
		region : 'west',
		xtype : 'mainwestnav',
		margin : '5 0 1 5',
		width : 200,
		collapsible : true,
		split : true
	}, {
		region : 'center',
		xtype : 'maincenter',
		margin : '5 5 1 0'
	} ]
});
