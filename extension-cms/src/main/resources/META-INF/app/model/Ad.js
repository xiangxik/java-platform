Ext.define("app.model.Ad", {
	extend : "app.model.Entity",
	fields : [ {
		name : "title",
		type : "string"
	}, {
		name : "type",
		type : "string"
	}, {
		name : "sortNo",
		type : "int"
	}, {
		name : "content",
		type : "string"
	}, {
		name : "beginDate",
		type : "date",
		dateFormat : "time"
	}, {
		name : "endDate",
		type : "date",
		dateFormat : "time"
	}, {
		name : "url",
		type : "string"
	}, {
		name : "adPositionName",
		mapping : "adPosition.name"
	}, {
		name : "adPosition",
		mapping : "adPosition.id",
		reference : "app.model.AdPosition"
	} ],
	constructor : function() {
		this.callParent(arguments);
		app.model.Ad.setProxy({
			type : "ajax",
			url : Ext.ctx + "/admin/ad/get",
			extraParams : {
				paths : this.paths
			}
		})
	}
});