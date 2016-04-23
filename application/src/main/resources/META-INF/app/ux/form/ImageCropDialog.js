Ext.define('app.ux.form.ImageCropDialog', {
	extend : 'Ext.window.Window',
	imgId : '',
	imgSrc : '',
	randomId : '',
	bodyCls : 'x-htmleditor-imageupload-cropdialog',
	naturalWidth : 0,
	naturalHeight : 0,
	maxWidth : 700,
	maxHeight : 500,
	height : 350,
	width : 400,
	minHeight : 350,
	minWidth : 400,
	myResizer : null,
	managerUrl : null,
	autoScroll : true,
	initComponent : function() {

		var me = this;

		Ext.applyIf(me, {
			items : [ {
				xtype : 'container',
				html : '<div id="myResizable" style="position: absolute;z-index:9999;"></div>'
			}, {
				xtype : 'image',
				itemId : 'imageToCrop',
				src : me.imgSrc + '?' + Math.floor(Math.random() * 111111),
				listeners : {
					afterrender : me._attachOnLoadEvent,
					scope : me
				}
			} ],
			dockedItems : [ {
				xtype : 'toolbar',
				dock : 'top',
				items : [ {
					labelWidth : 50,
					xtype : 'slider',
					itemId : 'zoomSlider',
					width : 150,
					value : 100,
					minValue : 0,
					maxValue : 200,
					fieldLabel : 'Zoom',
					listeners : {
						change : me._sliderChange,
						scope : me
					}
				} ]
			}, {
				xtype : 'container',
				dock : 'bottom',
				padding : 4,
				items : [ {
					xtype : 'button',
					style : {
						'float' : 'right'
					},
					text : 'Cancel',
					handler : me.close,
					scope : me
				}, {
					xtype : 'button',
					style : {
						'float' : 'right',
						'margin-right' : '8px'
					},
					text : 'OK',
					handler : me._cropImage,
					scope : me
				} ]
			} ]
		});

		me.callParent(arguments);
		me.setTitle('Crop Image');
	},

	// private
	_attachOnLoadEvent : function(comp) {
		var me = this;
		var flyImg = Ext.fly(comp.getEl().dom);
		comp.mon(flyImg, 'load', me._setupResizer, comp);
	},

	// private
	_sliderChange : function(slider) {
		var me = this;
		var imgToCrop = me.down('#imageToCrop');
		var zoom = Math.round(me.naturalWidth * (slider.getValue() / 100));
		imgToCrop.setWidth(zoom);
	},

	// private
	_setupResizer : function(ev, el) {
		var imageComp = this;
		var cropWindow = this.up('window');

		cropWindow.naturalWidth = el.width;
		cropWindow.naturalHeight = el.height;

		cropWindow.setWidth(el.width + 12);
		cropWindow.setHeight(el.height + 94);
		cropWindow.center();

		if (!cropWindow.myResizer)
			cropWindow.myResizer = Ext.create('Ext.resizer.Resizer', {
				el : 'myResizable',
				constrainTo : imageComp.getEl(),
				handles : 'all',
				minWidth : 16,
				minHeight : 16,
				width : 32,
				height : 32,
				pinned : true
			});
	},

	// private
	// method to crop the image
	_cropImage : function(fileField) {

		var me = this;

		Ext.Msg.show({
			title : 'Confirmation',
			msg : 'Are you sure you want to crop this image?',
			buttons : Ext.Msg.YESNO,
			closable : false,
			fn : function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
						url : me.managerUrl,
						method : 'POST',
						params : {
							'action' : 'crop',
							'id' : me.imgId,
							'zoom' : me.down('#zoomSlider').getValue(),
							'width' : me.myResizer.getEl().dom.offsetWidth,
							'height' : me.myResizer.getEl().dom.offsetHeight,
							'offsetLeft' : me.myResizer.getEl().dom.offsetLeft,
							'offsetTop' : me.myResizer.getEl().dom.offsetTop
						},
						success : function(response) {

							var result = Ext.JSON.decode(response.responseText);

							if (result.success) {
								me.imgSrc = result.data['src'];
								me.fireEvent('imagecropped');
							} else {
								Ext.Msg.alert('Error', 'Error: ' + result.errors);
							}
						}
					});
				}
			}
		});
	}
});