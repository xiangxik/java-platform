Ext.define("app.view.main.CenterController", {
	extend : "Ext.Mixin",
	addViewToCenter : function(menu) {
		var view = this.getView().down("maincenter");
		var tabId = menu.code;
		var tab = view.down("> panel#" + tabId);
		if (!tab) {
			if (!menu.config) {
				menu.config = {};
			}
			menu.config.closable = true;
			menu.config.id = tabId;
			tab = view.add(Ext.create(menu.view, menu.config));
		}
		view.setActiveTab(tab);
	}
});