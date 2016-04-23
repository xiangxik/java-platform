Ext.define("app.view.main.CenterController", {
	extend : "Ext.Mixin",
	init : function() {

	},
	tabOnCenter : function(code) {
		var maincenter = Ext.getCmp("maincenter");
		var tab = maincenter.down("> panel#" + code);
		return tab;
	},
	addViewToCenter : function(code, view) {
		var maincenter = Ext.getCmp("maincenter");
		var tab = maincenter.down("> panel#" + code);
		if (!tab) {
			tab = maincenter.add(view);
		}
		return tab;
	},
	activeTab : function(tab) {
		var maincenter = Ext.getCmp("maincenter");
		maincenter.setActiveTab(tab);
	},
	closeTab : function(tab) {
		var maincenter = Ext.getCmp("maincenter");
		maincenter.remove(tab);
	},
	menuToCenter : function(menu) {
		var code = menu.code;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var config = {};
			if (menu.config) {
				Ext.apply(config, menu.config);
			}
			config.closable = true;
			config.id = code;
			config.title = menu.text;
			config.iconCls = menu.iconCls;
			tab = this.addViewToCenter(code, Ext.create(menu.view, config));
		}
		this.activeTab(tab);
	}
});