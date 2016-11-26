Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.define('storeModel', { extend : 'Ext.data.Model', fields : [ 'id', 'groupName','sequence' ],
		idProperty : 'id' });

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		model : 'storeModel',
		autoLoad : true,
		proxy : {
			type : 'ajax',
			url : '../menu/menufunc-group!list.action',
			reader : { type : 'json', totalProperty : 'totalCount', root : 'results', successProperty : 'success',
				idProperty : 'id' } } });

	var success = function(form, window, store) {
		form.getForm().reset();
		window.hide();
		store.load();
	};
	

	var addForm = Ext.create('Ext.form.Panel', {
		layout : 'form',
		id : 'form',
		url : '../menu/menufunc-group!add.action',
		frame : true,
		bodyPadding : '5 5 0',
		fieldDefaults : { msgTarget : 'side', labelWidth : 75 },
		defaultType : 'textfield',
		items : [ { fieldLabel : '序号', name : 'sequence', allowBlank : false },
		          { fieldLabel : '菜单组名', name : 'groupName', allowBlank : false }],

		buttons : [ { text : '保存', handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				form.submit( { success : function(form, action) {
					Ext.Msg.alert('成功', action.result.msg);
					success(Ext.getCmp('form'), addWindow, store);
				}, failure : function(form, action) {
					Ext.Msg.alert('错误', action.result.msg);
				} });
			}
		} }, { text : '取消', handler : function() {
			this.up('form').getForm().reset();
			addWindow.hide();
		} } ] });

	var addWindow = Ext.create('widget.window', { height : 180, width : 400, title : '添加菜单组', closable : false,
		modal : true, layout : 'fit', resizable : false, items : [ addForm ] });

	var grid = Ext.create('Ext.grid.Panel', {
		region : 'center',
		title : "菜单组管理",
		store : store,
		loadMask : true,
		// grid columns
		columns : [
				{
					menuDisabled : true,
					sortable : false,
					xtype : 'actioncolumn',
					width : 25,
					items : [ {
						icon : '../../manager/images/del.png',
						tooltip : '删除',
						handler : function(grid, rowIndex, colIndex) {
							var rec = store.getAt(rowIndex);
							Ext.MessageBox.show( {
								title : '删除菜单组',
								msg : '是否确认要删除菜单组' + rec.data.groupName + '?',
								buttons : Ext.MessageBox.YESNO,
								fn : function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request( { url : '../menu/menufunc-group!del.action',
											params : { "id" : rec.data.id }, method : 'POST', success : function(r, o) {
												var strJson = r.responseText;
												var obj = eval("(" + strJson + ")");
												if (obj.success == true) {
													Ext.Msg.alert("信息", obj.msg);
													store.load();
												} else {
													Ext.Msg.alert("信息", obj.msg);
												}
											}, failure : function(form, action) {
												Ext.Msg.alert("信息", "系统错误！");
											} });
									}
								}, icon : Ext.MessageBox.QUESTION });

						} } ], hideTitle : true },
				{ text : "序号", dataIndex : 'sequence', flex : 0.6, sortable : false },
				{ text : "菜单组名", dataIndex : 'groupName', flex : 0.6, sortable : false }],
		// paging bar on the bottom
		bbar : Ext.create('Ext.PagingToolbar', { store : store, displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}', emptyMsg : "无数据" }),
		dockedItems : [ { xtype : 'toolbar',
			items : [ { text : '查询', icon : '../../manager/images/find.png', handler : function() {
				store.load();
			} }, '-', { text : '添加菜单组', icon : '../../manager/images/find.png', handler : function() {
				addWindow.show();
			} } ] } ] });

	var viewport = Ext.create('Ext.Viewport', { layout : { type : 'border', padding : 5 }, items : [ grid ] })

});
