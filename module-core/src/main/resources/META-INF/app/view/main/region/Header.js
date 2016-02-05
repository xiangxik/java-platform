Ext.define("app.view.main.region.Header", {
	extend : "Ext.toolbar.Toolbar",
	alias : "widget.mainheader",
	border : false,
	defaults : {
		xtype : "button"
	},
	items : [ {
		xtype : "label",
		bind : {
			text : "{info.systemName}"
		},
		style : "font-size:14px;font-weight:800"
	}, "->", {
		text : "用户",
		iconCls : "fa fa-user",
		menu : [ {
			text : "我的消息",
			iconCls : "fa fa-info-circle",
		}, "-", {
			text : "退出",
			iconCls : "fa fa-sign-out"
		} ]
	} ]
});