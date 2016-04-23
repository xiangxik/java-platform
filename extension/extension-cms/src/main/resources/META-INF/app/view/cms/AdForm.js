Ext.define("app.view.cms.AdForm", {
	extend : "Ext.form.Panel",
	alias : "widget.adform",
	requires : [ "app.view.cms.AdController", "app.view.cms.AdModel", "app.ux.form.htmleditor.Table", "app.ux.form.htmleditor.Image",
			"app.ux.form.Image" ],
	controller : "ad",
	viewModel : "ad",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/ad/save",
	border : false,
	frame : false,
	scrollable : true,
	layout : "auto",
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	defaultType : "textfield",
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		fieldLabel : "标题",
		name : "title",
		allowBlank : false
	}, {
		fieldLabel : "类型",
		xtype : "fieldcontainer",
		defaultType : "radiofield",
		layout : "hbox",
		items : [ {
			boxLabel : "文本",
			name : "type",
			inputValue : "text",
			margin : "0 12 0 0"
		}, {
			boxLabel : "图片",
			name : "type",
			inputValue : "image",
			margin : "0 12 0 0"
		}, {
			boxLabel : "动画",
			name : "type",
			inputValue : "flash"
		} ]
	}, {
		fieldLabel : "广告位",
		name : "adPosition",
		queryMode : 'remote',
		xtype : "combobox",
		editable : false,
		allowBlank : false,
		store : Ext.create("app.store.AdPositions"),
		displayField : 'name',
		valueField : 'id',
		allowBlank : false
	}, {
		fieldLabel : "展示图片",
		xtype : "imagefield",
		name : "path"
	}, {
		fieldLabel : "内容",
		name : "content",
		xtype : "htmleditor",
		plugins : [ {
			ptype : "htmleditortable"
		}, {
			ptype : "htmleditorimage"
		} ]
	}, {
		fieldLabel : "起止日期",
		name : "beginDate",
		xtype : "datefield",
		format : "Y-m-d"
	}, {
		fieldLabel : "结束日期",
		name : "endDate",
		xtype : "datefield",
		format : "Y-m-d"
	}, {
		fieldLabel : "链接地址",
		name : "url"
	}, {
		fieldLabel : "排序号",
		name : "sortNo",
		xtype : "numberfield"
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});