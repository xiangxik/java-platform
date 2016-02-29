Ext.define("app.view.role.AuthForm", {
	extend : "Ext.form.Panel",
	alias : "widget.authform",
	title : "赋权",
	url : Ext.ctx + "/admin/role/menuTree",
	layout : {
		type : "accordion",
		titleCollapse : false,
		animate : true
	},
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onAuthSave"
	} ],
	getRole : function() {
		return this.role;
	},
	initComponent : function() {
		this.callParent(arguments);

		var role = this.getRole();
		this.add({
			xtype : "treepanel",
			id : "menuAuth",
			title : "菜单权限",
			rootVisible : false,
			store : {
				proxy : {
					type : "ajax",
					method : "GET",
					url : Ext.ctx + "/admin/role/menuTree",
					extraParams : {
						id : role ? role.get("id") : ""
					},
					reader : {
						type : "json"
					}
				}
			},
			listeners : {
				checkchange : function(node, checked) {
					if (checked == true) {
						node.checked = checked;
						pNode = node.parentNode;

						for (; pNode != null; pNode = pNode.parentNode) {
							pNode.set("checked", true);
						}
					}

					if (!node.get("leaf") && !checked) {
						node.cascade(function(node) {
							node.set('checked', false);
						});
					}
				}
			}
		});
		this.add({
			xtype : "panel",
			title : "资源权限"
		});
	}
});