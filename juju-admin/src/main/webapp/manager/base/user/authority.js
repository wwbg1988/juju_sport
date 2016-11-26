Ext.onReady(function() {

	Ext.QuickTips.init();
	
	var userId = '';

	Ext.define('storeMoedl',
			{ extend : 'Ext.data.Model', fields : [ 'loginName', 'realName', 'id' ], idProperty : 'id' });

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		model : 'storeMoedl',
		autoLoad : true,
		proxy : {
			type : 'ajax',
			url : '../user/authority!list.action',
			reader : { type : 'json', totalProperty : 'totalCount', root : 'results', successProperty : 'success',
				idProperty : 'id' } } });

	var success = function(form, window, store) {
		form.getForm().reset();
		window.hide();
		store.load();
	};

	Ext.define('storeTab', { extend : 'Ext.data.Model', fields : [ 'roleName', 'isUsed', 'id' ], idProperty : 'id' });

	var sm = Ext.create('Ext.selection.CheckboxModel');
	// sm.setSelectionMode('SINGLE');

	var storeTab = Ext.create('Ext.data.Store', {
		model : 'storeTab',
		proxy : {
			type : 'ajax',
			url : '../user/authority!getAllUserRole.action',
			reader : { type : 'json', totalProperty : 'totalCount', root : 'results', successProperty : 'success',
				idProperty : 'id' } } });

	var gridAdd = Ext.create('Ext.grid.Panel', {
		region : 'center',
		title : "用户组",
		selModel : sm,
		store : storeTab,
		loadMask : true,
		// grid columns
		columns : [ { text : "用户组名", dataIndex : 'roleName', flex : 1, sortable : false, renderer : function(r, d, s) {
			if (s.data.isUsed == 'true') {
				sm.select(s.index, true, false);
			}
			return r;
		} } ],
		dockedItems : [ {
			xtype : 'toolbar',
			items : [ {
				text : '保存',
				icon : '../../manager/images/find.png',
				handler : function() {
					var arr = new Array();
					var index = 0;
					var selects = gridAdd.selModel.getSelection();
					if(selects.length==0){
						Ext.Msg.alert("提示", "请选择一项用户组！");
						return;
					}
					for ( var j = 0; j < selects.length; j++) {
						arr[index++] = selects[j].data["id"];
					}
					Ext.Ajax.request( { url : '../user/authority!saveById.action',
						params : { "id" : userId, "ids" : arr }, method : 'POST', success : function(r, o) {
							var strJson = r.responseText;
							var obj = eval("(" + strJson + ")");
							if (obj.success == true) {
								addWindow.hide();
								Ext.Msg.alert("信息", obj.msg);
							} else {
								Ext.Msg.alert("信息", obj.msg);
							}
						}, failure : function(form, action) {
							Ext.Msg.alert("信息", "系统错误！");
						} });
				} } ] } ] });

	var addWindow = Ext.create('widget.window', { height : 400, width : 500, title : '添加用户组', closable : true,
		modal : true, closeAction : 'hide', layout : 'fit', resizable : false, items : [ gridAdd ] });

	var grid = Ext.create('Ext.grid.Panel', {
		region : 'center',
		title : "人员权限管理",
		store : store,
		loadMask : true,
		columns : [
				{
					menuDisabled : true,
					sortable : false,
					xtype : 'actioncolumn',
					width : 25,
					items : [ { icon : '../../manager/images/modify.png', tooltip : '分配用户权限',
						handler : function(grid, rowIndex, colIndex) {

							var rec = store.getAt(rowIndex);
							userId = rec.data.id;
							storeTab.load( { params : { id : userId} });
							addWindow.show();
						} } ], hideTitle : true },
				{ text : "登录名", dataIndex : 'loginName', flex : 0.4, sortable : false },
				{ text : "用户名", dataIndex : 'realName', flex : 0.6, sortable : false } ],
		// paging bar on the bottom
		bbar : Ext.create('Ext.PagingToolbar', { store : store, displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}', emptyMsg : "无数据" }),
		dockedItems : [ { xtype : 'toolbar',
			items : [ { text : '查询', icon : '../../manager/images/find.png', handler : function() {
				store.load();
			} } ] } ] });

	var viewport = Ext.create('Ext.Viewport', { layout : { type : 'border', padding : 5 }, items : [ grid ] })

});
