Ext.define("app.mdm.view.app.AppModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.app",
	stores : {
		list : Ext.create("app.mdm.store.Apps")
	}
});