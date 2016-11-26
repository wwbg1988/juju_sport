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
		    
		    var medalTypeStore = Ext.create('Ext.data.Store', {
		        fields : ['key', 'value'],
		       	pageSize : 20,
		        proxy : {
		            type : 'ajax',
		            url : '${contextPath}/common/constants/medalType.do',
		            reader : {
		                type : 'json',
		                root : 'results',
		                successProperty : 'success',
		                totalProperty : 'total'
		            },
		            timeout : 180000
		        }
		    });
		    
		    var medalStore = Ext.create('Ext.data.ArrayStore', {
		        fields : ['id','type', 'typeName', 'level','name','logo','createTime','lastUpdateTime','stat'],
		       	pageSize : 20,
		        proxy : {
		            type : 'ajax',
		            api : {
		                read : '${contextPath}/user/medal/findAll.do'
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
		    var medalForm = Ext.create('Ext.form.Panel', {	
		    	bodyPadding: 5,
		    	defaultType : 'textfield',
		    	border : false,		    	      
		        layout : 'form',       
		    	items : [
					{
		               	xtype: 'filefield',	            
		 	            emptyText: 'Select an image',
		 	            fieldLabel: '功勋图标',
		 	            name: 'picImage',
		 	            buttonText: '请选择',
		 	            anchor:'90%',
		 	            labelWidth : 60,
		 	            buttonConfig: {
		 	                iconCls: 'upload-icon'
		 	            }
		 	        },
		 	       	{	      				    	
				    	xtype : 'combo', 
				    	name: 'type',
		            	store : medalTypeStore,
		            	emptyText : '请选择',
		            	queryMode: 'local',          	
		            	valueField : 'key',
		            	displayField : 'value',		                    	
		                triggerAction: 'all',
				    	fieldLabel : '类型',				    	
				    	labelWidth : 60
			        },
			    	{
		    			fieldLabel : '等级',
		    			name : 'level',
		    			anchor:'90%',
		    			labelWidth : 60
			    	},
			    	{
		    			fieldLabel : '名称',
		    			name : 'name',
		    			anchor:'90%',
		    			allowBlank : false,
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
								url : '${contextPath}/user/medal/create.do',
								method : 'POST',  
								success : function(form, action) {
									Ext.Msg.alert("信息", "保存成功!");
									//me.form.reset();
									medalWin.hide();
									medalStore.reload();
								},
								failure : function(form, action) {
									Ext.Msg.alert("信息", "保存失败!");
								},
								waitMsg : 'plz waiting...'
							});
    					}
		    		}
		    	]
		    });
		    
		     var medalWin = Ext.create('Ext.Window', {
            	id : 'accountWin',
                title: '帐号信息',            
                modal : true,
                closeAction: 'hide',
                width: 300,
                height: 250,
                layout : 'fit',
                items: [medalForm]
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
						text : '刷新',
						handler : function() {
							medalStore.load();
						}
				    },
				    {
						text : '新建',
						handler : function() {
							medalWin.show();
						}
				    }  
			    ]
			}); 
			
			
		    
		    var grid = Ext.create('Ext.grid.GridPanel', {	          
		    	id : 'grid',   
		        store: medalStore,
		        defaultType : 'textfield',
		        tbar : [      
		        	bbar
		    		],
		        columns: [
		        {
		        	    text: '操作',
                        sortable: false,
                        xtype: 'actioncolumn',
                        width: 60,
                        items: [
	                        {
	                            icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
	                            tooltip: '删除',
	                            handler: function(grid, rowIndex, colIndex) {
	                                var rec = medalStore.getAt(rowIndex);  
	                                var id = rec.get('id');       
	                                var msg = "是否确定需要删除勋章 ";
	                                Ext.Msg.confirm('提示', msg, function(ok) {
	                                	if('yes' != ok){
	                                		return;
	                                	}
	                                		
	                                	Ext.Ajax.request({
	                                		url : '${contextPath}/user/medal/delete.do',
	                                		params : {
	                                			'medalId' : id
	                                		},
	                                		success : function(r, o){
	                                			var strJson = r.responseText;
	                                			var obj = eval("(" + strJson + ")");
	                                			Ext.Msg.alert("信息", obj.message);
	                                			medalStore.remove(rec);
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
     		    	text: '勋章图标',
     			    width: 65,
     			    sortable: false,
     			    dataIndex: 'logo',
     			    align: 'center',
     			    renderer : function(v) {
     			    	//return "<img height=60 with=60 src='${contextPath}" + v + "'/>";
     			    	return "<img height=50 with=50 src='/" + v + "'/>";
     			    }
     		    },
				{
		            text: '类型',
		            width: 100,
		            sortable: true,
		            dataIndex: 'typeName'
		        }, {
		            text: '等级',
		            width: 100,
		            sortable: true,
		            dataIndex: 'level'
		        }, {
		            text: '名称',
		            width: 100,
		            sortable: true,
		            dataIndex: 'name'
		        }
		        ],
		    	bbar : Ext.create('Ext.PagingToolbar', {
					store : medalStore,
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
        	
		    medalTypeStore.load();
		    medalStore.load();
        });

    </script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>