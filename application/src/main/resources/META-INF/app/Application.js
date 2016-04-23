Ext.define('app.Application', {
	extend : 'Ext.app.Application',
	name : 'app',
	stores : [],
	launch : function() {

		this.onExtraApply();

		Ext.create("app.view.main.Main");
	},

	onExtraApply : function() {

		Ext.override(Ext.form.field.Base, {
			initComponent : function() {
				if (this.allowBlank !== undefined && !this.allowBlank) {
					if (this.afterLabelTextTpl) {
						this.afterLabelTextTpl.push("<span style=\"color:red;font-weight:bold\" data-qtip=\"必填选项\">*</span>");
					} else {
						this.afterLabelTextTpl = [ "<span style=\"color:red;font-weight:bold\" data-qtip=\"必填选项\">*</span>" ];
					}
				}
				this.callParent(arguments);
			}
		});

		Ext.apply(Ext.Msg, {
			info : function(title, content, opts) {
				var configs = {
					title : title,
					width : 320,
					position : "tr",
					cls : 'ux-notification-light',
					iconCls : 'ux-notification-icon-information',
					html : content
				};
				if (opts) {
					Ext.apply(configs, opts);
				}
				Ext.create("app.ux.window.Notification", configs).show();
			},
			error : function(title, content, opts) {
				var configs = {
					title : title,
					width : 320,
					position : "tr",
					cls : 'ux-notification-light',
					iconCls : 'ux-notification-icon-error',
					html : content
				};
				if (opts) {
					Ext.apply(configs, opts);
				}
				Ext.create("app.ux.window.Notification", configs).show();
			}
		});

		Ext.Ajax.on("requestexception", function(conn, response, options, eOpts) {
			if (response.status == 401) {
				Ext.create("app.view.main.Login");
			} else {
				Ext.Msg.error("错误", "系统出错");
			}
		});

		Ext.Ajax.on("requestcomplete", function(conn, response, options, eOpts) {
			if (response.status == 401) {
				Ext.create("app.view.main.Login");
			}
		});
	},

	onAppUpdate : function() {
		Ext.Msg.confirm('Application Update', 'This application has an update, reload?', function(choice) {
			if (choice === 'yes') {
				window.location.reload();
			}
		});
	}
});
