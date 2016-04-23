Ext.define('app.ux.form.Image', {
	extend : 'Ext.form.field.Text',
	alias : 'widget.imagefield',

	needArrowKeys : false,

	triggers : {
		filebutton : {
			type : 'component',
			hideOnReadOnly : false
		}
	},

	// <locale>
	/**
	 * @cfg {String} buttonText The button text to display on the upload button.
	 *      Note that if you supply a value for {@link #buttonConfig}, the
	 *      buttonConfig.text value will be used instead if available.
	 */
	buttonText : '选择文件',
	// </locale>

	/**
	 * @cfg {Boolean} buttonOnly True to display the file upload field as a
	 *      button with no visible text field. If true, all inherited Text
	 *      members will still be available.
	 */
	buttonOnly : false,

	/**
	 * @cfg {Number} buttonMargin The number of pixels of space reserved between
	 *      the button and the text field. Note that this only applies if
	 *      {@link #buttonOnly} = false.
	 */
	buttonMargin : 3,

	/**
	 * @cfg {Boolean} clearOnSubmit True to clear the selected file value when
	 *      the form this field belongs to is submitted to the server.
	 */
	clearOnSubmit : true,

	/**
	 * @cfg {Object} buttonConfig Specify optional custom button
	 *      {@link Ext.button.Button} config (eg. iconCls, text) for the upload
	 *      button
	 */

	/**
	 * @event change Fires when the underlying file input field's value has
	 *        changed from the user selecting a new file from the system file
	 *        selection dialog.
	 * @param {Ext.ux.form.FileUploadField}
	 *            this
	 * @param {String}
	 *            value The file value returned by the underlying file input
	 *            field
	 */

	/**
	 * @property {Ext.dom.Element} fileInputEl A reference to the invisible file
	 *           input element created for this upload field. Only populated
	 *           after this component is rendered.
	 */

	/**
	 * @property {Ext.button.Button} button A reference to the trigger Button
	 *           component created for this upload field. Only populated after
	 *           this component is rendered.
	 */

	/**
	 * @private
	 */
	extraFieldBodyCls : Ext.baseCSSPrefix + 'form-file-wrap',

	/**
	 * @private
	 */
	inputCls : Ext.baseCSSPrefix + 'form-text-file',

	/**
	 * @cfg {Boolean} [readOnly=true] Unlike with other form fields, the
	 *      readOnly config defaults to true in File field.
	 */
	readOnly : false,

	/**
	 * @cfg {Boolean} editable
	 * @inheritdoc
	 */
	editable : true,

	submitValue : true,

	/**
	 * Do not show hand pointer over text field since file choose dialog is only
	 * shown when clicking in the button
	 * 
	 * @private
	 */
	triggerNoEditCls : '',

	/**
	 * @private Extract the file element, button outer element, and button
	 *          active element.
	 */
	childEls : [ 'browseButtonWrap' ],

	/**
	 * @private
	 */
	applyTriggers : function(triggers) {
		var me = this, triggerCfg = (triggers || {}).filebutton;

		if (triggerCfg) {
			triggerCfg.component = Ext.apply({
				xtype : 'button',
				ownerCt : me,
				id : me.id + '-button',
				ui : me.ui,
				disabled : me.disabled,
				text : me.buttonText,
				style : me.buttonOnly ? '' : me.getButtonMarginProp() + me.buttonMargin + 'px',
				inputName : me.getName(),
				listeners : {
					scope : me,
					click : me.onFileChange
				}
			}, me.buttonConfig);

			return me.callParent([ triggers ]);
		}
		// <debug>
		else {
			Ext.raise(me.$className + ' requires a valid trigger config containing "filebutton" specification');
		}
		// </debug>
	},

	getSubTplData : function(fieldData) {
		var data = this.callParent([ fieldData ]);

		// Input field itself should not be focusable since it's always
		// decorative;
		// however the input element is naturally focusable (and tabbable) so we
		// have to
		// deactivate it by setting its tabIndex to -1.
		data.tabIdx = -1;

		return data;
	},

	/**
	 * @private
	 */
	onRender : function() {
		var me = this, inputEl, button, buttonEl, trigger;

		me.callParent(arguments);

		inputEl = me.inputEl;

		trigger = me.getTrigger('filebutton');
		button = me.button = trigger.component;
		me.fileInputEl = button.fileInputEl;
		buttonEl = button.el;

		if (me.buttonOnly) {
			me.inputWrap.setDisplayed(false);
			me.shrinkWrap = 3;
		}

		// Ensure the trigger element is sized correctly upon render
		trigger.el.setWidth(buttonEl.getWidth() + buttonEl.getMargin('lr'));
		if (Ext.isIE) {
			me.button.getEl().repaint();
		}
	},

	/**
	 * Gets the markup to be inserted into the subTplMarkup.
	 */
	getTriggerMarkup : function() {
		return '<td id="' + this.id + '-browseButtonWrap" data-ref="browseButtonWrap" role="presentation"></td>';
	},

	managerUrl : Ext.ctx + '/image',
	submitUrl : Ext.ctx + '/image',
	/**
	 * @private Event handler fired when the user selects a file.
	 */
	onFileChange : function(button, e, value) {

		var me = this;
		var sel = "";
		var range = "";
		var image = "";

		var uploadDialog = Ext.create('app.ux.form.ImageDialog', {
			submitUrl : me.submitUrl,
			managerUrl : me.managerUrl,
			imageToEdit : image,
			pageSize : me.pageSize,
			disableServerSideEdit : false,
			disableStyling : false,
			disableDelete : false
		});

		uploadDialog.on('imageloaded', function() {
			var newImage = this.getImage();
			me.inputEl.dom.value = newImage;
			this.close();
		});

		uploadDialog.show();

	},

	reset : function() {
		var me = this, clear = me.clearOnSubmit;
		if (me.rendered) {
			me.button.reset(clear);
			me.fileInputEl = me.button.fileInputEl;
			if (clear) {
				me.inputEl.dom.value = '';
			}
		}
		me.callParent();
	},

	onShow : function() {
		this.callParent();
		// If we started out hidden, the button may have a messed up layout
		// since we don't act like a container
		this.button.updateLayout();
	},

	onDisable : function() {
		this.callParent();
		this.button.disable();
	},

	onEnable : function() {
		this.callParent();
		this.button.enable();
	},

	restoreInput : function(el) {
		// If we're not rendered we don't need to do anything, it will be
		// created
		// when we get flushed to the DOM.
		if (this.rendered) {
			var button = this.button;
			button.restoreInput(el);
			this.fileInputEl = button.fileInputEl;
		}
	},

	onDestroy : function() {
		this.fileInputEl = this.button = null;
		this.callParent();
	},

	getButtonMarginProp : function() {
		return 'margin-left:';
	}
});