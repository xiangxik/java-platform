Ext.define("app.view.main.MainController", {
	extend : "Ext.app.ViewController",
	alias : "controller.main",
	loadUserReference : function() {
		var westNav = this.getView().down("mainwestnav");
		westNav.removeAll(true);

		Ext.Ajax.request({
			url : Ext.ctx + "/admin/user/reference",
			async : true,
			success : function(response) {
				var text = response.responseText;
				var reference = Ext.decode(text, true);

				Ext.each(reference.menuSets, function(menuSet, index, sets) {
					westNav.add({
						title : menuSet.name,
						rootVisible : false,
						listeners : {
							itemclick : 'onMenuTreeItemClick'
						},
						store : Ext.create("Ext.data.TreeStore", {
							data : menuSet.menuItems
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
			if (!menu.config) {
				menu.config = {};
			}
			menu.config.closable = true;
			menu.config.id = tabId;
			tab = view.add(Ext.create(menu.view, menu.config));
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
					Ext.Msg.alert("提示", "登陆失败");
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