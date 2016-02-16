Ext.define("app.model.Role", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "code",
		type : "string"
	} ],
	idProperty : "id",
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/admin/role/get",
		extraParams : {
			paths : [ "id", "name", "code" ]
		}
	}
});