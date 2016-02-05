Ext.define("app.view.main.region.WestNav", {
	extend : "Ext.panel.Panel",
	alias : "widget.mainwestnav",
	layout : {
		type : 'accordion',
		titleCollapse : false,
		animate : true
	},
	defaults : {
		xtype : "treepanel"
	},
	initComponent : function() {
		var me = this;
		Ext.create("app.store.MenuSets").load({
			callback : function(records, operation, success) {
				if (success == true) {
					var menuSets = [];
					Ext.each(records, function(record, index, array) {
						menuSets.push({
							name : record.get("name"),
							code : record.get("code"),
							menuItems : record.get("menuItems")
						})
					});

					for ( var i in menuSets) {
						var menuSet = menuSets[i];
						me.add({
							title : menuSet.name,
							rootVisible : false,
							listeners : {
								itemclick : 'onMenuTreeItemClick'
							},
							store : Ext.create("Ext.data.TreeStore", {
								data : menuSet.menuItems
							})
						})

					}
				}
			}
		});

		this.callParent(arguments);
	}
});