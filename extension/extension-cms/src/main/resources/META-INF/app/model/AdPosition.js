Ext.define("app.model.AdPosition", {
	extend : "app.model.Entity",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "width",
		type : "int"
	}, {
		name : "height",
		type : "int"
	}, {
		name : "description",
		type : "string"
	}, {
		name : "template",
		type : "string"
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.model.AdPosition.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/adPosition/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});