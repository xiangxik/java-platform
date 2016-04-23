Ext.define("app.model.Module", {
	extend : "app.model.Entity",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "code",
		type : "string"
	}, {
		name : "version",
		type : "int"
	}, {
		name : "createdBy",
		type : "string"
	}, {
		name : "createdDate",
		type : "date",
		dateFormat : "time"
	}, {
		name : "lastModifiedBy",
		type : "string"
	}, {
		name : "lastModifiedDate",
		type : "date",
		dateFormat : "time"
	}, {
		name : "type",
		type : "string"
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.model.Module.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/module/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});