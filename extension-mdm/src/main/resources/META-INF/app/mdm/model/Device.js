Ext.define("app.mdm.model.Device", {
	extend : "app.model.Entity",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "availableDeviceCapacity",
		type : "number"
	}, {
		name : "deviceCapacity",
		type : "number"
	}, {
		name : "phoneNumber",
		type : "string"
	}, {
		name : "sIMCarrierNetwork",
		type : "string"
	}, {
		name : "buildVersion",
		type : "string"
	}, {
		name : "imei",
		type : "string"
	}, {
		name : "oSVersion",
		type : "string"
	}, {
		name : "productName",
		type : "string"
	}, {
		name : "serialNumber",
		type : "string"
	}, {
		name : "udid",
		type : "string"
	}, {
		name : "createdDate",
		type : "date",
		dateFormat : "time"
	}, {
		name : "lastModifiedDate",
		type : "date",
		dateFormat : "time"
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.mdm.model.Device.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/device/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});