<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>
    <script type="text/javascript">
        
        Ext.onReady(function() {
            Ext.QuickTips.init();

            // setup the state provider, all state information will be saved to a cookie
            Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
            
            //角色功能配置
            var actionSm = new Ext.selection.CheckboxModel({checkOnly : true});
            
            var roleActionStore = Ext.create('Ext.data.ArrayStore', {
                fields : ['roleId', 'actionId', 'action', 'actionDiscribtion', 'selected'],
                proxy : {
                    type : 'ajax',
                    api : {
                        read : '${contextPath}/role/getRoleAction.do'
                    },
                    reader : {
                        type : 'json',
                        root : 'results',
                        successProperty : 'success',
                        totalProperty : 'total'
                    },
                    timeout : 180000
                }
            });
            
            var roleActionGrid = Ext.create('Ext.grid.GridPanel', {
                store: roleActionStore,
                multiSelect: true,
                autoHeight : true,     
                selModel : actionSm,
                tbar : [
                        {
                        	text : '保存',
                            handler : function() {
                            	Ext.Msg.confirm('提示', "确认要保存权限？", function(ok) {
                            		var m = roleActionGrid.getSelectionModel().getSelection();           						
            						var jsonArray = [];
            						Ext.each(m, function(item) {
            							jsonArray.push(item.data['actionId']);
            						});
                            		
                            		Ext.Ajax.request({
                                		url : '${contextPath}/role/updateRoleAction.do',
                                		params : {
                                			'roleId' : roleId,
                                			'actionIds' : Ext.encode(jsonArray)
                                		},
                                		success : function(r, o){
                                			var strJson = r.responseText;
                                			var obj = eval("(" + strJson + ")");
                                			Ext.Msg.alert("信息", obj.message);                                			
                                		},
                                		failure : function(form, action){
                                			Ext.Msg.alert("信息", "修改失败！")
                                		}
                                	});
                            	});
                            }
                        }
                    ],
                columns: [ 
					{
						text     : '功能',
						width    : 200,
	                    sortable : false,
	                    dataIndex: 'action'
					},
                    {
                        text     : '功能描述',
                        width    : 150,
                        sortable : false,
                        dataIndex: 'actionDiscribtion'
                    }
                ],                
                viewConfig: {
                    stripeRows: true,
                    enableTextSelection: true
                }
            });  
            
           var roleActionWin = Ext.create('Ext.Window', {
            	id : 'roleActionWin',
                title: '角色功能权限配置',
                closeAction: 'hide',
                width: 400,
                height: 430,
                layout : 'fit',
                items: [roleActionGrid]
            });                      
           
            //角色action配置
            var roleId = "";
            
            var roleMenuStore = Ext.create('Ext.data.ArrayStore', {
                fields : ['menuId', 'groupName', 'menuName', 'menuAction', 'roleId', 'selected'],
                groupField : 'groupName',
                proxy : {
                    type : 'ajax',
                    api : {
                        read : '${contextPath}/role/getRoleMenu.do'
                    },
                    reader : {
                        type : 'json',
                        root : 'results',
                        successProperty : 'success',
                        totalProperty : 'total'
                    },
                    timeout : 180000
                }
            });
           
           var sm = new Ext.selection.CheckboxModel({checkOnly : true});
            
            var roleMenuGrid = Ext.create('Ext.grid.GridPanel', {
                store: roleMenuStore,
                features : [{ftype : 'grouping'}],
                multiSelect: true,
                autoHeight : true,     
                selModel : sm,
                tbar : [
                        {
                        	text : '保存',
                            handler : function() {
                            	Ext.Msg.confirm('提示', "确认要保存权限？", function(ok) {
                            		var m = roleMenuGrid.getSelectionModel().getSelection();           						
            						var jsonArray = [];
            						Ext.each(m, function(item) {
            							jsonArray.push(item.data['menuId']);
            						});
                            		
                            		Ext.Ajax.request({
                                		url : '${contextPath}/role/updateRoleMenu.do',
                                		params : {
                                			'roleId' : roleId,
                                			'menuIds' : Ext.encode(jsonArray)
                                		},
                                		success : function(r, o){
                                			var strJson = r.responseText;
                                			var obj = eval("(" + strJson + ")");
                                			Ext.Msg.alert("信息", obj.message);                                			
                                		},
                                		failure : function(form, action){
                                			Ext.Msg.alert("信息", "修改失败！")
                                		}
                                	});
                            	});
                            }
                        }
                    ],
                columns: [ 
					{
						text     : '功能组',
						width    : 100,
	                    sortable : false,
	                    dataIndex: 'groupName'
					},
                    {
                        text     : '功能',
                        width    : 100,
                        sortable : false,
                        dataIndex: 'menuName'
                    },
                    {
                        text     : 'URL',
                        width    : 200,
                        sortable : false,
                        dataIndex: 'menuAction'
                    }
                ],                
                viewConfig: {
                    stripeRows: true,
                    enableTextSelection: true
                }
            });  
           
            var roleMenuWin = Ext.create('Ext.Window', {
            	id : 'roleMenuWin',
                title: '角色菜单权限配置',
                closeAction: 'hide',
                width: 460,
                height: 500,
                layout : 'fit',
                items: [roleMenuGrid]
            });                      

           //角色新增
		   var pwModifyForm = Ext.create('Ext.form.FormPanel', {
							id : 'modifyForm',
							frame : true,
							defaultType : 'textfield',
							items : [					
							         {
										fieldLabel : '角色名称',
										id : 'roleNameText',
										name : 'roleName',
										allowBlank : false
									}, {
										fieldLabel : '角色描述',
										id : 'roleDescriptionText',
										name : 'roleDescription',
										allowBlank : false
									}],
							buttons : [{
								text : '保存',
								handler : function() {
									var me = this.up('panel');
									if(!me.form.isValid()){
										return;
									}
									
									me.form.submit({
										url : '${contextPath}/role/create.do',
										method : 'POST',
										success : function(form, action) {
											Ext.Msg.alert("信息", action.result.message);
											me.form.reset();
											modifyWin.hide();
											grid_store.reload();
										},
										failure : function(form, action) {
											Ext.Msg.alert("信息", "新增失败!");
										},
										waitMsg : 'plz waiting...'
									});
								}
							}, {
								text : '重置',
								handler : function() {
									var me = this.up('panel');
									me.form.reset();
									
								}
							}]
						});
            
            var modifyWin = Ext.create('Ext.Window', {
            	id : 'modifyWin',
                title: '角色新增',
                closeAction: 'hide',
                width: 350,
                height: 120,
                items: [pwModifyForm]
            });                  

            //create ajax store
            var grid_store = Ext.create('Ext.data.Store', {
                fields : ['id', 'roleName', 'roleDescription'],
                proxy : {
                    type : 'ajax',
                    api : {
                        read : '${contextPath}/role/findAll.do'
                    },
                    reader : {
                        type : 'json',
                        root : 'results',
                        successProperty : 'success',
                        totalProperty : 'totalCount'
                    },
                    timeout : 180000
                }
            });

            // create the Grid
            var grid = Ext.create('Ext.grid.Panel', {
                store: grid_store,
                stateful: true,
                multiSelect: true,
                autoHeight : true,
                tbar : [
                    {
                        text : '新增',
                        handler : function() {
							modifyWin.show();
                        }
                    },
                     {
                        text : '刷新',
                        handler : function() {
							grid_store.reload();
                        }
                    }
                ],
                columns: [
                    {
                        text     : '角色',
                        width    : 100,
                        sortable : false,
                        dataIndex: 'roleName'
                    },
                    {
                        text     : '角色描述',
                        width    : 200,
                        sortable : false,
                        dataIndex: 'roleDescription'
                    },
                    {
                        sortable: false,
                        xtype: 'actioncolumn',
                        width: 80,
                        items: [
                        {
                            icon   : '${contextPath}/manager/images/detail.png',  // Use a URL in the icon config
                            tooltip: '菜单权限修改',
                            handler: function(grid, rowIndex, colIndex) {
                                var rec = grid_store.getAt(rowIndex);
                                roleMenuWin.show();
                                roleId = rec.get('id');
                                roleMenuStore.load({
                                	params : {
                                		roleId : rec.get('id')
                                	},
                                	callback : function(records, options, success){
                                		for(var i = 0; i < records.length; i++){
                                			var rec = records[i];
                                			if(rec.get('selected')){
                                				sm.select(i, true);                               			
                                			}                               	
                                		}
                                	},
                                	scope : roleMenuStore,
                                	add : false
                                });
                                                               
                            }
                        },
                        {
                            icon   : '${contextPath}/manager/images/modify.png',  // Use a URL in the icon config
                            tooltip: '功能权限修改',
                            handler: function(grid, rowIndex, colIndex) {
                                var rec = grid_store.getAt(rowIndex);
                                roleId = rec.get('id');
                                roleActionWin.show();  
                                
                                 roleActionStore.load({
                                	params : {
                                		roleId : rec.get('id')
                                	},
                                	callback : function(records, options, success){
                                		for(var i = 0; i < records.length; i++){
                                			var rec = records[i];
                                			if(rec.get('selected')){
                                				actionSm.select(i, true);                               			
                                			}                               	
                                		}
                                	},
                                	scope : roleActionStore,
                                	add : false
                                });                             
                            }
                        },
                        {
                            icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
                            tooltip: '删除',
                            handler: function(grid, rowIndex, colIndex) {
                                var rec = grid_store.getAt(rowIndex);  
                                var roleName = rec.get('roleName');                      
                                var msg = "是否确定需要删除角色 " + roleName;
                              	Ext.Msg.confirm('提示', msg, function(ok) {
                                	if('yes' != ok){
                                		return;
                                	}
                                		
                                	Ext.Ajax.request({
                                		url : '${contextPath}/role/delete.do',
                                		params : {
                                			'roleId' : rec.data.id
                                		},
                                		success : function(r, o){
                                			var strJson = r.responseText;
                                			var obj = eval("(" + strJson + ")");
                                			Ext.Msg.alert("信息", obj.message);
                                			if(obj.success == true){
                                				grid_store.reload();
                                			}
                                		},
                                		failure : function(form, action){
                                			Ext.Msg.alert("信息", "删除失败！")
                                		}
                                	});
                                });
                            }
                        }
                        ]
                    }
                ],
                
                viewConfig: {
                    stripeRows: true,
                    enableTextSelection: true
                }
            });

            Ext.create('Ext.container.Viewport', {
        		layout : 'fit',
        		renderTo: 'order-grid',
        		items : [grid]		 
        	});
            
            grid_store.reload();
        });

    </script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>
