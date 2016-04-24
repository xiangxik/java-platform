Ext.define("app.model.PaymentPlugin", {
	extend : "app.model.Plugin",
	constructor : function() {
		this.callParent(arguments);
		var fields = this.getFields();
		fields.push({
			name : "paymentName",
			type : "string"
		});
		fields.push({
			name : "feeType",
			type : "string"
		});
		fields.push({
			name : "fee",
			type : "number"
		});
		fields.push({
			name : "logo",
			type : "string"
		});
		fields.push({
			name : "description",
			type : "string"
		});
	}
});