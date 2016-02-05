Ext.define("app.model.User", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "name",
		type : "string",
		sortable : true
	}, {
		name : "age",
		type : "int",
		sortable : true
	} ]
});