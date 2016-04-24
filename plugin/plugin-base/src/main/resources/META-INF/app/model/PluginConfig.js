Ext.define("app.model.PluginConfig", {
	extend : "app.model.Entity",
	fields : [ {
		name : "pluginId",
		type : "string"
	}, {
		name : "isEnabled",
		type : "boolean"
	}, {
		name : "attributes"
	} ],
	proxy: {
		type: "ajax"
	}
});