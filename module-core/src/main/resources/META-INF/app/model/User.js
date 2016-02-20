Ext.define("app.model.User", {
	extend : "app.model.Entity",
	fields : [ {
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
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.model.User.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/user/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});