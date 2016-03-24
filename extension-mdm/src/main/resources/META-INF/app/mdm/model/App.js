Ext.define("app.mdm.model.App", {
	extend : "app.model.Entity",
	fields : [ {
		name : "name",
		type : "string"
	}, {
		name : "icon60",
		type : "string"
	}, {
		name : "icon100",
		type : "string"
	}, {
		name : "icon512",
		type : "string"
	}, {
		name : "screenshots"
	}, {
		name : "supportedDevices"
	}, {
		name : "bundleId",
		type : "string"
	}, {
		name : "trackId",
		type : "string"
	}, {
		name : "version",
		type : "string"
	}, {
		name : "fileSizeBytes",
		type : "int"
	}, {
		name : "description",
		type : "string"
	}, {
		name : "gameCenterEnabled",
		type : "boolean"
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
		app.mdm.model.App.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/app/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});