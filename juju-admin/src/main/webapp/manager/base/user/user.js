Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.define('storeMoedl', { extend : 'Ext.data.Model', fields : [ 'loginName', 'realName', 'id', 'userGroup' ],
		idProperty : 'id' });

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		model : 'storeMoedl',
		autoLoad : true,
		proxy : {
			type : 'ajax',
			url : '../user/user!list.action',
			reader : { type : 'json', totalProperty : 'totalCount', root : 'results', successProperty : 'success',
				idProperty : 'id' } } });

	var success = function(form, window, store) {
		form.getForm().reset();
		window.hide();
		store.load();
	};
	
	Ext.define('storeGroupMoedl', { extend : 'Ext.data.Model', fields : [ 'groupName', 'id' ], idProperty : 'id' });

	
	var groupStore = Ext.create('Ext.data.Store', {
		model : 'storeGroupMoedl',
		autoLoad : true,
		proxy : {
			type : 'ajax',
			url : '../user/user!listGroup.action',
			reader : { type : 'json', totalProperty : 'totalCount', root : 'results', successProperty : 'success',
				idProperty : 'id' } } });

	var addForm = Ext.create('Ext.form.Panel', {
		layout : 'form',
		id : 'form',
		url : '../user/user!add.action',
		frame : true,
		bodyPadding : '5 5 0',
		fieldDefaults : { msgTarget : 'side', labelWidth : 75 },
		defaultType : 'textfield',
		items : [ { fieldLabel : '登录名', name : 'loginName', allowBlank : false },
				{ fieldLabel : '用户名', name : 'realName', allowBlank : false },
				{ fieldLabel : '修理厂', xtype : 'combobox', name : 'userGroup', displayField : 'groupName',
					valueField : 'groupName', store : groupStore, selectOnFocus : true, forceSelection : true } ],

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

	var addWindow = Ext.create('widget.window', { height : 180, width : 400, title : '添加用户', closable : false,
		modal : true, layout : 'fit', resizable : false, items : [ addForm ] });

	var grid = Ext.create('Ext.grid.Panel', {
		region : 'center',
		title : "人员信息管理",
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
								title : '删除用户',
								msg : '是否确认要删除用户' + rec.data.realName + '?',
								buttons : Ext.MessageBox.YESNO,
								fn : function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request( { url : '../user/user!del.action',
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
				{ text : "登录名", dataIndex : 'loginName', flex : 0.4, sortable : false },
				{ text : "用户名", dataIndex : 'realName', flex : 0.6, sortable : false },
				{ text : "修理厂", dataIndex : 'userGroup', flex : 0.6, sortable : false }],
		// paging bar on the bottom
		bbar : Ext.create('Ext.PagingToolbar', { store : store, displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}', emptyMsg : "无数据" }),
		dockedItems : [ { xtype : 'toolbar',
			items : [ { text : '查询', icon : '../../manager/images/find.png', handler : function() {
				store.load();
			} }, '-', { text : '添加用户', icon : '../../manager/images/find.png', handler : function() {
				addWindow.show();
			} } ] } ] });

	var viewport = Ext.create('Ext.Viewport', { layout : { type : 'border', padding : 5 }, items : [ grid ] })

});
