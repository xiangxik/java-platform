Ext.define("app.view.role.AuthForm", {
	extend : "Ext.form.Panel",
	alias : "widget.authform",
	requires : [ "app.view.role.RoleController", "app.view.role.RoleModel" ],
	controller : "role",
	viewModel : "role",
	border : false,
	frame : false,
	url : Ext.ctx + "/admin/role/auth",
	layout : "fit",
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onAuthSave"
	} ],
	listeners : {
		beforerender : function(form, opts) {
			var role = form.getRecord();
			form.add({
				xtype : "tabpanel",
				border : false,
				frame : false,
				layout : "fit",
				items : [ {
					xtype : "treepanel",
					id : "menuAuth",
					title : "菜单权限",
					rootVisible : false,
					store : {
						proxy : {
							type : "ajax",
							method : "GET",
							url : Ext.ctx + "/admin/role/authMenu",
							extraParams : {
								id : role ? role.get("id") : ""
							},
							reader : {
								type : "json"
							}
						}
					},
					border : false,
					frame : false,
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
				}, {
					xtype : "panel",
					title : "资源权限",
					border : false,
					frame : false
				} ]
			});

		}
	}
});