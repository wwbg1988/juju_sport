<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

	Ext.define('Menu', {
	    extend: 'Ext.data.Model',
	    fields: ['id', 'menuGroupId', 'menuFunctionName', 'menuFunctionAction', 'menuFunctionDescript']
	});
	
	Ext.onReady(function() {
	    Ext.QuickTips.init();
	    // setup the state provider, all state information will be saved to a cookie
	    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
	    
	    var groupStore = Ext.create('Ext.data.Store', {
	    	//outoLoad : true,
	        fields : ['id', 'menuGroupName'],      
	        proxy : {
	            type : 'ajax',
	            api : {
	                read : '${contextPath}/menu-group/findAll.do'
	            },
	            reader : {
	                type : 'json',
	                root : 'results',
	                successProperty : 'success'
	            },
	            timeout : 180000
	        }
	    });
	    
	    var store = Ext.create('Ext.data.ArrayStore', {
	        fields : ['id', 'menuGroupName', 'menuFunctionName', 'menuFunctionAction', 'menuFunctionDescript'],      
	        proxy : {
	            type : 'ajax',
	            api : {
	                read : '${contextPath}/menu/findAll.do'
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
	    
	    var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
	    	saveBtnText: '保存',
            cancelBtnText: "取消", 
	    	clicksToEdit: 2,
	    	errorSummary : false,
	        listeners: {
	            cancelEdit: function(rowEditing, context) {
	                // Canceling editing of a locally added, unsaved record: remove it
	                if (context.record.phantom) {
	                    store.remove(context.record);
	                }
	            },
	            edit:function(rowEditing, context) {
					var rec = context.record;
					
					Ext.Ajax.request({
                		url : '${contextPath}/menu/saveOrUpdate.do',
                		params : {
                			'id' : rec.get('id'),
                			'menuGroupId' : rec.get('menuGroupId'),
                			'menuGroupName' : rec.get('menuGroupName'),
                			'menuFunctionName' : rec.get('menuFunctionName'),
                			'menuFunctionAction' : rec.get('menuFunctionAction'),
                			'menuFunctionDescript' : rec.get('menuFunctionDescript')             			
                		},
                		success : function(r, o){
                			var strJson = r.responseText;
                			var obj = eval("(" + strJson + ")");
                			Ext.Msg.alert("信息", obj.message);      
                			store.reload();
                		},
                		failure : function(form, action){
                			Ext.Msg.alert("信息", "保存失败！");
                		}
                	});
	            }
	        }
	    });
	    
	    var grid = Ext.create('Ext.grid.Panel', {	       
	        plugins: [rowEditing],	      
	        frame: true,       
	        store: store,
	        selType: 'rowmodel',
	        defaultType : 'textfield',
	        columns: [{
	            text: '功能名称',
	            width: 100,
	            sortable: true,
	            dataIndex: 'menuFunctionName',
	            field: {
	                xtype: 'textfield',
	                allowBlank: false,
	                maxLength : 25
	            }
	        }, {
	            header: '功能组',
	            width: 100,	            
	            dataIndex: 'menuGroupName',
	            editor : new Ext.form.field.ComboBox({
	            	store : groupStore,
	            	emptyText : '请选择',
	            	mode : 'local',	            	
	            	valueField : 'menuGroupName',
	            	displayField : 'menuGroupName',
	            	editable : false,
	            	allowBlank: false,	            	
                    triggerAction: 'all',
                    listeners:{                    
                        'select': function(combo, records, eOpts){                        	
                        	 var selection = grid.getView().getSelectionModel().getSelection()[0];
                        	 selection.set('menuGroupName', combo.value);
                        }
                   }
	            })
	        }, {
	            text: 'Action',
	            width: 300,
	            sortable: false,
	            dataIndex: 'menuFunctionAction',
	            field: {
	                xtype: 'textfield',
	                allowBlank: false,
	                maxLength : 25
	            }
	        }, {
	            text: '描述',
	            width: 300,
	            sortable: false,
	            dataIndex: 'menuFunctionDescript',
	            field: {
	                xtype: 'textfield',
	                maxLength : 50
	            }
	        }],
	        dockedItems: [{
	            xtype: 'toolbar',
	            items: [{
	                text: '新增',
	                //iconCls: 'icon-add',
	                handler: function(){
	                	var menu = new Menu();
	                	var groupName = groupStore.getAt(0).get('menuGroupName');
	                	menu.set('menuGroupName', groupName);
	                    store.insert(0, menu);
	                    rowEditing.startEdit(0, 0);
	                }
	            }, '-', {
	                itemId: 'delete',
	                text: '删除',
	                //iconCls: 'icon-delete',
	                disabled: true,
	                handler: function(){
	                    var selection = grid.getView().getSelectionModel().getSelection()[0];
	                    Ext.Msg.confirm('提示', "确认要删除菜单项？", function(ok) {
	                    	if ('yes' != ok) {
	                    		return;
	                    	}
	                    	deleteMenu(selection);
	                    });
	                }
	            },{
	                text: '刷新',
	               // iconCls: 'icon-add',
	                handler: function(){
	                	store.reload();
	                }
	            }
	            
	            ]
	        }]
	    });
	    grid.getSelectionModel().on('selectionchange', function(selModel, selections){
	        grid.down('#delete').setDisabled(selections.length === 0);
	    });
	    
	    var deleteMenu = function(selection){
	    	 if (selection) {
            	 
             	Ext.Ajax.request({
             		url : '${contextPath}/menu/deleteMenu.do',
             		params : {
             			'menuId' : selection.get('id')		
             		},
             		success : function(r, o){
             			var strJson = r.responseText;
             			var obj = eval("(" + strJson + ")");
             			Ext.Msg.alert("信息", obj.message);      
             			store.remove(selection);
             		},
             		failure : function(form, action){
             			Ext.Msg.alert("信息", "保存失败！");
             		}
             	});
               
             }
	    }
	    

        Ext.create('Ext.container.Viewport', {
    		layout : 'fit',
    		renderTo: 'order-grid',
    		items : [grid]		 
    	});
	    
        //init
        store.load();
        groupStore.load();
	});

</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>