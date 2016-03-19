Ext.define("app.view.module.ModuleModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.module",
	stores : {
		list : Ext.create("app.store.Modules")
	}
});