Ext.define("app.model.MenuItem", {
	extend : "Ext.data.Model",
	alias : "model.menuitem",
	fields : [ {
		name : "text",
		type : "string"
	}, {
		name : "code",
		type : "string"
	}, {
		name : "cls",
		type : "string"
	}, {
		name : "icon",
		type : "string"
	}, {
		name : "iconCls",
		type : "string"
	}, {
		name : "href",
		type : "string"
	}, {
		name : "hrefTarget",
		type : "string"
	}, {
		name : "view",
		type : "string"
	}, {
		name : "menuSet",
		reference : "app.model.MenuSet"
	} ]
});