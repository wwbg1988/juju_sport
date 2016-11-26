<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<script type="text/javascript" src="${contextPath}/js/dateutil.js"></script>
<script type="text/javascript" src="${contextPath}/app/order-constants.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>聚运动后台管理---订单管理</title>
<script type="text/javascript">
Ext.onReady(function() {
    Ext.QuickTips.init();
    // setup the state provider, all state information will be saved to a cookie
    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
    
    App.store.order.orderTypeStore = Ext.create('Ext.data.Store', {
        fields : ['key', 'value'],
        pageSize : 20,
        proxy : {
            type : 'ajax',
            url : '${contextPath}/order/getOrderType.do',
            reader : {
                type : 'json',
                root : 'results',
                successProperty : 'success',
                totalProperty : 'total'
            },
            timeout : 180000
        }
    });
    
    App.store.order.orderPaymentStatusStore = Ext.create('Ext.data.Store', {
        fields : ['key', 'value'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            url : '${contextPath}/order/getPaymentType.do',
            reader : {
                type : 'json',
                root : 'results',
                successProperty : 'success',
                totalProperty : 'total'
            },
            timeout : 180000
        }
    });
    
    App.store.order.orderStatusStore = Ext.create('Ext.data.Store', {
    	fields : ['Key' , 'value'],
    	pageSize : 20,
    	proxy : {
    		type : 'ajax',
    		url : '${contextPath}/order/getOrder.do',
    		reader : {
    			type : 'json',
    			root : 'results',
    			successProperty : 'success',
    			totalProperty : 'total'
    		},
    		timeout : 180000
    	}
    });
    
    /*App.store.venues.userVenuesNameAndId = Ext.create */
    
    var rec = null;
    
    var orderStore = Ext.create('Ext.data.ArrayStore', {
        fields : ['id', 'orderNo', 'telephone', 'orderTotal', 'orderStatus', 'orderStatusName', 'userAcountIdName', 'userName', 'venuesName',
                  'paymentStatus', 'paymentStatusName', 'userAccountId', 'ownerAccountId', 'orderTime', 'createTime', 'lastUpdateTime', 'stat', 'statName'
                  //{name:'createTime',type:'date',mapping:'createTime.time',dateFormat : 'Y-m-d'}
                  ],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/order/find.do'
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
    		columns : 4
    	},   	
	    items: [
	       {	    	 
				xtype : 'datefield',
				fieldLabel : '开始时间',
				format : 'Y-m-d',
				labelWidth : 60,
				width : 160,
				id : 'beginTime',
				value : new Date()
		    },
		    {
				xtype : 'datefield',
				fieldLabel : '至',
				format : 'Y-m-d',
				labelWidth : 30,
				width : 130,
				id : 'endTime',
				value : new Date(),
	            validator: function(value){
	            	var endTime = new Date(value);
	            	var beginTime = Ext.getCmp('beginTime').value;
	                if(endTime < beginTime){
	                  return '结束时间不能小于开始时间！！！';
	                }else{
	                  return true;
	                }
	            }
		    },
		    {	      
		    	id : 'orderStatus',     
		    	xtype : 'combo', 
            	store : App.store.order.orderTypeStore,
            	emptyText : '请选择',
            	mode : 'local',	            	
            	valueField : 'key',
            	displayField : 'value',
            	editable : false,                   	
                triggerAction: 'all',
                width : 160,
		    	fieldLabel : '订单状态',
		    	labelWidth : 60
	        },
		    {	      
		    	id : 'paymentStatus',    
		    	xtype : 'combo',   
            	store : App.store.order.orderPaymentStatusStore,
            	emptyText : '请选择',
            	mode : 'local',	            	
            	valueField : 'key',
            	displayField : 'value',
            	editable : true,           	          	
                triggerAction: 'all',
                width : 160,
		    	fieldLabel : '支付状态',
		    	labelWidth : 60
	        },
		    {
		    	id : 'beginAmount',
				xtype : 'numberfield',
				fieldLabel : '订单金额',		
				minValue : 0,	
				labelWidth : 60,
				width : 160,
	            validator: function(value){
	            	var endAmount = Ext.getCmp('endAmount').value;	
	           
	            	if(null == endAmount || '' == endAmount) {
	            		return true;
	            	}
	                if(value > endAmount){
	                  return '订单范围区间不对,开始不能大于结束！！！';
	                }else{
	                  return true;
	                
	                }
	            },
	        	listeners : {
		    		change : function(combo, newValue, oldValue, eOpts){
		    		  Ext.getCmp('endAmount').validate();
		    		}
		    	}										
		    },
		    {
		    	id : 'endAmount',
				xtype : 'numberfield',
				fieldLabel : '至',	
				minValue : 0,			
				labelWidth : 30,
				width : 130,
	            validator: function(value){
	            	var beginAmount = Ext.getCmp('beginAmount').value;	         
	            	if(null == beginAmount || '' == beginAmount) {
	            		return true;
	            	}
	            		
	            	if(null != value && ('' != value) && value < beginAmount){
	            	    return '订单范围区间不对,结束不能小于开始！！！';
	            	} else{
	                    return true;
	                }
	            },
	        	listeners : {
		    		change : function(combo, newValue, oldValue, eOpts){
		    			   Ext.getCmp('beginAmount').validate();
		    		}
		    	}
		    },
		    {
		    	id : 'orderNo',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '订单号',
		    	labelWidth : 50
		    },
		   /*  {
		    	id : 'userAcountIdName',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '预约用户',
		    	labelWidth : 50
		    }, */
		    /* {
		    	id : 'ownerAccountIdName',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '预约场地',
		    	labelWidth : 50
		    }, */
		    {
				text : '查询',
				handler : function() {
					orderStore.proxy.extraParams.beginTime=Ext.getCmp('beginTime').value.format("yyyy-MM-dd");
					orderStore.proxy.extraParams.endTime=Ext.getCmp('endTime').value.format("yyyy-MM-dd");
					orderStore.proxy.extraParams.orderStatus=Ext.getCmp('orderStatus').value;
					orderStore.proxy.extraParams.paymentStatus=Ext.getCmp('paymentStatus').value;
					orderStore.proxy.extraParams.beginAmount=Ext.getCmp('beginAmount').value;
					orderStore.proxy.extraParams.endAmount=Ext.getCmp('endAmount').value;
					orderStore.proxy.extraParams.orderNo=Ext.getCmp('orderNo').value;
					//orderStore.proxy.extraParams.ownerAcountIdName=Ext.getCmp('ownerAcountIdName').value;
					//orderStore.proxy.extraParams.ownerAccountIdName=Ext.getCmp('ownerAccountIdName').value;
					orderStore.load();
				}
		    } 
	    ]
	}); 
	
    
  	//-------- create win
    var accountForm = Ext.create('Ext.form.Panel', {	
    	bodyPadding: 5,
    	defaultType : 'textfield',
    	//store: orderStore,   //测试是否可取数据
    	border : false,	
        layout : 'form', 
        /* layout : {
    		type : 'table',
    		columns : 1
    	}, */ 
    	items : [
    		{
    			fieldLabel : '订单号',
    			id : 'id',
    			//hidden : true,
    			name : 'id',
    			anchor:'95%',
    			hidden : true			
	    	},
    		{
    			fieldLabel : '订单编号',
    			name : 'orderNo',
    			anchor:'90%',
    			allowBlank : false,
    			//sortable: false,
    		    //dataIndex: 'orderNo',   //测试是否可以显示数据
    			labelWidth : 60,
    			disabled : true
    			
	    	},
	    	{
    			fieldLabel : '联系电话',
    			name : 'telephone',
    			anchor:'90%',
    			labelWidth : 60,
                regex : /^1[3|4|5|8][0-9][0-9]{8}$/, 
    	        regexText:"错误的手机格式!"
	    	},
	    	{
    			fieldLabel : '订单总额',
    			name : 'orderTotal',
    			anchor:'90%',
    			allowBlank : false,
    			labelWidth : 60,
                regex : /^[0-9.]{0,8}$/, 
    	        regexText:"订单总额只能是数字!"
	    	},
		    {	      
		    	name : 'orderStatus',     
		    	xtype : 'combo', 
            	store : App.store.order.orderTypeStore,
            	emptyText : '请选择',
            	mode: 'local',          	
            	valueField : 'key',
            	displayField : 'value',		                    	
                triggerAction: 'all',
		    	fieldLabel : '订单状态',
		    	labelWidth : 60
	        },
		    {	      
	        	name : 'paymentStatus',    
		    	xtype : 'combo',   
            	store : App.store.order.orderPaymentStatusStore,
            	emptyText : '请选择',
            	mode : 'local',	            	
            	valueField : 'key',
            	displayField : 'value',
            	editable : false,           	          	
                triggerAction: 'all',
		    	fieldLabel : '支付状态',
		    	labelWidth : 60
	        },
	        /* {	      
		    	name : 'paymentStatus',     
		    	xtype : 'combo', 
            	store : App.store.order.orderPaymentStatusStore,
            	emptyText : '请选择',
            	queryMode: 'local',          	
            	valueField : 'key',
            	displayField : 'value',		                    	
                triggerAction: 'all',
		    	fieldLabel : '角色',
		    	labelWidth : 60
	        }, */
	        {
    			fieldLabel : '预约用户',
    			name : 'userName',
    			anchor:'90%',
    			allowBlank : false,
    			labelWidth : 60,
    			disabled : true
	    	},
	    	{
    			fieldLabel : '预约场地',
    			name : 'venuesName',
    			anchor:'90%',
    			allowBlank : false,
    			labelWidth : 60,
    			disabled : true
	    	},
	    	{
	    		id : 'sorderTime',
    			fieldLabel : '订单时间',
    			xtype : 'datefield',
    			name : 'orderTime',
    			anchor:'90%',
    			format : 'Y-m-d',
    			allowBlank : false,
    			labelWidth : 60
	    	},
	    	{
	    		id : 'screateTime',
    			fieldLabel : '创建时间',
    			name : 'createTime',
    			xtype : 'datefield',
    			anchor:'90%',
    			format : 'Y-m-d',
    			allowBlank : false,
    			labelWidth : 60,
    			disabled : true
	    	}
    	],
    	buttons : [
    		{
    			//id : 'conform',
    			text : '确认',
    			handler : function() {
    				var me = this.up('panel');
					if(!me.form.isValid()){
						return;
					}
					
					me.form.submit({
						url : '${contextPath}/order/updateOrder.do',
						method : 'POST',  
						success : function(form, action) {
							Ext.Msg.alert("信息", action.result.message);
							//me.form.reset();
							accountWin.hide();
							
							orderStore.reload();						
						},
						failure : function(form, action) {
							Ext.Msg.alert("信息", action.result.message);
						},
						waitMsg : 'plz waiting...'
					});
				}
    		},{
    			text : '重置',
    			handler : function(){
    				if(rec == null){
    					accountForm.form.reset();
    				}else{
    					loadOrder(rec);
    				}
    			}
    		}
    	]
    });
    
    var accountWin = Ext.create('Ext.Window', {
    	id : 'accountWin',
        title: '定单修改表单',            
        modal : true,
        closeAction: 'hide',
        width: 300,
        height: 400,
        layout : 'fit',
        items: [accountForm]
    });
    
  	//实现修改框的数据填充
    var loadOrder = function(sode){
    	accountForm.loadRecord(sode);
    	//var orderNo = sode.get('orderNo');
    	//Ext.getCmp('orderNo').setValue(sode.get('orderNo'));
    	//var orderStatus = sode.get('orderStatusName');
    	/*App.store.order.orderTypeStore.load({
    		params : {
    			parentId : sode.get('orderStatus')
    		}
    	});
    	 App.store.order.orderPaymentStatusStore.loaf({
    		params : {
    			parentId : sode.get('paymentStatus')
    		}
    	}); */
   
    	var orderTime = sode.get('orderTime');
    	if(null != orderTime && '' != orderTime) {
    		Ext.getCmp('sorderTime').setValue(new Date(orderTime))
    	}
    	var createTime = sode.get('createTime');
    	Ext.getCmp('screateTime').setValue(new Date(createTime))
    	
    }
    
    var grid = Ext.create('Ext.grid.GridPanel', {	          
    	id : 'grid',   
        store: orderStore,
        defaultType : 'textfield',
        tbar : [      
        	bbar
    		],
        columns: [
		{
		    text: '订单号',
		    width: 100,
		    sortable: true,
		    dataIndex: 'orderNo'
		}, {
            text: '联系电话',
            width: 100,
            sortable: true,
            dataIndex: 'telephone'
        }, {
            text: '订单总额',
            width: 100,
            sortable: true,
            dataIndex: 'orderTotal'
        }, 
        {
            text: '订单状态',
            width: 100,
            sortable: true,
            dataIndex: 'orderStatusName'
        },
        {
            text: '支付状态',
            width: 100,
            sortable: true,
            dataIndex: 'paymentStatusName'
        },{
            text: '预约用户',
            width: 100,
            sortable: true,
            dataIndex: 'userName'
        },{
            text: '预约场地',
            width: 100,
            sortable: true,
            dataIndex: 'venuesName'
        }, {
            text: '订单时间',
            width: 100,
            sortable: true,
            dataIndex: 'orderTime',
            renderer : function(value) {
            	if(value != null){
            		return Ext.util.Format.date(new Date(value), 'Y-m-d');
            	}
            }
        }, {
            text: '创建日期',
            width: 100,
            sortable: true,
            dataIndex: 'createTime',
            	renderer : function(value) {
                	if(value != null){
                		return Ext.util.Format.date(new Date(value), 'Y-m-d');
                	}
                }
        }, {
            text: '更新日期',
            width: 100,
            sortable: true,
            dataIndex: 'lastUpdateTime',
            renderer : function(value) {
            	if(value != null){
            		return Ext.util.Format.date(new Date(value), 'Y-m-d');
            	}
            }
        },
        {
            text: '状态',
            width: 100,
            sortable: true,
            dataIndex: 'statName'
        },
        {
        	text: '操作',
            width: 100,
            sortable: false,
            xtype: 'actioncolumn',
            width: 60,
            items: [
                {
                    icon   : '${contextPath}/manager/images/modify.png',  // Use a URL in the icon config
                    tooltip: '修改订单',
                    handler : function(grid, rowIndex, colIndex) {
                    	rec = orderStore.getAt(rowIndex);
                    	loadOrder(rec);
                    	var id = rec.get('id');
						accountWin.show();
						Ext.Ajax.request({
                    		url : '${contextPath}/order/findOne.do',
                    		params : {
                    			'id' : rec.data.id
                    		},
                    		success : function(r, o){
                    			var strJson = r.responseText;
                    			var obj = eval("(" + strJson + ")");
                    			//Ext.Msg.alert("订单", obj.message);
                    			if(obj.success == true){
                    				orderStore.reload();
                    			}
                    		},
                    		failure : function(form, action){
                    			Ext.Msg.alert("订单", "操作失败！")
                    		}
                    	});
					}
                    /* handler: function(grid, rowIndex, colIndex) {
                       var rec = orderStore.getAt(rowIndex); 
                       //accountWin.show();
                       Ext.Msg.confirm('提示', '审核订单通过！', function(ok) {
                        	if('yes' != ok){
                        		return;
                        	}
                        	
                        	Ext.Ajax.request({
                        		url : '${contextPath}/order/resetDefaultPasswd.do',
                        		params : {
                        			'id' : rec.data.id
                        		},
                        		success : function(r, o){
                        			var strJson = r.responseText;
                        			var obj = eval("(" + strJson + ")");
                        			Ext.Msg.alert("订单", obj.message);
                        			if(obj.success == true){
                        				accountStore.reload();
                        			}
                        		},
                        		failure : function(form, action){
                        			Ext.Msg.alert("订单", "操作失败！")
                        		}
                        	});
                        	
                       });                               
                    } */
                },
                {
                    icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
                    tooltip: '删除',
                    /* handler : function() {
						accountWin.show();
					} */
                    handler: function(grid, rowIndex, colIndex) {
                        var rec = orderStore.getAt(rowIndex);
                        var deleteId = rec.get('id');
                        var orderNo = rec.get('orderNo');       
                         var msg = "是否确定需要删除订单 " + orderNo;
                        Ext.Msg.confirm('提示', msg, function(ok) {
                        	if('yes' != ok){
                        		return;
                        	}                       		
                        		
                        	Ext.Ajax.request({
                        		url : '${contextPath}/order/deleteOrder.do',
                        		params : {
                        			'deleteId' : rec.data.id
                        		},
                        		success : function(r, o){
                        			var strJson = r.responseText;
                        			var obj = eval("(" + strJson + ")");
                        			//Ext.Msg.alert("订单", obj.message);
                        			if(obj.success == true){
                        				orderStore.reload();
                        			}
                        		},
                        		failure : function(form, action){
                        			Ext.Msg.alert("订单", "操作失败！")
                        		}
                        	});
                    	});
                    }
                }
            ]
     }
        ],
    	bbar : Ext.create('Ext.PagingToolbar', {
			store : orderStore,
			displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}',
			emptyMsg : "无数据"
		})
    });
    
    App.store.order.orderTypeStore.load();
    App.store.order.orderPaymentStatusStore.load();
    
    Ext.create('Ext.container.Viewport', {
		layout : 'fit',
		renderTo: 'order-grid',
		items : [grid]		 
	});
});

</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>