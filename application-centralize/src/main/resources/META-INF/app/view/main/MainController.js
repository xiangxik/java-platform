Ext.define("app.view.main.MainController", {
	extend : "Ext.app.ViewController",
	alias : "controller.main",
	loadUserReference : function() {
		var westNav = this.getView().down("mainwestnav");
		westNav.removeAll(true);

		var vm = this.getViewModel();
		Ext.Ajax.request({
			url : Ext.ctx + "/admin/user/reference",
			async : true,
			success : function(response) {
				var text = response.responseText;
				var reference = Ext.decode(text, true);

				if(reference.user) {
					vm.set("user", reference.user);
				}
				
				Ext.each(reference.menus, function(menu, index, sets) {
					westNav.add({
						title : menu.text,
						rootVisible : false,
						listeners : {
							itemclick : 'onMenuTreeItemClick'
						},
						store : Ext.create("Ext.data.TreeStore", {
							data : menu.children
						})
					})
				});
			}
		});
	},
	onMenuTreeItemClick : function(tree, item) {
		if (item.isLeaf()) {
			this.addViewToCenter(item.data);
		}
	},
	addViewToCenter : function(menu) {
		var view = this.getView().down("maincenter");
		var tabId = menu.code;
		var tab = view.down("> panel#" + tabId);
		if (!tab) {
			var config = {};
			if (menu.config) {
				Ext.apply(config, menu.config);
			}
			config.closable = true;
			config.id = tabId;
			config.title = menu.text;
			config.iconCls = menu.iconCls;
			tab = view.add(Ext.create(menu.view, config));
		}
		view.setActiveTab(tab);
	},
	onLoginSubmit : function(button) {
		var me = this;
		var window = button.up('window');
		var form = window.down("form").getForm();
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					window.destroy();
					me.getView().mask("加载系统中...");
					me.loadUserReference();
					me.getView().unmask();
				},
				failure : function(form, action) {
					if (action && action.result) {
						Ext.Msg.alert("提示", action.result.msg);
					}

					document.getElementById("captchaImage").src = Ext.ctx + "/captcha?_t=" + new Date().getTime();
				}
			});
		}
	},
	onSignOut : function() {
		Ext.Msg.confirm("提示", "您确定要退出系统？", function(choice) {
			if (choice === "yes") {
				window.location.href = Ext.ctx + "/admin/logout";
			}
		}, this);
	}
});