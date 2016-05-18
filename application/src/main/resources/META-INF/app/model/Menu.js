Ext.define("app.model.Menu", {
	extend : "app.model.TreeEntity",
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
	}, {
		name : "sortNo",
		type : "int"
	}, {
		name : "parent",
		mapping : "parent.id",
		type : "int"
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.model.Menu.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/menu/tree",
			extraParams : {
				paths : this.paths
			}
		})
	}
});