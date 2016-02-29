Ext.define("app.view.cms.TemplateModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.cmstemplate",
	data : {
		path : "",
		content : ""
	},
	stores : {
		files : {
			type : "tree",
			proxy : {
				type : "ajax",
				url : Ext.ctx + "/admin/template/files"
			},
			autoLoad : true
		}
	}
});