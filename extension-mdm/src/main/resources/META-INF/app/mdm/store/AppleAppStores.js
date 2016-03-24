Ext.define("app.mdm.store.AppleAppStores", {
	extend : "Ext.data.Store",
	proxy : {
		type : "jsonp",
		url : "https://itunes.apple.com/search?entity=software",
		reader : {
			type : "json",
			rootProperty : "results",
			totalProperty : "resultCount"
		}
	},
	remoteFilter : true,
	autoLoad : true
});