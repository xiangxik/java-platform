Ext.define("app.view.main.region.Footer", {
	extend : "Ext.toolbar.Toolbar",
	alias : "widget.mainfooter",
	requires : ["app.ux.ButtonTransparent"],
	border: false,
	items : [{
		bind: {text:"用户名"},
		handler : 'onUserDwmcClick',
		iconCls : 'fa fa-building'
	}, "->", {
		bind: {
			html:"&copy;{info.systemName}"
		},
		xtype:"label"
	}, "->", {
		bind: {
			html:"&copy;{info.systemName}"
		},
		xtype:"label"
	}]
});