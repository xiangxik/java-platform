Ext.define("app.store.PaymentPlugins", {
	extend : "app.store.PageStore",
	alias : "store.paymentplugins",
	model : "app.model.Plugin",
	url : Ext.ctx + "/admin/payment/plugin/list"
});