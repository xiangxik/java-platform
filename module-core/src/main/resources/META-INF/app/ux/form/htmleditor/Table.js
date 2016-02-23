Ext.define("app.ux.form.htmleditor.Table", {
	extend : "Ext.util.Observable",
	alias : 'plugin.htmleditortable',
	// Table language text
	langTitle : 'Insert Table',
	langInsert : 'Insert',
	langCancel : 'Cancel',
	langRows : 'Rows',
	langColumns : 'Columns',
	langBorder : 'Border',
	langCellLabel : 'Label Cells',
	// private
	cmd : 'table',
	/**
	 * @cfg {Boolean} showCellLocationText Set true to display row and column
	 *      informational text inside of newly created table cells.
	 */
	showCellLocationText : true,
	/**
	 * @cfg {String} cellLocationText The string to display inside of newly
	 *      created table cells.
	 */
	cellLocationText : '{0}&nbsp;-&nbsp;{1}',
	/**
	 * @cfg {Array} tableBorderOptions A nested array of value/display options
	 *      to present to the user for table border style. Defaults to a simple
	 *      list of 5 varrying border types.
	 */
	tableBorderOptions : [ [ 'none', 'None' ], [ '1px solid #000', 'Sold Thin' ], [ '2px solid #000', 'Solid Thick' ],
			[ '1px dashed #000', 'Dashed' ], [ '1px dotted #000', 'Dotted' ] ],
	// private
	init : function(cmp) {
		this.cmp = cmp;
		this.cmp.on('render', this.onRender, this);
	},
	// private
	onRender : function() {
		var btn = this.cmp.getToolbar().add(
				{
					xtype : "button",
					iconCls : 'x-edit-table',
					handler : function() {
						if (!this.tableWindow) {
							this.tableWindow = new Ext.Window({
								title : this.langTitle,
								closeAction : 'hide',
								width : 235,
								items : [ {
									itemId : 'insert-table',
									xtype : 'form',
									border : false,
									plain : true,
									layout : "anchor",
									defaults : {
										anchor : "100%"
									},
									margin : 5,
									fieldDefaults : {
										labelAlign : "left",
										labelWidth : 60
									},
									items : [ {
										xtype : 'numberfield',
										allowBlank : false,
										allowDecimals : false,
										fieldLabel : this.langRows,
										name : 'row',
										width : 60
									}, {
										xtype : 'numberfield',
										allowBlank : false,
										allowDecimals : false,
										fieldLabel : this.langColumns,
										name : 'col',
										width : 60
									}, {
										xtype : 'combo',
										fieldLabel : this.langBorder,
										name : 'border',
										forceSelection : true,
										mode : 'local',
										store : new Ext.data.ArrayStore({
											autoDestroy : true,
											fields : [ 'spec', 'val' ],
											data : this.tableBorderOptions
										}),
										triggerAction : 'all',
										value : 'none',
										displayField : 'val',
										valueField : 'spec',
										anchor : '-15'
									}, {
										xtype : 'checkbox',
										fieldLabel : this.langCellLabel,
										checked : this.showCellLocationText,
										listeners : {
											check : function() {
												this.showCellLocationText = !this.showCellLocationText;
											},
											scope : this
										}
									} ]
								} ],
								buttons : [
										{
											text : this.langInsert,
											handler : function() {
												var format = function() {
													if (arguments.length == 0)
														return null;

													var str = arguments[0];
													for (var i = 1; i < arguments.length; i++) {
														var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
														str = str.replace(re, arguments[i]);
													}
													return str;
												};
												var frm = this.tableWindow.getComponent('insert-table').getForm();
												if (frm.isValid()) {
													var border = frm.findField('border').getValue();
													var rowcol = [ frm.findField('row').getValue(), frm.findField('col').getValue() ];
													if (rowcol.length == 2 && rowcol[0] > 0 && rowcol[1] > 0) {
														var colwidth = Math.floor(100 / rowcol[0]);
														var html = "<table style='border-collapse: collapse'>";
														var cellText = '&nbsp;';
														if (this.showCellLocationText) {
															cellText = this.cellLocationText;
														}
														for (var row = 0; row < rowcol[0]; row++) {
															html += "<tr>";
															for (var col = 0; col < rowcol[1]; col++) {
																html += "<td width='" + colwidth + "%' style='border: " + border + ";'>"
																		+ format(cellText, (row + 1), String.fromCharCode(col + 65)) + "</td>";
															}
															html += "</tr>";
														}
														html += "</table>";
														this.cmp.insertAtCursor(html);
													}
													this.tableWindow.hide();
												} else {
													if (!frm.findField('row').isValid()) {
														frm.findField('row').getEl().frame();
													} else if (!frm.findField('col').isValid()) {
														frm.findField('col').getEl().frame();
													}
												}
											},
											scope : this
										}, {
											text : this.langCancel,
											handler : function() {
												this.tableWindow.hide();
											},
											scope : this
										} ]
							});

						} else {
							this.tableWindow.getEl().frame();
						}
						this.tableWindow.show();
					},
					scope : this,
					tooltip : {
						title : this.langTitle
					},
					overflowText : this.langTitle
				});
	}
});