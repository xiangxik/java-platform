Ext.define("app.store.Departments", {
	extend : "Ext.data.TreeStore",
	alias : "store.departments",
	model : "app.model.Department",
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/admin/department/all",
		extraParams : {
			paths : [ "text", "code", "leaf", "children" ]
		},
		reader : {
			type : "json"
		},
		autoLoad : true
	},
	autoLoad : true
});