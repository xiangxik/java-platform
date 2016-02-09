Ext.define("app.model.Department", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "code",
		type : "string"
	}, {
		name : "manager",
		reference : "User"
	} ]
});