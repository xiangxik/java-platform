Ext.define("app.view.plugin.PaymentPluginModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.paymentplugin",
	stores : {
		list : Ext.create("app.store.PaymentPlugins")
	}
});