<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<script type="text/javascript" src="${contextPath}/js/dateutil.js"></script>
<script type="text/javascript" src="${contextPath}/app/appoint-constants.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">	
	Ext.onReady(function() {
		 	Ext.QuickTips.init();
		    // setup the state provider, all state information will be saved to a cookie
		    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
		    
		    var appointStore = Ext.create('Ext.data.ArrayStore', {
		        fields : ['id', 'userId', 'userName', 'sellerId', 'sellerName', 'mobileNo', 'telephone',
		                  'appointTime', 'appointStatus', 'appointStatusName', 'memo', 'createTime' ],
		       	pageSize : 20,
		        proxy : {
		            type : 'ajax',
		            api : {
		                read : '${contextPath}/appoint/appoint-query/find.do'
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
		    
		    var bbar =  Ext.create('Ext.Toolbar', {
		    	width : Ext.get('order-grid').getWidth(),
		    	border : 0,
		    	layout : {
		    		type : 'table',
		    		columns : 3
		    	},   	
			    items: [
			        {
						xtype : 'datefield',
						fieldLabel : '预约日期',
						format : 'Y-m-d',
						labelWidth : 60,
						width : 160,
						id : 'begin_date',
						value : new Date()
				    },
				    {
						xtype : 'datefield',
						fieldLabel : '至',
						format : 'Y-m-d',
						labelWidth : 30,
						width : 130,
						id : 'end_date',
						value : new Date()
				    },
				  	  {	      			
		    			id : 'appointStatus',	    	 
				    	xtype : 'combo', 
				    	name : 'appointStatus',
		            	store : App.store.appoint.appointStatusStore,
		            	emptyText : '请选择',
		            	queryMode: 'local',           	
		            	valueField : 'key',
		            	displayField : 'value',              	
		                triggerAction: 'all',		            
				    	fieldLabel : '预约状态',
				    	labelWidth : 60,
				    	width : 160
				     },
				    {
				    	id : 'sellerName',
				    	name : 'sellerName',
				    	xtype : 'textfield',
				    	width : 160,
				    	fieldLabel : '商家名称',
				    	labelWidth : 50
				    },
				    {
				    	id : 'userName',
				    	name : 'userName',
				    	xtype : 'textfield',
				    	width : 160,
				    	fieldLabel : '用户姓名',
				    	labelWidth : 50
				    },
				    {
						text : '查询',
						handler : function() {
							appointStore.proxy.extraParams.beginDate=Ext.getCmp('begin_date').value.format("yyyy-MM-dd");	
							appointStore.proxy.extraParams.endDate=Ext.getCmp('end_date').value.format("yyyy-MM-dd");	
							appointStore.proxy.extraParams.appointStatus=Ext.getCmp('appointStatus').value;	
							appointStore.proxy.extraParams.sellerName=Ext.getCmp('sellerName').value;	
							appointStore.proxy.extraParams.userName=Ext.getCmp('userName').value;				
							appointStore.load();
						}
				    } 
			    ]
			}); 
			
			
		    
		    var grid = Ext.create('Ext.grid.GridPanel', {	          
		    	id : 'grid',   
		        store: appointStore,
		        defaultType : 'textfield',
		        tbar : [      
		        	bbar
		    		],
		        columns: [
				{
				    text: '预约人',
				    width: 100,
				    sortable: false,
				    dataIndex: 'userName'
				}, {
		            text: '商家',
		            width: 150,
		            sortable: true,
		            dataIndex: 'sellerName'
		        }, {
		            text: '手机号',
		            width: 100,
		            sortable: false,
		            dataIndex: 'mobileNo'
		        }, {
		            text: '电话号码',
		            width: 100,
		            sortable: false,
		            dataIndex: 'telephone'
		        }, {
		            text: '预约时间',
		            width: 100,
		            sortable: false,
		            dataIndex: 'appointTime',
		            renderer : function(value) {
		            	if(value != null){
		            		return Ext.util.Format.date(new Date(value), 'Y-m-d');
		            	}
		            }
		        }, {
		            text: '预约状态',
		            width: 100,
		            sortable: false,
		            dataIndex: 'appointStatusName'
		        }, {
		            text: '备注',
		            width: 100,
		            sortable: false,
		            dataIndex: 'memo'
		        }, {
		            text: '创建日期',
		            width: 100,
		            sortable: false,
		            dataIndex: 'createTime',
		            renderer : function(value) {
		            	if(value != null){
		            		return Ext.util.Format.date(new Date(value), 'Y-m-d');
		            	}
		            }
		        }
		        ],
		    	bbar : Ext.create('Ext.PagingToolbar', {
					store : appointStore,
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
			
			App.store.appoint.appointStatusStore.load();
		});
	</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html