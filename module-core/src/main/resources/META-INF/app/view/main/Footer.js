Ext.define("app.view.main.Footer", {
	extend : "Ext.toolbar.Toolbar",
	alias : "widget.mainfooter",
	border : false,
	items : [ "->", {
		bind : {
			html : "&copy;{appInfo.company}"
		},
		xtype : "label"
	}, "->" ]
});