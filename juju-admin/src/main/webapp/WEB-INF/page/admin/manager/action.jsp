<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

	Ext.define('Action', {
	    extend: 'Ext.data.Model',
	    fields: ['id', 'action', 'actionDiscribtion']
	});
	
	Ext.onReady(function() {
	    Ext.QuickTips.init();
	    // setup the state provider, all state information will be saved to a cookie
	    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
	    
	    var store = Ext.create('Ext.data.ArrayStore', {
	        fields : ['id', 'action', 'actionDiscribtion'],      
	        proxy : {
	            type : 'ajax',
	            api : {
	                read : '${contextPath}/action/findAll.do'
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
                		url : '${contextPath}/action/saveOrUpdate.do',
                		params : {
                			'id' : rec.get('id'),
                			'action' : rec.get('action'),
                			'actionDiscribtion' : rec.get('actionDiscribtion')
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
	        columns: [
	        {
	            text: 'Action',
	            width: 200,
	            sortable: false,
	            dataIndex: 'action',
	            field: {
	                xtype: 'textfield',
	                allowBlank: false
	            }
	        }, {
	            text: '描述',
	            width: 200,
	            sortable: false,
	            dataIndex: 'actionDiscribtion',
	            field: {
	                xtype: 'textfield'
	            }
	        }],
	        dockedItems: [{
	            xtype: 'toolbar',
	            items: [{
	                text: '新增',
	                iconCls: 'icon-add',
	                handler: function(){
	                	var menu = new Action();
	                    store.insert(0, menu);
	                    rowEditing.startEdit(0, 0);
	                }
	            }, '-', {
	                itemId: 'delete',
	                text: '删除',
	                iconCls: 'icon-delete',
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
	                iconCls: 'icon-add',
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
             		url : '${contextPath}/action/deleteAction.do',
             		params : {
             			'actionId' : selection.get('id')		
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
	});

</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>