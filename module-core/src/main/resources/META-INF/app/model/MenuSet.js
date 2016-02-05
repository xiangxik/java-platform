Ext.define("app.model.MenuSet", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "code",
		type : "string"
	}, {
		name : "menuItems"
	} ]
});