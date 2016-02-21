Ext.define("app.view.cms.TemplateController", {
	extend : "Ext.app.ViewController",
	alias : "controller.cmstemplate",

	onFileItemClick : function(tree, item) {
		var viewModel = this.getViewModel();
		if (item.isLeaf()) {
			var template = item.data;

			viewModel.set("path", template.path);
			viewModel.set("content", "");
			Ext.Ajax.request({
				url : Ext.ctx + "/admin/template",
				params : {
					path : template.path
				},
				async : false,
				method : "GET",
				success : function(response) {
					var text = response.responseText;
					viewModel.set("content", text);
				}
			});
		} else {
			viewModel.set("path", "");
			viewModel.set("content", "");
		}
	},
	onFormSave : function(button) {
		var form = button.up("form").getForm();
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},
	onReview : function(button) {
		var viewModel = this.getViewModel();
		var path = viewModel.get("path");
		if (path != "") {
			window.open(Ext.ctx + path);
		}
	}
});