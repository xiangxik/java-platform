Ext.define("app.view.main.Center", {
	extend : "Ext.tab.Panel",
	alias : "widget.maincenter",
	id : "maincenter",
	uses : [ "app.view.main.HomePage" ],
	requires : [ "Ext.ux.TabReorderer", "Ext.ux.TabCloseMenu" ],
	plugins : [ {
		ptype : "tabclosemenu",
		closeAllTabsText : "关闭所有",
		closeOthersTabsText : "关闭其他",
		closeTabText : "关闭"
	}, {
		ptype : "tabreorderer"
	} ],
	initComponent : function() {
		this.items = [ {
			xtype : "homepage",
			reorderable : false
		} ];
		this.callParent(arguments);
	}
});