Ext.define('app.ux.grid.SearchPanel', {
	extend : "Ext.form.Panel",
	alias : "widget.searchpanel",
	title : "搜索面板",
	collapsible : true,
	border : false,
	frame : false,
	layout : "column",
	bodyPadding : 5,
	fieldDefaults : {
		labelAlign : "right",
		labelWidth : 80
	},
	buttonAlign : "center",
	buttons : [ {
		text : "搜索",
		formBind : true,
		handler : function() {
			var formPanel = this.up("form");
			var form = formPanel.getForm();
			var store = formPanel.getStore();

			if (form.isValid()) {

				form.getFields().each(function(item, index, len) {
					if (item.getValue() && item.getValue() != "" && (Ext.getClassName(item) != "Ext.ux.TreePicker" || item.getValue() != "root")) {
						this.addFilter(new Ext.util.Filter({
							id : item.getName(),
							property : item.getName(),
							value : item.getValue()
						}), true);
					} else {
						this.removeFilter(item.getName(), true);
					}
				}, store);

				store.load();
			}
		}
	}, {
		text : "重置",
		handler : function() {
			var formPanel = this.up("form");
			var form = formPanel.getForm();
			var store = formPanel.getStore();

			form.reset();
			form.getFields().each(function(item, index, len) {
				this.removeFilter(item.getName(), true);
			}, store);
		}
	} ],
	getStore : function() {
		return this.store;
	},
	setStore : function(store) {
		this.store = store;
	}
});