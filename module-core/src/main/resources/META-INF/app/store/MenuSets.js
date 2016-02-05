Ext.define("app.store.MenuSets", {
	extend : "Ext.data.Store",
	alias : "store.menusets",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "code",
		type : "string"
	}, {
		name : "menuItems"
	} ],
	proxy : {
		type : "ajax",
		url : Ext.base + "/../../admin/menu/nav",
		reader : {
			type : "json"
		}
	}
});