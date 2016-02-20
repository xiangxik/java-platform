Ext.define("app.store.PageStore", {
	extend : "Ext.data.Store",
	constructor : function() {

		this.callParent(arguments);

		var entity = this.getModel();
		var fields = entity.getFields();
		var paths = [];
		Ext.each(fields, function(field, index, array) {
			paths.push(field.getName());
		});

		this.setProxy({
			type : "ajax",
			url : this.url,
			extraParams : {
				paths : paths
			},
			reader : {
				type : "json",
				rootProperty : "rows"
			}
		});
	},
	autoLoad : true
});