Ext
		.define(
				'app.ux.form.ImageDialog',
				{
					extend : 'Ext.window.Window',
					lang : null,
					t : null,
					submitUrl : null,
					managerUrl : null,
					iframeDoc : null,
					pageSize : null,
					imageToEdit : '',
					closeAction : 'destroy',
					width : 460,
					modal : true,
					resizable : false,
					layout : {
						type : 'fit'
					},
					title : '',
					listeners : {
						show : function(panel) {
							// we force the focus on the dialog window to avoid
							// control artifacts on IE
							this._loadImageDetails();
							panel.down('[name=src]').focus();
						},
						resize : function(panel) {
							panel.center();
						}
					},

					initComponent : function() {
						var me = this;
						var imageStore = Ext.create('Ext.data.Store', {
							fields : [ {
								name : 'id',
								type : 'int'
							}, {
								name : 'name',
								type : 'string'
							}, {
								name : 'fullname',
								type : 'string'
							}, {
								name : 'source',
								type : 'string'
							}, {
								name : 'thumbnail',
								type : 'string'
							} ],
							proxy : {
								type : 'ajax',
								url : me.managerUrl,
								extraParams : {
									action : 'imagesList'
								},
								reader : {
									type : 'json',
									rootProperty : 'rows'
								}
							},
							autoLoad : false,
							pageSize : me.pageSize
						});

						// if I dont remove store records I get an internalId
						// exception when refresh button is clicked
						imageStore.on('beforeload', function(store) {
							while (store.getCount(0) > 0)
								store.removeAt(0);
						});

						var alignStore = Ext.create('Ext.data.ArrayStore', {
							autoDestroy : true,
							idIndex : 0,
							fields : [ {
								name : 'name',
								type : 'string'
							}, {
								name : 'value',
								type : 'string'
							} ],
							data : [ [ '左', 'left' ], [ '无', 'none' ], [ '右', 'right' ] ]
						});

						var displayStore = Ext.create('Ext.data.ArrayStore', {
							autoDestroy : true,
							idIndex : 0,
							fields : [ {
								name : 'name',
								type : 'string'
							}, {
								name : 'value',
								type : 'string'
							} ],
							data : [ [ '默认', '' ], [ '同行', 'inline' ], [ '块', 'block' ] ]
						});

						var unitsStore = Ext.create('Ext.data.ArrayStore', {
							autoDestroy : true,
							idIndex : 0,
							fields : [ {
								name : 'name',
								type : 'string'
							}, {
								name : 'value',
								type : 'string'
							} ],
							data : [ [ 'px', 'px' ], [ '%', '%' ], [ 'em', 'em' ], [ 'in', 'in' ], [ 'cm', 'cm' ], [ 'mm', 'mm' ], [ 'ex', 'ex' ],
									[ 'pt', 'pt' ], [ 'pc', 'pc' ] ]
						});

						me.items = [ {
							xtype : 'form',
							name : 'imageUploadForm',
							bodyPadding : 10,
							border : false,
							items : [
									{
										xtype : 'fieldcontainer',
										height : 36,
										padding : 4,
										width : 450,
										layout : {
											columns : 2,
											type : 'column'
										},
										items : [
												{
													xtype : "hiddenfield",
													name : "id"
												},
												{
													xtype : 'combobox',
													name : 'src',
													queryMode : 'remote',
													fieldLabel : '地址',
													labelWidth : 50,
													columnWidth : 0.70,
													margin : '0 4 0 0',
													editable : true,
													allowBlank : true,
													store : imageStore,
													displayField : 'source',
													valueField : 'source',
													needsRefresh : false,
													checkChangeBuffer : 500,
													listeners : {
														'expand' : {
															fn : me._comboExpand,
															scope : me
														},
														'change' : {
															fn : me._comboChange,
															scope : me
														},
														'select' : {
															fn : me._comboSelect,
															scope : me
														}
													},
													tpl : '<tpl for="."><table class="x-boundlist-item" style="width:50%;float:left"><tr><td style="vertical-align:top;width:12px"><tpl if="'
															+ me.disableDelete
															+ ' == false"><a title="'
															+ '删除图片'
															+ '" href="#" img_id="{id}" class="x-htmleditor-imageupload-delete"></a></tpl></td><td><div class="x-htmleditor-imageupload-thumbcontainer"><img src="{thumbnail}"/></div></td></tr><tr><td colspan="2" style="text-align:center;font-size:12px">{name}</td></tr></table></tpl>',
													listConfig : {
														loadingText : '搜索中...',
														emptyText : '没有找到匹配的数据',
														listeners : {
															el : {
																click : {
																	delegate : 'a.x-htmleditor-imageupload-delete',
																	scope : me,
																	fn : me._deleteImage
																}
															}
														}
													},
													pageSize : me.pageSize
												}, {
													xtype : 'filefield',
													buttonOnly : true,
													name : 'file',
													value : '',
													columnWidth : 0.30,
													buttonText : '选择文件...',
													listeners : {
														change : me._uploadImage,
														scope : me
													}
												} ]
									}, {
										xtype : 'fieldset',
										title : '更多选项',
										itemId : 'fieldOptions',
										collapsible : true,
										layout : 'anchor',
										collapsed : true,
										defaults : {
											anchor : '100%',
											labelWidth : 72
										},
										items : [ {
											xtype : 'fieldset',
											title : '大小 & 明细',
											collapsible : true,
											layout : 'anchor',
											collapsed : false,
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
												layout : {
													align : 'middle',
													pack : 'center',
													type : 'hbox'
												},
												style : {
													border : '1px solid #ccc'
												},
												height : 130,
												width : 130,
												padding : 2,
												items : [ {
													xtype : 'image',
													itemId : 'vistaPrevia',
													resetImageSize : false,
													listeners : {
														afterrender : me._attachOnLoadEvent,
														scope : me
													}
												} ]
											}, {
												xtype : 'fieldcontainer',
												layout : {
													type : 'table',
													columns : 3
												},
												defaults : {
													labelSeparator : ' ',
													fieldLabel : '',
													labelAlign : 'left',
													labelWidth : 72,
													decimalSeparator : '.',
													width : 164,
													margin : '0 4 4 0'
												},
												items : [ {
													colspan : 3,
													xtype : 'combobox',
													width : 216,
													name : 'float',
													queryMode : 'local',
													editable : false,
													allowBlank : false,
													fieldLabel : '对齐',
													value : 'left',
													store : alignStore,
													displayField : 'name',
													valueField : 'value'
												}, {
													xtype : 'numberfield',
													fieldLabel : '宽度',
													name : 'width',
													minValue : 1,
													maxValue : 9999,
													constrainName : 'height',
													listeners : {
														change : me._checkConstrain
													}
												}, {
													xtype : 'combobox',
													name : 'widthUnits',
													queryMode : 'local',
													width : 48,
													editable : false,
													allowBlank : false,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value'
												}, {
													rowspan : 2,
													xtype : 'button',
													itemId : 'constraintProp',
													cls : 'x-htmleditor-imageupload-constrain',
													enableToggle : true,
													pressed : true,
													style : {
														border : '0px'
													},
													width : 24,
													height : 50,
													listeners : {
														toggle : me._toggleConstrain,
														scope : me
													}
												}, {
													xtype : 'numberfield',
													fieldLabel : '高度',
													name : 'height',
													minValue : 1,
													maxValue : 9999,
													constrainName : 'width',
													listeners : {
														change : me._checkConstrain
													}
												}, {
													xtype : 'combobox',
													name : 'heightUnits',
													queryMode : 'local',
													width : 48,
													editable : false,
													allowBlank : false,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value'
												}, {
													colspan : 3,
													xtype : 'displayfield',
													fieldLabel : '原始大小',
													itemId : 'realSize'
												} ]
											}, {
												xtype : 'fieldcontainer',
												hidden : me.serverSideEdit,
												layout : {
													type : 'table',
													columns : 4
												},
												defaults : {
													margin : '0 8 0 0'

												},
												padding : '0 0 0 12',
												items : [ {
													xtype : 'button',
													iconCls : 'x-htmleditor-imageupload-cropbutton',
													itemId : 'cropButton',
													disabled : true,
													handler : me._openCropDialogClick,
													scope : me,
													tooltip : 'Open crop window'
												}, {
													xtype : 'button',
													itemId : 'rotateButton',
													disabled : true,
													iconCls : 'x-htmleditor-imageupload-rotatebutton',
													handler : me._rotateImageClick,
													scope : me,
													tooltip : 'Rotate image 90º'
												}, {
													xtype : 'button',
													itemId : 'resizeButton',
													disabled : true,
													iconCls : 'x-htmleditor-imageupload-resizebutton',
													handler : me._resizeImageClick,
													scope : me,
													tooltip : 'Resize Image'
												}, {
													xtype : 'button',
													itemId : 'deleteButton',
													disabled : true,
													iconCls : 'x-htmleditor-imageupload-deletebutton',
													handler : me._deleteImageClick,
													scope : me,
													tooltip : 'Delete Image from server'
												} ]
											}, {
												xtype : 'hiddenfield',
												itemId : 'naturalWidth'
											}, {
												xtype : 'hiddenfield',
												itemId : 'naturalHeight'
											}, {
												xtype : 'hiddenfield',
												itemId : 'ratio'
											} ]
										}, {
											xtype : 'fieldset',
											hidden : me.styling,
											title : '样式',
											collapsible : true,
											layout : 'anchor',
											collapsed : true,
											items : [ {
												xtype : 'combobox',
												name : 'display',
												queryMode : 'local',
												editable : false,
												anchor : '100%',
												labelWidth : 72,
												allowBlank : false,
												fieldLabel : '显示',
												emptyText : '无',
												value : '',
												store : displayStore,
												displayField : 'name',
												valueField : 'value'
											}, {
												xtype : 'textfield',
												name : 'title',
												labelWidth : 72,
												anchor : '100%',
												fieldLabel : '标题'
											}, {
												xtype : 'textfield',
												name : 'className',
												labelWidth : 72,
												anchor : '100%',
												fieldLabel : '样式名'
											}, {
												xtype : 'fieldcontainer',
												layout : {
													type : 'table',
													columns : 4
												},
												fieldLabel : '内边距',
												labelWidth : 73,
												defaults : {
													labelSeparator : ' ',
													labelAlign : 'left',
													labelWidth : 48,
													width : 98,
													decimalSeparator : '.',
													margin : '0 4 4 0'
												},
												items : [ {
													xtype : 'numberfield',
													fieldLabel : '上',
													name : 'paddingTop'
												}, {
													xtype : 'combobox',
													name : 'paddingTopUnits',
													queryMode : 'local',
													width : 48,
													editable : false,
													allowBlank : false,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value'
												}, {
													xtype : 'numberfield',
													fieldLabel : '右',
													name : 'paddingRight'
												}, {
													xtype : 'combobox',
													name : 'paddingRightUnits',
													queryMode : 'local',
													width : 48,
													editable : false,
													allowBlank : false,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value'
												}, {
													xtype : 'numberfield',
													fieldLabel : '下',
													name : 'paddingBottom'
												}, {
													xtype : 'combobox',
													name : 'paddingBottomUnits',
													queryMode : 'local',
													editable : false,
													allowBlank : false,
													width : 48,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value'
												}, {
													xtype : 'numberfield',
													fieldLabel : '左',
													name : 'paddingLeft'
												}, {
													xtype : 'combobox',
													name : 'paddingLeftUnits',
													queryMode : 'local',
													editable : false,
													allowBlank : false,
													width : 48,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value',
													margin : '0'
												} ]
											}, {
												xtype : 'fieldcontainer',
												layout : {
													type : 'table',
													columns : 4
												},
												fieldLabel : '外边距',
												labelWidth : 73,
												defaults : {
													labelSeparator : ' ',
													labelAlign : 'left',
													labelWidth : 48,
													width : 98,
													margin : '0 4 4 0'
												},
												items : [ {
													xtype : 'numberfield',
													fieldLabel : '上',
													name : 'marginTop'
												}, {
													xtype : 'combobox',
													name : 'marginTopUnits',
													queryMode : 'local',
													width : 48,
													editable : false,
													allowBlank : false,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value'
												}, {
													xtype : 'numberfield',
													fieldLabel : '右',
													name : 'marginRight'
												}, {
													xtype : 'combobox',
													name : 'marginRightUnits',
													queryMode : 'local',
													width : 48,
													editable : false,
													allowBlank : false,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value'
												}, {
													xtype : 'numberfield',
													fieldLabel : '下',
													name : 'marginBottom'
												}, {
													xtype : 'combobox',
													name : 'marginBottomUnits',
													queryMode : 'local',
													editable : false,
													allowBlank : false,
													width : 48,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value'
												}, {
													xtype : 'numberfield',
													fieldLabel : '左',
													name : 'marginLeft'
												}, {
													xtype : 'combobox',
													name : 'marginLeftUnits',
													queryMode : 'local',
													editable : false,
													allowBlank : false,
													width : 48,
													emptyText : 'None',
													value : 'px',
													store : unitsStore,
													displayField : 'name',
													valueField : 'value'
												} ]
											} ]
										} ]
									} ],
							dockedItems : [ {
								xtype : 'container',
								dock : 'bottom',
								padding : 4,
								items : [ {
									xtype : 'button',
									style : {
										'float' : 'right'
									},
									text : '取消',
									handler : me.close,
									scope : me
								}, {
									xtype : 'button',
									style : {
										'float' : 'right',
										'margin-right' : '8px'
									},
									text : '确定',
									formBind : true,
									handler : function() {
										me.fireEvent('imageloaded');
									},
									scope : me
								} ]
							} ]
						} ];

						me.callParent(arguments);
						me.setTitle('插入/编辑 图片');
					},

					/**
					 * Returns the current image with all the data specified in
					 * the form. (Size, borders, padding e.t.c)
					 * 
					 * @return {HTMLImageObject}
					 */
					getImage : function() {
						// we have to create the node on iframe's document or
						// Opera will explode!
						
						var values = this.down('form').getForm().getValues();
						
						if(!this.iframeDoc) {
							return values['src'];
						}
						var image = this.iframeDoc.createElement("img");
						

						// set image attrs
						image.setAttribute('src', values['src']);
						if (values['title'])
							image.setAttribute('title', values['title']);
						if (values['className'])
							image.className = values['className'];
						if (values['display'])
							image.style.display = values['display'];
						if (values['width'])
							image.style.width = values['width'] + values['widthUnits'];
						if (values['height'])
							image.style.height = values['height'] + values['heightUnits'];
						if (values['paddingTop'])
							image.style.paddingTop = values['paddingTop'] + values['paddingTopUnits'];
						if (values['paddingBottom'])
							image.style.paddingBottom = values['paddingBottom'] + values['paddingBottomUnits'];
						if (values['paddingLeft'])
							image.style.paddingLeft = values['paddingLeft'] + values['paddingLeftUnits'];
						if (values['paddingRight'])
							image.style.paddingRight = values['paddingRight'] + values['paddingRightUnits'];
						if (values['marginTop'])
							image.style.marginTop = values['marginTop'] + values['marginTopUnits'];
						if (values['marginBottom'])
							image.style.marginBottom = values['marginBottom'] + values['marginBottomUnits'];
						if (values['marginLeft'])
							image.style.marginLeft = values['marginLeft'] + values['marginLeftUnits'];
						if (values['marginRight'])
							image.style.marginRight = values['marginRight'] + values['marginRightUnits'];
						if (values['cssFloat'] != 'none') {
							if (Ext.isIE) {
								image.style.styleFloat = values['float'];
							} else
								image.style.cssFloat = values['float'];
						}

						// internet explorer add this two attrs, and we dont
						// need them
						image.removeAttribute("width");
						image.removeAttribute("height");

						return image;
					},

					// private
					_comboExpand : function(combo, options) {
						// I have to do this here because if I do store.load
						// after Image Upload or Image Delete, the paging
						// toolbar disappears.
						var me = this;
						if (combo.needsRefresh) {
							combo.store.currentPage = 1;
							combo.store.load({
								start : 0,
								limit : me.pageSize,
								page : 1
							})
						} else
							combo.needsRefresh = false;
					},

					// private
					_comboChange : function(combo, oldValue, newValue) {
						// in ie8 sometimes this event is fired and I dont know
						// why. So if newValue is not defined I just ignore it.
						if (newValue == undefined)
							return;
						this._setPreviewImage(combo.getValue(), true);
					},

					// private
					_comboSelect : function(combo) {
						this._setPreviewImage(combo.getValue(), true);
					},

					// private
					_attachOnLoadEvent : function(comp) {
						var flyImg = Ext.fly(comp.getEl().dom);
						comp.mon(flyImg, 'load', this._resizePreviewImage, comp);
					},

					// private
					// loads the selected image info into the form
					_loadImageDetails : function() {

						var image = this.imageToEdit;

						// if user has an image selected get the image attrs
						if (image != "") {

							var cssFloat = "";

							if (!image.style)
								image.style = new Object();

							if (Ext.isIE) {
								cssFloat = image.style.styleFloat ? image.style.styleFloat : 'none';
							} else {
								cssFloat = image.style.cssFloat ? image.style.cssFloat : 'none';
							}

							var values = {
								'display' : image.style.display ? image.style.display : '',
								'widthUnits' : image.style.width ? image.style.width.replace(/[\d.]/g, "") : 'px',
								'display' : image.style.display ? image.style.display : '',
								'widthUnits' : image.style.width ? image.style.width.replace(/[\d.]/g, "") : 'px',
								'heightUnits' : image.style.height ? image.style.height.replace(/[\d.]/g, "") : 'px',
								'paddingTop' : image.style.paddingTop ? image.style.paddingTop.replace(/[^\d.]/g, "") : '',
								'paddingTopUnits' : image.style.paddingTop ? image.style.paddingTop.replace(/[\d.]/g, "") : 'px',
								'paddingLeft' : image.style.paddingLeft ? image.style.paddingLeft.replace(/[^\d.]/g, "") : '',
								'paddingLeftUnits' : image.style.paddingLeft ? image.style.paddingLeft.replace(/[\d.]/g, "") : 'px',
								'paddingBottom' : image.style.paddingBottom ? image.style.paddingBottom.replace(/[^\d.]/g, "") : '',
								'paddingBottomUnits' : image.style.paddingBottom ? image.style.paddingBottom.replace(/[\d.]/g, "") : 'px',
								'paddingRight' : image.style.paddingRight ? image.style.paddingRight.replace(/[^\d.]/g, "") : '',
								'paddingRightUnits' : image.style.paddingRight ? image.style.paddingRight.replace(/[\d.]/g, "") : 'px',
								'marginTop' : image.style.marginTop ? image.style.marginTop.replace(/[^\d.]/g, "") : '',
								'marginTopUnits' : image.style.marginTop ? image.style.marginTop.replace(/[\d.]/g, "") : 'px',
								'marginLeft' : image.style.marginLeft ? image.style.marginLeft.replace(/[^\d.]/g, "") : '',
								'marginLeftUnits' : image.style.marginLeft ? image.style.marginLeft.replace(/[\d.]/g, "") : 'px',
								'marginBottom' : image.style.marginBottom ? image.style.marginBottom.replace(/[^\d.]/g, "") : '',
								'marginBottomUnits' : image.style.marginBottom ? image.style.marginBottom.replace(/[\d.]/g, "") : 'px',
								'marginRight' : image.style.marginRight ? image.style.marginRight.replace(/[^\d.]/g, "") : '',
								'marginRightUnits' : image.style.marginRight ? image.style.marginRight.replace(/[\d.]/g, "") : 'px',
								'title' : image.title,
								'className' : image.className.replace("x-htmleditor-imageupload-bordeResize", "").replace(
										"x-htmleditor-imageupload-bordeSelect", ""),
								'float' : cssFloat
							};

							this.down('form').getForm().setValues(values);

							// show the image preview
							this._setPreviewImage(image.src, false);

							// I do this here because I dont want to fire the
							// change events
							// In IE 8 combobox change event is fired even with
							// setRawValue. Bug?
							this.down('[name=src]').setRawValue(image.src);
							this.down('[name=width]').setRawValue(image.style.width ? image.style.width.replace(/[^\d.]/g, "") : image.width);
							this.down('[name=height]').setRawValue(image.style.height ? image.style.height.replace(/[^\d.]/g, "") : image.height);

							this.down('#fieldOptions').expand();

						} else
							this.down('#fieldOptions').collapse();
					},

					// private
					// enables/disables constrain proportion toggle
					_toggleConstrain : function(btn) {
						var me = this;
						if (!btn.pressed) {
							btn.removeCls('x-htmleditor-imageupload-constrain');
							btn.addCls('x-htmleditor-imageupload-unconstrain');
						} else {
							btn.removeCls('x-htmleditor-imageupload-unconstrain');
							btn.addCls('x-htmleditor-imageupload-constrain');

							me.down('[name=width]').setRawValue(me.down('#naturalWidth').getValue());
							me.down('[name=height]').setRawValue(me.down('#naturalHeight').getValue());
						}
					},

					// private
					_deleteImage : function(ev, a) {

						var me = this;

						if (!me.disableDelete) {
							Ext.Msg.show({
								title : "确认提示",
								msg : "您确定删除该图片吗？",
								buttons : Ext.Msg.YESNO,
								closable : false,
								fn : function(btn) {
									if (btn == 'yes') {
										console.log(a);
										Ext.Ajax.request({
											url : me.managerUrl,
											method : 'POST',
											params : {
												'action' : 'delete',
												'id' : a.getAttribute ? a.getAttribute('img_id') : a
											},
											success : function(response) {

												var result = Ext.JSON.decode(response.responseText);
												if (result.success) {
													var idField = me.down('[name=id]');
													idField.setValue('');

													// delete the image from the
													// list
													var combo = me.down('[name=src]');

													// if I do here a
													// combo.store.load() to
													// refresh, the paging
													// toolbar disappears
													// so I'll do it on combo
													// expand event
													combo.needsRefresh = true;

													combo.setValue('');
													me.down('form').getForm().reset();
													me._setPreviewImage('blank', true);
												} else {
													Ext.Msg.alert("错误", 'Error: ' + result.errors);
												}
											}
										});
									}
								}
							});
						}
					},

					// private
					// method to upload the image to the server
					_uploadImage : function(fileField) {

						var me = this;

						var form = fileField.up('form').getForm();
						if (form.isValid()) {
							form.submit({
								url : me.submitUrl + '?action=upload',
								params : {
									paths : [ "id", "name", "source", "thumbnail" ]
								},
								waitMsg : '图片上传中...',
								success : function(fp, o) {
									Ext.Msg.alert('提示', '你的图片已经成功上传.');
									var idField = me.down('[name=id]');
									idField.setRawValue(o.result.data['id']);
									var combo = me.down('[name=src]');
									combo.needsRefresh = true;
									combo.setRawValue(o.result.data['source']);
									me._setPreviewImage(o.result.data['source'], true);
								},
								failure : function(form, action) {
									if (action.result)
										Ext.Msg.alert('Error', 'Error: ' + action.result.errors);
									me.down('[name=file]').reset();
								}
							});
						}
					},

					// private
					_serverAction : function(params) {

						var me = this;

						Ext.Ajax.request({
							url : me.managerUrl,
							method : 'POST',
							params : params,
							success : function(response) {

								var result = Ext.JSON.decode(response.responseText);

								if (result.success) {
									var idField = me.down('[name=id]');
									idField.setValue(result.data['id']);
									var combo = me.down('[name=src]');
									combo.needsRefresh = true;
									combo.setRawValue(result.data['source']);
									me._setPreviewImage(result.data['source'], true);

								} else {
									Ext.Msg.alert('Error', 'Error: ' + result.errors);
								}
							}
						});
					},

					// private
					_rotateImageClick : function() {
						var me = this;
						me._serverAction({
							action : 'rotate',
							id : me.down('[name=id]').getValue()
						});
					},

					// private
					_resizeImageClick : function() {
						var me = this;
						var width = me.down('[name=width]').getValue();
						var height = me.down('[name=height]').getValue();

						Ext.Msg.show({
							title : 'Confirmation',
							msg : 'Image will be permanently resized to: ' + width + 'x' + height + ' px',
							buttons : Ext.Msg.YESNO,
							closable : false,
							fn : function(btn) {
								if (btn == 'yes') {
									me._serverAction({
										action : 'resize',
										id : me.down('[name=id]').getValue(),
										width : width,
										height : height
									});
								}
							}
						});
					},

					// private
					_deleteImageClick : function() {
						var me = this;
						var id = me.down('[name=id]').getValue();
						me._deleteImage(null, id);
					},

					// private
					_openCropDialogClick : function() {
						var me = this;
						var imageId = me.down('[name=id]').getValue();

						me.cropDialog = Ext.create('app.ux.form.ImageCropDialog', {
							imageId : imageId,
							managerUrl : me.managerUrl
						});

						me.cropDialog.on('imagecropped', function() {

							var combo = me.down('[name=src]');
							combo.needsRefresh = true;
							me._setPreviewImage(me.cropDialog.imgSrc, true);
							me.cropDialog.close()
						});

						me.cropDialog.show();
					},

					// private
					// Check if the image size respects aspect ratio
					_checkConstrain : function(combo, newValue, oldValue) {
						var sizeField = combo.up('form').down('[name=' + combo.constrainName + ']');
						if (!newValue || !sizeField.getValue())
							return;
						if (newValue <= 0) {
							combo.setRawValue(oldValue);
							return;
						}
						if (combo.up('form').down('#constraintProp').pressed) {
							var ratio = combo.up('form').down('#ratio').getValue();

							// if I dont suspendEvents IE 8 fires change event
							// and this function enters into a loop
							sizeField.suspendEvents();
							sizeField.setRawValue(Math.round((ratio * newValue)));
						}
					},

					// private
					// Loads selected image on preview panel
					_setPreviewImage : function(src, resetImageSize) {
						if (!this.previewComponent)
							this.previewComponent = this.down('#vistaPrevia');

						this.previewComponent.setWidth('');
						this.previewComponent.setHeight('');
						this.previewComponent.resetImageSize = resetImageSize;

						var img = new Image();
						img.src = src;

						var me = this;
						// if src is avaible show the image in preview panel
						img.onload = function() {
							// when I change the src the _resizePreviewImage
							// method will be fired.
							// It happens because _resizePreviewImage is
							// attached to the image onload event
							if (!(/^https:\/\//.test(src)))
								me.previewComponent.setSrc(src + '?' + Math.floor(Math.random() * 111111));
							else
								me.previewComponent.setSrc(src);
						};

					},

					// private
					// Resizes the image to fit on the preview panel
					_resizePreviewImage : function(evt, el) {
						var width, height;
						var comp = this;
						var myForm = comp.up('form');
						var image = el;
						var maxWidth = maxHeight = 124;
						var constrainComp = myForm.down('#constraintProp');
						var widthComp = myForm.down('[name=width]');
						var heightComp = myForm.down('[name=height]')

						// ExtJS 4.1 Need to remove the preview image style to
						// get the right sizes
						image.removeAttribute("style");

						// save real image size
						myForm.down('#naturalWidth').setValue(image.width);
						myForm.down('#naturalHeight').setValue(image.height);
						myForm.down('#ratio').setValue(image.height / image.width);
						myForm.down('#realSize').setValue(image.width + 'x' + image.height);

						// enable server image editing buttons
						if (!comp.disableServerSideEdit) {
							if (image.src != '' && image.src != 'blank' && image.src.search(document.domain) >= 0) {
								myForm.down('#cropButton').enable();
								myForm.down('#rotateButton').enable();
								myForm.down('#resizeButton').enable();
								myForm.down('#deleteButton').enable();
							} else {
								myForm.down('#cropButton').disable();
								myForm.down('#rotateButton').disable();
								myForm.down('#resizeButton').disable();
								myForm.down('#deleteButton').disable();
							}
						}

						if (comp.resetImageSize == true) {
							widthComp.setRawValue(image.width);
							heightComp.setRawValue(image.height);
							myForm.down('[name=widthUnits]').setRawValue('px');
							myForm.down('[name=heightUnits]').setRawValue('px');

						} else {
							// toggle off constrain button if image ratio is
							// different
							if (Math.round(widthComp.getValue() / heightComp.getValue()) != Math.round(image.width / image.height))
								constrainComp.toggle(false);
						}

						if (image.width >= image.height) {
							width = image.width < maxWidth ? image.width : maxWidth;
							height = Math.ceil((width / image.width) * image.height)
						} else {
							height = image.height < maxHeight ? image.height : maxHeight;
							width = Math.ceil((height / image.height) * image.width)
						}

						comp.setWidth(width);
						comp.setHeight(height);
					}
				});