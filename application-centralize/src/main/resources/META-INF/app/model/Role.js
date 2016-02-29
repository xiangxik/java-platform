Ext.define("app.model.Role", {
	extend : "app.model.Entity",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "code",
		type : "string"
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.model.Role.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/role/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});