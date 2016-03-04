Ext.define("app.model.Entity", {
	extend : "Ext.data.Model",
	idProperty : "id",
	constructor : function() {
		var me = this;
		this.callParent(arguments);

		var fields = this.getFields();

		me.paths = [];
		var hasIdField = false;
		Ext.each(fields, function(field, index, array) {
			if (field.getName() == "id") {
				hasIdField = true;
			}
			me.paths.push(field.getMapping() || field.getName());
		});

		if (!hasIdField) {
			this.getFields().add({
				name : "id",
				type : "int"
			});
			me.paths.push("id")
		}
	}
});