Ext.define("app.view.cms.AdPositionModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.adPosition",
	stores : {
		list : Ext.create("app.store.AdPositions")
	}
});