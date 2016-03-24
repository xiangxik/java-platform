Ext.define("app.mdm.store.Devices", {
	extend : "app.store.PageStore",
	alias : "store.devices",
	model : "app.mdm.model.Device",
	url : Ext.ctx + "/admin/device/list"
});