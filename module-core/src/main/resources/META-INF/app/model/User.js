Ext.define("app.model.User", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "username",
		type : "string"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "email",
		type : "string"
	}, {
		name : "mobile",
		type : "string"
	}, {
		name : "avatar",
		type : "string"
	}, {
		name : "birthday",
		type : "date",
		dateFormat : "time"
	}, {
		name : "superAdmin",
		type : "bool"
	}, {
		name : "locked",
		type : "bool"
	}, {
		name : "sex",
		type : "string"
	}, {
		name : "departmentName",
		mapping : "department.name",
		reference : "Department"
	} ]
});