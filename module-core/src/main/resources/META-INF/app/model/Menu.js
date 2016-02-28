Ext.define("app.model.Menu", {
	extend : "app.model.Entity",
	fields : [ {
		name : "text",
		type : "string"
	}, {
		name : "code",
		type : "string"
	}, {
		name : "iconCls",
		type : "string"
	}, {
		name : "view",
		type : "string"
	}, {
		name : "config",
		type : "string"
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.model.Menu.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/menu/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});