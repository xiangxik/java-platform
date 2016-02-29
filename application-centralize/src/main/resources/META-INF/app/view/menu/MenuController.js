Ext.define("app.view.menu.MenuController", {
	extend : "Ext.app.ViewController",
	alias : "controller.menu",

	onItemClick : function(tree, item) {
		this.getViewModel().set("currentItem", item.data);
		var menuForm = tree.up("menuview").down("menuform");
		menuForm.setBind({
			title : "编辑菜单【{currentItem.text}】"
		})
		menuForm.loadRecord(item);
		menuForm.expand(true);
	},

	onAdd : function(button) {
		var menuForm = button.up("menuview").down("menuform");
		menuForm.setTitle("新建菜单");
		menuForm.setBind({
			title : "新建菜单 上级为【{currentItem.text}】"
		})
		menuForm.reset(true);
		menuForm.expand(true);
	},

	onFormSave : function(button) {
		var menuForm = button.up("menuform");
		var form = menuForm.getForm();
		var store = this.getViewModel().getStore("tree");

		if (form.isValid()) {
			var currentItem = this.getViewModel().get("currentItem");
			form.submit({
				params : {
					current : currentItem && currentItem.id
				},
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					menuForm.reset(true);
					menuForm.collapse();
					store.reload();
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},

	onDelete : function(button) {
		var menuTree = button.up("menutree");
		var selection = menuTree.getSelection();
		if (selection.length == 0) {
			Ext.Msg.alert("提示", "您最少要选择一条数据");
		} else {
			var allLeaf = true;
			var ids = [];
			Ext.each(selection, function(record, index, array) {
				if (!record.isLeaf()) {
					allLeaf = false;
				}
				ids.push(record.id);
			});
			if (!allLeaf) {
				Ext.Msg.alert("提示", "不能删除包含有下级的数据，请先把其下级删除");
			} else {
				Ext.Msg.confirm("提示", "您确定要删除所选菜单？", function(choice) {
					if (choice === "yes") {
						var store = this.getViewModel().getStore("tree");
						Ext.Ajax.request({
							url : Ext.ctx + "/admin/menu/delete",
							params : {
								ids : ids
							},
							method : "POST",
							timeout : 10000,
							success : function(response) {
								Ext.Msg.info("提示", "操作成功");
								store.reload();
							}
						});
					}
				}, this);
			}
		}
	}
});