Ext.define("app.model.PaymentPlugin", {
	extend : "app.model.Entity",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "version",
		type : "string"
	}, {
		name : "author",
		type : "string"
	}, {
		name : "settingView",
		type : "string"
	}, {
		name : "siteUrl",
		type : "string"
	}, {
		name : "installUrl",
		type : "string"
	}, {
		name : "uninstallUrl",
		type : "string"
	}, {
		name : "sortNo",
		type : "int"
	}, {
		name : "isInstalled",
		type : "boolean"
	}, {
		name : "isEnabled",
		type : "boolean"
	} ]
});