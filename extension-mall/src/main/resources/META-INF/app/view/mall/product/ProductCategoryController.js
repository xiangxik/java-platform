Ext.define("app.view.mall.product.ProductCategoryController", {
	extend : "Ext.app.ViewController",
	alias : "controller.productcategory",
	mixins : {
		center : "app.view.main.CenterController"
	},
	onItemClick : function(tree, item) {

	},

	onAdd : function(button) {
		var code = "productcategoryform";
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.mall.product.ProductCategoryForm", {
				id : code,
				closable : true,
				title : "新建商品分类",
				iconCls : "Bricklink"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowEdit : function(tree, rowIndex, colIndex) {
		var item = tree.getStore().getAt(rowIndex);
		var code = "productcategoryform" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.mall.product.ProductCategoryForm", {
				id : code,
				closable : true,
				title : "编辑商品分类【" + item.get("name") + "】",
				iconCls : "Bricklink"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowDelete : function(tree, rowIndex, colIndex) {
		var category = tree.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + category.get("name") + "】？", function(choice) {
			if (choice === "yes") {

				var store = this.getViewModel().getStore("tree");

				Ext.Ajax.request({
					url : Ext.ctx + "/admin/productCategory/delete",
					params : {
						id : category.id
					},
					method : "POST",
					success : function(response) {
						Ext.Msg.info("提示", "操作成功");
						store.remove(category);
					}
				});
			}
		}, this);
	},

	onFormSave : function(button) {
		var productcategoryForm = button.up("productcategoryform");
		var form = productcategoryForm.getForm();
		var store = this.getViewModel().getStore("tree");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					store.reload();
					me.closeTab(productcategoryForm);
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},

	onDelete : function(button) {
		var tree = button.up("productcategory");
		var data = tree.getSelection();
		if (data.length == 0) {
			Ext.Msg.alert("提示", "您最少要选择一条数据");
		} else {
			Ext.Msg.confirm("提示", "您确定要删除所选数据？", function(choice) {
				if (choice === "yes") {
					var ids = [];
					Ext.each(data, function(record, index, array) {
						ids.push(record.id);
					});

					var store = this.getViewModel().getStore("tree");

					Ext.Ajax.request({
						url : Ext.ctx + "/admin/productCategory/delete",
						params : {
							ids : ids
						},
						method : "POST",
						success : function(response) {
							Ext.Msg.info("提示", "操作成功");
							store.remove(data);
						}
					});
				}
			}, this);
		}
	}
});