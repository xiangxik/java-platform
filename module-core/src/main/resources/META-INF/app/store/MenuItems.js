Ext.define("app.store.MenuItems", {
	extend : "Ext.data.Store",
	alias : "store.menuitem",
	model : "app.model.MenuItem",
	proxy : {
		type : "ajax",
		url : Ext.base + "/../../admin/menu/nav",
		reader : {
			type : "json"
		}
	},
	autoLoad : true
})