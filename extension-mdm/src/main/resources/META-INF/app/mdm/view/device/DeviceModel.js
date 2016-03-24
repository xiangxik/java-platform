Ext.define("app.mdm.view.device.DeviceModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.device",
	stores : {
		list : Ext.create("app.mdm.store.Devices")
	}
});