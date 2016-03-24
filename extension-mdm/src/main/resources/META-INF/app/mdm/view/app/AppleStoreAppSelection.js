Ext.define("app.mdm.view.app.AppleStoreAppSelection", {
	extend : "Ext.window.Window",
	layout : "fit",
	title : "选择AppStore上的应用",
	width : 460,
	modal : true,
	resizable : false,
	initComponent : function() {
		var me = this;

		me.items = [ {
			xtype : "form",
			border : false,
			bodyPadding : 10,
			items : [
					{
						type : 'fieldcontainer',
						height : 36,
						border : false,
						padding : 4,
						width : 450,
						layout : "anchor",
						items : [ {
							xtype : "combobox",
							fieldLabel : "关键字",
							displayField : "trackName",
							valueField : 'trackId',
							anchor : "90%",
							labelWidth : 50,
							editable : true,
							allowBlank : false,
							queryMode : "remote",
							hideTrigger : true,
							typeAhead : false,
							store : Ext.create("app.mdm.store.AppleAppStores"),
							minChars : 2,
							queryParam : "term",
							listConfig : {
								emptyText : "<div style=\"line-height:22px;padding:2px 10px\">没有找到匹配的数据</div>",
								getInnerTpl : function() {
									return "<div class=\"search-item\" >"
											+ "<div class=\"x-tree-icon x-tree-icon-leaf search-item-icon \"></div>{trackName}" + "</div>";
								}
							},
							listeners : {
								"select" : {
									fn : me.comboSelect,
									scope : me
								}
							}
						} ]
					}, {
						xtype : "fieldset",
						title : "应用详情",
						collapsible : true,
						layout : 'anchor',
						layout : {
							type : 'table',
							columns : 2
						},
						defaults : {
							anchor : '100%',
							labelWidth : 72
						},
						items : [ {
							xtype : 'container',
							margin : 4,
							padding : 1,
							layout : "fit",
							style : {
								border : '1px solid #ccc'
							},
							height : 106,
							width : 106,
							padding : 2,
							items : [ {
								xtype : 'image',
								itemId : 'vistaPrevia',
								resetImageSize : false,
							} ]
						}, {
							xtype : 'fieldcontainer',
							defaultType : "textfield",
							fieldDefaults : {
								labelAlign : "left",
								labelWidth : 72,
								anchor : '100%',
							},
							defaults : {
								editable : false
							},
							items : [ {
								name : "trackName",
								fieldLabel : "名称"
							}, {
								name : "sellerName",
								fieldLabel : "公司"
							}, {
								name : "fileSizeBytes",
								fieldLabel : "文件大小"
							}, {
								name : "version",
								fieldLabel : "版本"
							} ]
						}, {
							xtype : "label",
							text : "详情:",
							colspan : 2
						}, {
							xtype : "textarea",
							name : "description",
							width : 380,
							margin : "2 10 10",
							colspan : 2
						} ]
					} ]
		} ];
		me.callParent(arguments);
	},
	getCallback: function() {
		this.callback;
	},
	buttons : [ {
		text : "确定",
		handler : function() {
			var win = this.up("window");
			var record = win.down("form").getRecord();
			win.callback(win, record);
		}
	}, {
		text : "取消",
		handler : function() {
			this.up("window").close();
		}
	} ],
	comboSelect : function(combo, record, eOpts) {
		if (record == undefined) {
			return;
		}

		var me = this;

		this.down("#vistaPrevia").setSrc(record.get("artworkUrl100"));
		this.down("form").loadRecord(record);
	}
});