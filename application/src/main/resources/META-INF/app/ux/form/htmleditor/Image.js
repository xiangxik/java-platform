Ext.define('app.ux.form.htmleditor.Image', {
	alias : 'plugin.htmleditorimage',
	/**
	 * @cfg {Array} options Associative array with all the strings. If not
	 *      specified it will show all the strings in english
	 */
	lang : {
		'Display' : '显示',
		'By Default' : '默认',
		'Inline' : '单行',
		'Block' : '块',
		'Insert/Edit Image' : '插入/编辑 图片',
		'Upload Image...' : '上传图片...',
		'Uploading your photo...' : '图片上传中...',
		'Error' : '错误',
		'Width' : '宽度',
		'Height' : '高度',
		'Align' : '对齐',
		'Title' : '标题',
		'Class' : '类',
		'Padding' : '内间隔',
		'Margin' : '外间隔',
		'Top' : '上',
		'Bottom' : '下',
		'Right' : '右',
		'Left' : '左',
		'None' : '无',
		'Size & Details' : '大小 & 细节',
		'More Options' : '更多选项',
		'Style' : '样式',
		'OK' : '确定',
		'Cancel' : '取消',
		'Delete Image' : '删除图片',
		'Confirmation' : '确认信息',
		'Are you sure you want to delete this image?' : '你确定要删除这个图片吗？',
		'Your photo has been uploaded.' : '你的图片已经上传。',
		'Real Size' : '原始大小'
	},

	/**
	 * @cfg {String} submitUrl Path to the upload script. Default
	 *      'htmlEditorImageUpload.php'
	 */
	submitUrl : Ext.ctx + '/image',

	/**
	 * @cfg {String} serverSideEdit Enables/disables server side image editing
	 *      buttons. Default false
	 */
	disableServerSideEdit : false,

	/**
	 * @cfg {String} serverSideEdit Enables/disables server side image deletion.
	 *      Default false
	 */
	disableDelete : false,

	/**
	 * @cfg {String} styling Enables/disables image css styling. Default false
	 */
	disableStyling : false,

	/**
	 * @cfg {String} mamangerUrl Path to the image manager script. Default
	 *      'htmlEditorImageManager.php'
	 */
	managerUrl : Ext.ctx + '/image',

	/**
	 * @cfg {integer} pageSize Number of images to show on the list. Default 6
	 */
	pageSize : 12,

	/**
	 * @cfg {Boolean} values are: true : Default Allows the user to resize an
	 *      image clicking on it and dragging with the mouse. (Only WebKit
	 *      browsers) false The image wont be resized if the user drags on it
	 */
	dragResize : true,

	/**
	 * @cfg {Boolean} values are: false : Default Context menu for images
	 *      enabled true Context menu will not be avaible
	 */
	enableContextMenu : false,

	/**
	 * @cfg {Boolean} values are: true : Default Allows the user to resize an
	 *      image clicking on it and using the mousewheel. (Only WebKit browsers &
	 *      Opera)
	 * 
	 * false The image wont be resized if the user uses mousewheel on it
	 */
	wheelResize : true,

	/**
	 * @cfg {String} iframeCss Path to the iframe css file. It's important to do
	 *      not merge this css with other CSS files, because it will be applied
	 *      to the htmleditor iframe head. If more css rules are included, it
	 *      can suffer undesired effects Default 'css/iframe_styles.css'
	 */
	// iframeCss : 'css/iframe_styles.css',
	t : function(string) {
		return this.lang[string] ? this.lang[string] : string;
	},

	constructor : function(config) {
		Ext.apply(this, config);
		this.callParent(arguments);
	},

	init : function(panel) {
		this.cmp = panel;
		this.cmp.on('render', this.onRender, this);
		this.cmp.on('initialize', this.initialize, this);
		this.cmp.on('beforedestroy', this.beforeDestroy, this);
	},

	initialize : function() {
		var me = this;
		var cmpDoc = this.cmp.getDoc();
		me.flyDoc = Ext.fly(cmpDoc);

		// Inject custom css file to iframe's head in order to simulate image
		// control selector on click, over webKit and Opera browsers
		if ((Ext.isWebKit || Ext.isOpera))
			me._injectCss(me.cmp, me.iframeCss);

		// attach context menu
		if (me.enableContextMenu)
			me._contextMenu();

		// attach events to control when the user interacts with an image
		me.cmp.mon(me.flyDoc, 'dblclick', me._dblClick, me, {
			delegate : "img"
		});
		me.cmp.mon(me.flyDoc, 'mouseup', me._docMouseUp, me);
		me.cmp.mon(me.flyDoc, 'paste', me._removeSelectionHelpers, me);

		// mousewheel resize event
		if ((Ext.isWebKit || Ext.isOpera) && me.wheelResize) {
			me.cmp.mon(me.flyDoc, 'mousewheel', me._wheelResize, me, {
				delegate : "img"
			});
		}

		// mouse drag resize event
		if (Ext.isWebKit && me.dragResize) {
			me.cmp.mon(me.flyDoc, 'drag', me._dragResize, me, {
				delegate : "img"
			});
		}
	},

	beforeDestroy : function() {
		var me = this;
		if (me.uploadDialog)
			me.uploadDialog.destroy();
		if (me.contextMenu)
			me.contextMenu.destroy();
	},

	onRender : function() {

		var me = this;
		var imageButton = Ext.create('Ext.button.Button', {
			iconCls : 'x-htmleditor-imageupload',
			handler : me._openImageDialog,
			scope : me,
			tooltip : me.t('插入/编辑 图片'),
			overflowText : me.t('插入/编辑 图片')
		});

		var toolbar = me.cmp.getToolbar();

		// we save a reference to this button to use it later
		me.imageButton = imageButton;

		me.cmp.getToolbar().add(imageButton);

	},

	// private
	_contextMenu : function() {
		var me = this;

		if (!me.contextMenu) {
			var editAction = Ext.create('Ext.Action', {
				text : me.t('编辑'),
				iconCls : 'x-htmleditor-imageupload-editbutton',
				disabled : false,
				handler : me._openImageDialog,
				scope : me
			});

			var deleteAction = Ext.create('Ext.Action', {
				iconCls : 'x-htmleditor-imageupload-deletebutton',
				text : me.t('删除'),
				disabled : false,
				handler : function() {
					me.cmp.execCmd('delete')
				}
			});

			var contextMenu = Ext.create('Ext.menu.Menu', {
				closeAction : 'hide',
				items : [ editAction, deleteAction ]
			});
			me.contextMenu = contextMenu;
		}

		me.cmp.mon(me.flyDoc, 'contextmenu', function(e, htmlEl) {
			e.stopEvent();
			e.stopPropagation();
			var iframePos = this.cmp.getPosition();
			var elementPos = e.getXY();
			var pos = [ iframePos[0] + elementPos[0], iframePos[1] + elementPos[1] + 30 ];
			if (e.getTarget().tagName == 'IMG')
				;
			Ext.Function.defer(function() {
				me.contextMenu.showAt(pos)
			}, 100);
		}, me, {
			delegate : 'img'
		});
	},

	// private
	// instead of overriding the htmleditor header method we just append another
	// css file to it's iframe head
	_injectCss : function(cmp, cssFile) {
		var frameName = cmp.iframeEl.dom.name;
		var iframe;

		if (document.frames)
			iframe = document.frames[frameName];
		else
			iframe = window.frames[frameName];

		// we have to add our custom css file to the iframe
		var ss = iframe.document.createElement("link");
		ss.type = "text/css";
		ss.rel = "stylesheet";
		ss.href = cssFile;

		if (document.all)
			iframe.document.createStyleSheet(ss.href);
		else
			iframe.document.getElementsByTagName("head")[0].appendChild(ss);

	},

	// private
	_dblClick : function(evt) {
		var me = this;
		var target = evt.getTarget();

		if (target.tagName == "IMG") {
			me._openImageDialog()
		}
	},

	// private
	_openImageDialog : function() {

		var me = this;
		var cmp = this.cmp;
		var doc = this.cmp.getDoc();
		var win = this.cmp.win;
		var sel = "";
		var range = "";
		var image = "";
		var imagesList = doc.body.getElementsByTagName("IMG");
		var imagesListLength = imagesList.length;

		// insertAtCursor function is completely useless for this purpose, so I
		// need to write all this stuff to insert html at caret position
		// I need to know if the browser uses the W3C way or the Internet
		// Explorer method
		var ieBrowser = doc.selection && doc.selection.createRange ? true : false;
		var nonIeBrowser = win.getSelection && win.getSelection().getRangeAt ? true : false;

		if (nonIeBrowser) {
			sel = win.getSelection();
			// if focus is not in htmleditor area
			try {
				range = sel.getRangeAt(0);
			} catch (err) {
				win.focus();
				range = sel.getRangeAt(0);
			}

		} else if (ieBrowser) {
			// it's compulsory to get the focus before creating the range, if
			// not we'll lose the caret position
			win.focus();
			sel = doc.selection;
			range = sel.createRange();
		}

		// to make the things easier, if the user has an image selected when he
		// presses the image upload button, I'll mark it with a custom attr
		// "iu_edit".
		// afterwards, if the user presses the ok button I just need to find the
		// image with that attr, and replace it with the new one.
		if (Ext.isIE && sel.type == "Control" && range.item(0).tagName == "IMG") {
			image = r;
		} else if (range.startContainer == range.endContainer) {
			if (range.endOffset - range.startOffset < 2) {
				if (range.startContainer.hasChildNodes()) {
					var r = range.startContainer.childNodes[range.startOffset];
					if (r.tagName) {
						if (r.tagName == "IMG")
							image = r;
					}
				}
			}
		}

		if (!image) {
			// if we dont find the image we try to search by editable attr
			for (i = 0; i < imagesListLength; i++) {
				if (parseInt(imagesList[i].getAttribute('iu_edit')) > 0) {
					image = imagesList[i];
					break;
				}
			}
		}

		me.uploadDialog = Ext.create('app.ux.form.ImageDialog', {
			lang : me.lang,
			t : me.t,
			submitUrl : me.submitUrl,
			managerUrl : me.managerUrl,
			iframeDoc : doc,
			imageToEdit : image,
			pageSize : me.pageSize,
			imageButton : me.imageButton,
			disableServerSideEdit : me.serverSideEdit,
			disableStyling : me.styling,
			disableDelete : me.disableDelete
		});

		me.uploadDialog.on('close', function() {
			if (Ext.isIE) {
				me.imageButton.toggle(false);
				me._removeSelectionHelpers()
			}
		}, me);

		// custom event that fires when the user presses the ok button on the
		// dialog
		me.uploadDialog.on('imageloaded', function() {

			var newImage = this.getImage();

			// if it's an edited image, we have to replace it with the new
			// values
			if (image != "") {
				for (i = 0; i < imagesListLength; i++) {
					if (parseInt(imagesList[i].getAttribute('iu_edit')) > 0) {
						if (nonIeBrowser) {
							imagesList[i].parentNode.replaceChild(newImage, imagesList[i]);
							try {
								if (sel) {
									sel.selectAllChildren(doc.body);
									sel.collapseToStart();
								}

							} catch (ex) {
							}
							;
						} else if (ieBrowser) {
							imagesList[i].outerHTML = newImage.outerHTML;
						}
						break;
					}
				}
			}
			// if not we just insert a new image on the document
			else {
				if (nonIeBrowser) {
					range.insertNode(newImage);
				} else if (ieBrowser) {
					win.focus();
					range.select();
					range.pasteHTML(newImage.outerHTML);
				}
			}

			me.imageToEdit = "";
			this.close();
			me.imageButton.toggle(false);
		});

		me.uploadDialog.show();
	},

	// private
	// Remove custom image attrs from the iframe body DOM
	_removeSelectionHelpers : function() {
		var me = this;
		var imagesList = me.cmp.getDoc().body.getElementsByTagName("IMG");
		var imagesListLength = imagesList.length;

		for (i = 0; i < imagesListLength; i++) {
			imagesList[i].removeAttribute('iu_edit');
		}
	},

	// private
	// When user uses mousewheel over an image
	_wheelResize : function(e) {
		var target = e.getTarget();
		if (target.tagName == "IMG" && target.getAttribute('iu_edit') == 1) {
			var delta = e.getWheelDelta();
			var width = target.style.width ? parseInt(target.style.width.replace(/[^\d.]/g, "")) : target.width;
			var height = target.style.height ? parseInt(target.style.height.replace(/[^\d.]/g, "")) : target.height;

			target.removeAttribute('height');
			target.style.removeProperty('height');

			// change just width to keep aspect ratio
			target.style.width = (delta < 1) ? width - 10 : width + 10;

			e.preventDefault();
		} else
			return;
	},

	// private
	// When user drags over an image
	_dragResize : function(e) {

		var target = e.getTarget();

		if (target.tagName == "IMG" && (target.getAttribute('iu_edit') == 1)) {
			var width = e.getX() - target.offsetLeft;
			var height = e.getY() - target.offsetTop;
			target.style.width = width + "px";
			target.style.height = height + "px";
			e.preventDefault();
		} else
			return;
	},

	// private
	// When user clicks on content editable area
	_docMouseUp : function(evt) {

		var me = this;
		var target = evt.getTarget();

		me._removeSelectionHelpers();

		if (target.tagName == "IMG") {
			me.imageButton.toggle(true);
			if ((me.wheelResize || me.dragResize) && (Ext.isWebKit || Ext.isOpera))
				target.setAttribute('iu_edit', '1');
			else
				target.setAttribute('iu_edit', '2');

			// select image.
			// On safari if we copy and paste the image, class attrs are
			// converted to inline styles. It's a browser bug.
			if (Ext.isWebKit) {
				var sel = this.cmp.getWin().getSelection ? this.cmp.getWin().getSelection() : this.cmp.getWin().document.selection;
				sel.setBaseAndExtent(target, 0, target, 1);
			}
		} else
			me.imageButton.toggle(false);
	}
});

