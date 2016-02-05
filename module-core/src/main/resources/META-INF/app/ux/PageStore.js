Ext.define("app.ux.PageStore", {
	extend : "Ext.data.Store",
	alias : "store.page",
	constructor : function() {

		this.callParent(arguments);

		this.setProxy({
			type : "ajax",
			url : this.url,
			extraParams : this.extraParams,
			reader : {
				type : "json",
				rootProperty : "rows"
			}
		});
	},
	autoLoad : true
});