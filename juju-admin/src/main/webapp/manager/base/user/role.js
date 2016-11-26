Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.define('storeMoedl', { extend : 'Ext.data.Model', fields : [ 'roleName', 'roleDescript', 'id' ],
		idProperty : 'id' });

	var roleId = '';

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		model : 'storeMoedl',
		autoLoad : true,
		proxy : {
			type : 'ajax',
			url : '../user/role!list.action',
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
		url : '../user/role!add.action',
		frame : true,
		bodyPadding : '5 5 0',
		fieldDefaults : { msgTarget : 'side', labelWidth : 75 },
		defaultType : 'textfield',
		items : [ { fieldLabel : '用户组名', name : 'roleName', allowBlank : false },
				{ fieldLabel : '用户组描述', name : 'roleDescript', allowBlank : false } ],

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

	var addWindow = Ext.create('widget.window', { height : 150, width : 400, title : '添加用户组', closable : false,
		modal : true, layout : 'fit', resizable : false, items : [ addForm ] });

	// 增加分配权限功能开始

		var tabs = Ext.widget('tabpanel', {
			enableTabScroll : true,
			header : false,
			trackMouseOver : true,
			loadMask : true,
			items : [],
			dockedItems : [ {
				xtype : 'toolbar',
				items : [ {
					text : '保存',
					icon : '../../manager/images/find.png',
					handler : function() {
						var grids = tabs.items.items;
						var arr = new Array();
						var index = 0;
						for ( var int = 0; int < grids.length; int++) {
							var selects = tabs.items.items[int].selModel.getSelection();
							for ( var j = 0; j < selects.length; j++) {
								arr[index++] = selects[j].data["id"];
							}
						}
						
						if(arr.length==0){
							Ext.Msg.alert("提示", "请至少选择一项功能！");
							return;
						}
						
						Ext.Ajax.request( { url : '../user/role!saveMenuByRoleId.action', params : { "id" : roleId, "ids" : arr },
							method : 'POST', success : function(r, o) {
								var strJson = r.responseText;
								var obj = eval("(" + strJson + ")");
								if (obj.success == true) {
									addMenu.hide();
									Ext.Msg.alert("信息", obj.msg);
								} else {
									Ext.Msg.alert("信息", obj.msg);
								}
							}, failure : function(form, action) {
								Ext.Msg.alert("信息", "系统错误！");
							} });
					} } ] } ] });

		Ext.define('storeTab', { extend : 'Ext.data.Model', fields : [ 'funcName', 'isSelect', 'id' ],
			idProperty : 'id' });

		var addTab = function(group, id) {
			roleId = id;
			var sm = Ext.create('Ext.selection.CheckboxModel');

			var storeTab = Ext.create('Ext.data.Store', {
				model : 'storeTab',
				autoLoad : true,
				proxy : {
					type : 'ajax',
					url : '../user/role!getMenuByGroupAndRoleId.action?id=' + id + '&groupId=' + group.id,
					reader : { type : 'json', totalProperty : 'totalCount', root : 'results',
						successProperty : 'success', idProperty : 'id' } } });

			var grid = Ext.create('Ext.grid.Panel', { region : 'center',
				title : group.groupName,
				selModel : sm,
				store : storeTab,
				loadMask : true,
				// grid columns
				columns : [ { text : "功能名称", dataIndex : 'funcName', flex : 1, sortable : false,
					renderer : function(r, d, s) {
						if (s.data.isSelect == 'true') {
							sm.select(s.index, true, false);
						}
						return r;
					} } ] });

			tabs.add(grid).show();
			tabs.setActiveTab(0);
		};

		function delTab() {
			tabs.removeAll();
		}
		var loadAll = function(id) {
			Ext.Ajax.request( { url : '../user/role!getMenuGroupById.action', method : 'POST',
				success : function(r, o) {
					var strJson = r.responseText;
					var obj = eval("(" + strJson + ")");
					if (obj.success == true) {
						for ( var i = 0; i < obj.results.length; i++) {
							addTab(obj.results[i], id);
						}
					} else {
						Ext.Msg.alert("信息", obj.msg);
					}
				}, failure : function(form, action) {
					Ext.Msg.alert("信息", "系统错误！");
				} });
		}

		// 增加分配权限功能结束

		var addMenu = Ext.create('widget.window', { height : 400, width : 600, title : '分配功能', closable : true,
			closeAction : 'hide', modal : true, layout : 'fit', resizable : false, items : [ tabs ] });

		var grid = Ext.create('Ext.grid.Panel', {
			region : 'center',
			title : "用户组管理",
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
									title : '删除用户组',
									msg : '是否确认要删除用户组' + rec.data.roleName + '?',
									buttons : Ext.MessageBox.YESNO,
									fn : function(btn) {
										if (btn == 'yes') {
											Ext.Ajax.request( { url : '../user/role!del.action',
												params : { "id" : rec.data.id }, method : 'POST',
												success : function(r, o) {
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
					{
						menuDisabled : true,
						sortable : false,
						xtype : 'actioncolumn',
						width : 25,
						items : [ { icon : '../../manager/images/modify.png', tooltip : '分配用户组功能',
							handler : function(grid, rowIndex, colIndex) {
								var rec = store.getAt(rowIndex);
								delTab();
								addMenu.show();
								loadAll(rec.data.id);
							} } ], hideTitle : true },
					{ text : "用户组名", dataIndex : 'roleName', flex : 0.4, sortable : false },
					{ text : "用户组描述", dataIndex : 'roleDescript', flex : 0.6, sortable : false } ],
			// paging bar on the bottom
			bbar : Ext.create('Ext.PagingToolbar', { store : store, displayInfo : true,
				displayMsg : '显示条数 {0} - {1} of {2}', emptyMsg : "无数据" }),
			dockedItems : [ { xtype : 'toolbar',
				items : [ { text : '查询', icon : '../../manager/images/find.png', handler : function() {
					store.load();
				} }, '-', { text : '添加用户组', icon : '../../manager/images/find.png', handler : function() {
					addWindow.show();
				} } ] } ] });

		var viewport = Ext.create('Ext.Viewport', { layout : { type : 'border', padding : 5 }, items : [ grid ] })

	});
