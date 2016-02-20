Ext.define("app.view.main.MainModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.main",
	constructor : function() {
		var me = this;
		this.callParent(arguments);

		Ext.Ajax.request({
			url : Ext.ctx + "/appinfo",
			async : false,
			success : function(response) {
				var text = response.responseText;
				var appInfo = Ext.decode(text, true);
				Ext.apply(me.data, {
					appInfo : appInfo
				});
			}
		});
	}
});