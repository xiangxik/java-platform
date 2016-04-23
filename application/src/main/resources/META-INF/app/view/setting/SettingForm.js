Ext.define("app.view.setting.SettingForm", {
	extend : "Ext.form.Panel",
	alias : "widget.settingform",
	bodyPadding : 5,
	url : Ext.ctx + "/admin/setting",
	border : false,
	frame : false,
	scrollable : true,
	layout : "auto",
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	defaultType : "textfield",
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : function() {
			var formPanel = this.up("form");
			var form = formPanel.getForm();
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
		}
	} ],
	initComponent : function() {
		this.callParent(arguments);

		var me = this;
		Ext.Ajax.request({
			url : Ext.ctx + "/admin/setting",
			method : "GET",
			async : true,
			success : function(response) {
				var text = response.responseText;
				var settings = Ext.decode(text, true);

				Ext.each(settings, function(setting, index, array) {
					var fieldItems = [];
					Ext.each(setting.value, function(config, j, arr) {
						fieldItems.push({
							xtype : "textfield",
							fieldLabel : config.display,
							name : config.key,
							value : config.value
						});
					});
					me.add({
						xtype : "fieldset",
						title : setting.display,
						collapsible : true,
						layout : 'anchor',
						defaults : {
							anchor : '90%'
						},
						items : fieldItems
					});
				});
			}
		});
	}
});