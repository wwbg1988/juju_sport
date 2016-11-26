Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.define('storeMoedl', { extend : 'Ext.data.Model',
		fields : [ 'funcName', 'funcAction', 'menuOrder', 'menufuncGroupName', 'id' ], idProperty : 'id' });

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		model : 'storeMoedl',
		autoLoad : true,
		proxy : {
			type : 'ajax',
			url : '../menu/menu!list.action',
			reader : { type : 'json', totalProperty : 'totalCount', root : 'results', successProperty : 'success',
				idProperty : 'id' } } });

	Ext.define('storeGroupMoedl', { extend : 'Ext.data.Model', fields : [ 'groupName', 'id' ], idProperty : 'id' });

	var groupStore = Ext.create('Ext.data.Store', {
		model : 'storeGroupMoedl',
		autoLoad : true,
		proxy : {
			type : 'ajax',
			url : '../menu/menu!listGroup.action',
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
		url : '../menu/menu!add.action',
		frame : true,
		bodyPadding : '5 5 0',
		fieldDefaults : { msgTarget : 'side', labelWidth : 75 },
		defaultType : 'textfield',
		items : [
				{ fieldLabel : '功能名称', name : 'funcName', allowBlank : false },
				{ fieldLabel : 'Action', name : 'funcAction', allowBlank : false },
				{ fieldLabel : '排序', name : 'menuOrder', xtype : 'numberfield', allowBlank : false },
				{ fieldLabel : '功能组名称', xtype : 'combobox', name : 'menufuncGroupId', displayField : 'groupName',
					valueField : 'id', store : groupStore, selectOnFocus : true, forceSelection : true,
					editable : false, allowBlank : false } ],

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

	var addWindow = Ext.create('widget.window', { height : 250, width : 400, title : '添加用户组', closable : false,
		modal : true, layout : 'fit', resizable : false, items : [ addForm ] });

	var grid = Ext.create('Ext.grid.Panel', {
		region : 'center',
		title : "功能管理",
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
								title : '删除功能',
								msg : '是否确认要删除功能' + rec.data.funcName + '?',
								buttons : Ext.MessageBox.YESNO,
								fn : function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request( { url : '../menu/menu!del.action',
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
				{ text : "功能名称", dataIndex : 'funcName', flex : 0.25, sortable : false },
				{ text : "Action", dataIndex : 'funcAction', flex : 0.25, sortable : false },
				{ text : "排序", dataIndex : 'menuOrder', flex : 0.25, sortable : false },
				{ text : "功能组名称", dataIndex : 'menufuncGroupName', flex : 0.25, sortable : false } ],
		dockedItems : [ { xtype : 'toolbar',
			items : [ { text : '查询', icon : '../../manager/images/find.png', handler : function() {
				store.load();
			} }, '-', { text : '添加功能', icon : '../../manager/images/find.png', handler : function() {
				addWindow.show();
			} } ] } ] });

	var viewport = Ext.create('Ext.Viewport', { layout : { type : 'border', padding : 5 }, items : [ grid ] });

});
