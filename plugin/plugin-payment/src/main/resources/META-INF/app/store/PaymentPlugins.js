Ext.define("app.store.PaymentPlugins", {
	extend : "app.store.PageStore",
	alias : "store.paymentplugins",
	model : "app.model.PaymentPlugin",
	url : Ext.ctx + "/admin/payment/plugin/list"
});