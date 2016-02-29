Ext.define("app.store.TreeStore", {
	extend : "Ext.data.TreeStore",
	constructor : function() {

		this.callParent(arguments);

		var entity = this.getModel();
		var fields = entity.getFields();

		var excludes = [ "parentId", "index", "depth", "expanded", "expandable", "checked", "leaf", "cls", "icon", "root", "isLast", "isFirst",
				"allowDrop", "allowDrag", "loaded", "loading", "href", "hrefTarget", "qtip", "qtitle", "qshowDelay", "children", "visible" ];

		var paths = [];
		Ext.each(fields, function(field, index, array) {
			if (Ext.Array.indexOf(excludes, field.getName()) < 0) {
				paths.push(field.getName());
			}
		});

		this.setProxy({
			type : "ajax",
			url : this.url,
			extraParams : {
				paths : paths
			},
			reader : {
				type : "json"
			}
		});
	},
	autoLoad : true
});