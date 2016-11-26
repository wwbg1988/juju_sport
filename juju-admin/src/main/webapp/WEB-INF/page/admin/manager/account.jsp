<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<script type="text/javascript" src="${contextPath}/js/dateutil.js"></script>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>
    <script type="text/javascript">
        
        Ext.onReady(function() {
        	
		    Ext.QuickTips.init();
		    // setup the state provider, all state information will be saved to a cookie
		    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
		    
		    var roleStore = Ext.create('Ext.data.Store', {
		        fields : ['key', 'value'],
		       	pageSize : 20,
		        proxy : {
		            type : 'ajax',
		            url : '${contextPath}/manager/constants/getRole.do',
		            reader : {
		                type : 'json',
		                root : 'results',
		                successProperty : 'success',
		                totalProperty : 'total'
		            },
		            timeout : 180000
		        }
		    });
		    
		    var accountStore = Ext.create('Ext.data.ArrayStore', {
		        fields : ['id', 'userName', 'realName', 'roleId', 'roleName'],
		       	pageSize : 20,
		        proxy : {
		            type : 'ajax',
		            api : {
		                read : '${contextPath}/manager/account/find.do'
		            },
		            reader : {
		                type : 'json',
		                root : 'results',
		                successProperty : 'success',
		                totalProperty : 'total'
		            },
		            timeout : 180000,
		    		actionMethods : {
		    			read : 'POST'
		    		}
		        }
		    });
		    
		    //-------- create win
		    var accountForm = Ext.create('Ext.form.Panel', {	
		    	bodyPadding: 5,
		    	defaultType : 'textfield',
		    	border : false,		    	      
		        layout : 'form',       
		    	items : [
		    		{
		    			id : 'id',
		    			hidden : true,
		    			name : 'id',
		    			anchor:'95%'
			    	},
		    		{
		    			fieldLabel : '帐号',
		    			name : 'userName',
		    			anchor:'90%',
		    			allowBlank : false,
		    			labelWidth : 60
			    	},
			    	{
		    			fieldLabel : '密码',
		    			name : 'passwd',
		    			anchor:'90%',
		    			labelWidth : 60
			    	},
			    	{
		    			fieldLabel : '真实姓名',
		    			name : 'realName',
		    			anchor:'90%',
		    			allowBlank : false,
		    			labelWidth : 60
			    	},
				    {	      
				    	name : 'roleId',     
				    	xtype : 'combo', 
		            	store : roleStore,
		            	emptyText : '请选择',
		            	queryMode: 'local',          	
		            	valueField : 'key',
		            	displayField : 'value',		                    	
		                triggerAction: 'all',
				    	fieldLabel : '角色',
				    	labelWidth : 60
			        }
		    	],
		    	buttons : [
		    		{
		    			id : 'conform',
		    			text : '确认',
		    			handler : function() {
		    				var me = this.up('panel');
							if(!me.form.isValid()){
								return;
							}
							
							me.form.submit({
								url : '${contextPath}/manager/account/saveOrUpdate.do',
								method : 'POST',  
								success : function(form, action) {
									Ext.Msg.alert("信息", action.result.message);
									//me.form.reset();
									accountWin.hide();
									accountStore.reload();
										
								},
								failure : function(form, action) {
									Ext.Msg.alert("信息", action.result.message);
								},
								waitMsg : 'plz waiting...'
							});
    					}
		    		}
		    	]
		    });
		    
		     var accountWin = Ext.create('Ext.Window', {
            	id : 'accountWin',
                title: '帐号信息',            
                modal : true,
                closeAction: 'hide',
                width: 260,
                height: 200,
                layout : 'fit',
                items: [accountForm]
            });        
		    
		    
		    var bbar =  Ext.create('Ext.Toolbar', {
		    	width : Ext.get('order-grid').getWidth(),
		    	border : 0,
		    	layout : {
		    		type : 'table',
		    		columns : 4
		    	},   	
			    items: [
				    {
				    	id : 'accountCode',
				    	xtype : 'textfield',
				    	width : 160,
				    	fieldLabel : '帐号',
				    	labelWidth : 50
				    },
				    {	      
				    	id : 'role',     
				    	xtype : 'combo', 
		            	store : roleStore,
		            	emptyText : '请选择',
		            	queryMode: 'local',          	
		            	valueField : 'key',
		            	displayField : 'value',		                    	
		                triggerAction: 'all',
		                width : 160,
				    	fieldLabel : '角色',
				    	labelWidth : 50
			        },
				    {
						text : '查询',
						handler : function() {
							accountStore.proxy.extraParams.accountCode=Ext.getCmp('accountCode').value;
							accountStore.proxy.extraParams.roleId=Ext.getCmp('role').value;
							accountStore.load();
						}
				    },
				    {
						text : '新建',
						handler : function() {
							accountWin.show();
						}
				    }  
			    ]
			}); 
			
			
		    
		    var grid = Ext.create('Ext.grid.GridPanel', {	          
		    	id : 'grid',   
		        store: accountStore,
		        defaultType : 'textfield',
		        tbar : [      
		        	bbar
		    		],
		        columns: [
		        {
                        sortable: false,
                        xtype: 'actioncolumn',
                        width: 60,
                        text: '操作',
                        items: [
	                        {
	                            icon   : '${contextPath}/manager/images/modify.png',  // Use a URL in the icon config
	                            tooltip: '重置密码',
	                            handler: function(grid, rowIndex, colIndex) {
	                               var rec = accountStore.getAt(rowIndex);  
	                               Ext.Msg.confirm('提示', '确认要重置该账号密码？重置后密码将和账号一致！', function(ok) {
	                                	if('yes' != ok){
	                                		return;
	                                	}
	                                	
	                                	Ext.Ajax.request({
	                                		url : '${contextPath}/manager/account/resetDefaultPasswd.do',
	                                		params : {
	                                			'accountId' : rec.data.id
	                                		},
	                                		success : function(r, o){
	                                			var strJson = r.responseText;
	                                			var obj = eval("(" + strJson + ")");
	                                			Ext.Msg.alert("信息", obj.message);
	                                			if(obj.success == true){
	                                				accountStore.reload();
	                                			}
	                                		},
	                                		failure : function(form, action){
	                                			Ext.Msg.alert("信息", "删除失败！")
	                                		}
	                                	});
	                                	
	                               });                               
	                            }
	                        },
	                        {
	                            icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
	                            tooltip: '删除',
	                            handler: function(grid, rowIndex, colIndex) {
	                                var rec = accountStore.getAt(rowIndex);  
	                                var userName = rec.get('userName');       
	                                 var msg = "是否确定需要删除帐号 " + userName;
	                                Ext.Msg.confirm('提示', msg, function(ok) {
	                                	if('yes' != ok){
	                                		return;
	                                	}
	                                		
	                                	Ext.Ajax.request({
	                                		url : '${contextPath}/manager/account/delete.do',
	                                		params : {
	                                			'accountId' : rec.data.id
	                                		},
	                                		success : function(r, o){
	                                			var strJson = r.responseText;
	                                			var obj = eval("(" + strJson + ")");
	                                			Ext.Msg.alert("信息", obj.message);
	                                			if(obj.success == true){
	                                				accountStore.reload();
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
                 },
				{
				    text: '帐号',
				    width: 150,
				    sortable: true,
				    dataIndex: 'userName'
				}, {
		            text: '真实姓名',
		            width: 100,
		            sortable: true,
		            dataIndex: 'realName'
		        }, {
		            text: '角色',
		            width: 100,
		            sortable: true,
		            dataIndex: 'roleName'
		        }
		        ],
		    	bbar : Ext.create('Ext.PagingToolbar', {
					store : accountStore,
					displayInfo : true,
					displayMsg : '显示条数 {0} - {1} of {2}',
					emptyMsg : "无数据"
				})
		    });
		    
		    Ext.create('Ext.container.Viewport', {
				layout : 'fit',
				renderTo: 'order-grid',
				items : [grid]		 
			});
        	
        	roleStore.load();
        });

    </script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>