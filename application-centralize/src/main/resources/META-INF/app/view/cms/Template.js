Ext.define("app.view.cms.Template", {
	extend : "Ext.panel.Panel",
	xtype : "widget.cmstemplate",
	layout : "border",
	requires : [ "app.view.cms.TemplateController", "app.view.cms.TemplateModel" ],
	controller : "cmstemplate",
	viewModel : "cmstemplate",
	title : "模板管理",
	border : false,
	frame : false,
	items : [ {
		region : "west",
		xtype : "treepanel",
		bind : {
			store : "{files}"
		},
		rootVisible : false,
		listeners : {
			itemclick : "onFileItemClick"
		},
		title : "模板文件",
		width : 200,
		collapsible : true,
		border : false,
		frame : false,
		split : true,
		tbar : [ {
			xtype : "button",
			text : "添加",
			iconCls : "Add",
			handler : "onAdd"
		}, {
			xtype : "button",
			text : "删除",
			iconCls : "Delete",
			handler : "onDelete"
		} ]
	}, {
		region : "center",
		xtype : "form",
		url : Ext.ctx + "/admin/template",
		method : "POST",
		border : false,
		frame : false,
		layout : "fit",
		items : [ {
			xtype : "hiddenfield",
			allowBlank : false,
			name : "path",
			bind : {
				value : "{path}"
			}
		}, {
			xtype : "textarea",
			allowBlank : false,
			name : "content",
			bind : {
				value : "{content}"
			}
		} ],
		tbar : [ {
			xtype : "label",
			bind : {
				text : "{path}"
			}
		}, {
			xtype : "button",
			text : "预览",
			handler : "onReview"
		} ],
		buttons : [ {
			text : "保存",
			formBind : true,
			handler : "onFormSave"
		} ]
	} ]
});