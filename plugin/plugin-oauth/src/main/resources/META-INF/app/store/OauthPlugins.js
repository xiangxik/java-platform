Ext.define("app.store.OauthPlugins", {
	extend : "app.store.PageStore",
	alias : "store.oauthplugins",
	model : "app.model.Plugin",
	url : Ext.ctx + "/admin/oauth/plugin/list"
});