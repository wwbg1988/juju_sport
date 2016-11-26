<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<script type="text/javascript" src="${contextPath}/js/dateutil.js"></script>
<script type="text/javascript" src="${contextPath}/app/order-constants.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>聚运动后台管理---会员管理</title>
<script type="text/javascript">
Ext.onReady(function() {
    Ext.QuickTips.init();
    // setup the state provider, all state information will be saved to a cookie
    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
    
    App.store.order.userThirdTypeStore = Ext.create('Ext.data.Store', {
        fields : ['key', 'value'],
        pageSize : 20,
        proxy : {
            type : 'ajax',
            url : '${contextPath}/user/getThirdType.do',
            reader : {
                type : 'json',
                root : 'results',
                successProperty : 'success',
                totalProperty : 'total'
            },
            timeout : 180000
        }
    });
    
    App.store.order.userTypeStore = Ext.create('Ext.data.Store', {
        fields : ['key', 'value'],
        pageSize : 20,
        proxy : {
            type : 'ajax',
            url : '${contextPath}/user/getUserType.do',
            reader : {
                type : 'json',
                root : 'results',
                successProperty : 'success',
                totalProperty : 'total'
            },
            timeout : 180000
        }
    });
    
    var orderStore = Ext.create('Ext.data.ArrayStore', {
        fields : ['id', 'userAccount', 'password', 'repassword', 'type', 'typeName', 'thirdLogin', 'thirdType', 'thirdTypeName',
                  'createTime', 'lastUpdateTime', 'stat', 'statName'
                  //{name:'createTime',type:'date',mapping:'createTime.time',dateFormat : 'Y-m-d'}
        		  //'paymentStatus', 'paymentStatusName', 'userAccountId', 
                  ],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/user/find.do'
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
	       /* {
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
				value : new Date()
		    },*/
		    {
		    	id : 'id',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '用户编号',
		    	labelWidth : 50
		    },
		    {
		    	id : 'userAcount',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '预约用户',
		    	labelWidth : 50
		    },
		    {	      
		    	id : 'thirdType',     
		    	xtype : 'combo', 
            	store : App.store.order.userThirdTypeStore,
            	emptyText : '请选择',
            	mode : 'local',	            	
            	valueField : 'key',
            	displayField : 'value',
            	editable : false,                   	
                triggerAction: 'all',
                width : 160,
		    	fieldLabel : '第三方账号',
		    	labelWidth : 50
	        },
	        {	      
		    	id : 'type',     
		    	xtype : 'combo', 
            	store : App.store.order.userTypeStore,
            	emptyText : '请选择',
            	mode : 'local',	            	
            	valueField : 'key',
            	displayField : 'value',
            	editable : false,                   	
                triggerAction: 'all',
                width : 160,
		    	fieldLabel : '用户类型',
		    	labelWidth : 50
	        },
		    {
				text : '查询',
				handler : function() {
					//orderStore.proxy.extraParams.beginTime=Ext.getCmp('beginTime').value.format("yyyy-MM-dd");
					//orderStore.proxy.extraParams.endTime=Ext.getCmp('endTime').value.format("yyyy-MM-dd");
					orderStore.proxy.extraParams.type=Ext.getCmp('type').value;
					orderStore.proxy.extraParams.thirdType=Ext.getCmp('thirdType').value;
					orderStore.proxy.extraParams.userAccount=Ext.getCmp('userAccount').value;
					orderStore.proxy.extraParams.orderNo=Ext.getCmp('id').value;
					orderStore.load();
				}
		    } 
	    ]
	}); 
	
    
  	//-------- create win
    var accountForm = Ext.create('Ext.form.Panel', {	
    	bodyPadding: 5,
    	defaultType : 'textfield',
    	border : false,	
        layout : 'form', 
    	items : [
    		{
    			fieldLabel : '用户编号',
    			id : 'id',
    			//hidden : true,
    			name : 'id',
    			anchor:'95%',
    			readOnly : true
	    	},
    		{
    			fieldLabel : '用户账号',
    			name : 'orderNo',
    			anchor:'90%',
    			allowBlank : false,
    			labelWidth : 60
	    	},
	    	{
    			fieldLabel : '用户密码',
    			name : 'telephone',
    			anchor:'90%',
    			labelWidth : 60
	    	},
	    	{
    			fieldLabel : '用户类型',
    			name : 'orderTotal',
    			anchor:'90%',
    			allowBlank : false,
    			labelWidth : 60
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
	        {
    			fieldLabel : '预约用户',
    			name : 'userAccountId',
    			anchor:'90%',
    			allowBlank : false,
    			labelWidth : 60
	    	},
	    	{
    			fieldLabel : '预约场地',
    			name : 'ownerAccountId',
    			anchor:'90%',
    			allowBlank : false,
    			labelWidth : 60
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
    			labelWidth : 60
	    	},
	    	{
	    		id : 'slastUpdateTime',
    			fieldLabel : '更新时间',
    			name : 'lastUpdateTime',
    			xtype : 'datefield',
    			anchor:'90%',
    			format : 'Y-m-d',
    			allowBlank : false,
    			labelWidth : 60
	    	},
	    	{
	    		fieldLabel : '状态',
    			name : 'stat',
    			anchor:'90%',
    			allowBlank : false,
    			labelWidth : 60
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
						url : '${contextPath}/user/updateOrder.do',
						method : 'POST',  
						success : function(form, action) {
							Ext.Msg.alert("信息", action.result.message);
							//me.form.reset();
							accountWin.hide();
							
							if(rec != null){												
								orderStore.reload();
							}	
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
        title: '订单修改表单',            
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
    	App.store.order.orderTypeStore.load({
    		params : {
    			parentId : sode.get('orderStatus')
    		}
    	});
    	/* App.store.order.orderPaymentStatusStore.loaf({
    		params : {
    			parentId : sode.get('paymentStatus')
    		}
    	}); */
    	var lastUpdateTime = sode.get('lastUpdateTime');
    	Ext.getCmp('slastUpdateTime').setValue(new Date(lastUpdateTime))
    	var orderTime = sode.get('orderTime');
    	Ext.getCmp('sorderTime').setValue(new Date(orderTime))
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
		    text: '用户编号',
		    width: 100,
		    sortable: false,
		    dataIndex: 'id'
		}, {
            text: '用户账号',
            width: 100,
            sortable: true,
            dataIndex: 'userAccount'
        }, {
            text: '用户密码',
            width: 100,
            sortable: false,
            dataIndex: 'password'
        }, 
        {
            text: '用户类型',
            width: 100,
            sortable: false,
            dataIndex: 'typeName'
        },
        {
            text: '第三方账号类型',
            width: 100,
            sortable: false,
            dataIndex: 'thirdTypeName'
        },
        {
            text: '创建时间',
            width: 100,
            sortable: false,
            dataIndex: 'createTime',
            renderer : function(value) {
            	if(value != null){
            		return Ext.util.Format.date(new Date(value), 'Y-m-d');
            	}
            }
        }, 
        {
            text: '更新日期',
            width: 100,
            sortable: false,
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
            sortable: false,
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
                    tooltip: '修改用户',
                    handler : function(grid, rowIndex, colIndex) {
                    	var rec = orderStore.getAt(rowIndex);
                    	loadOrder(rec);
                    	var id = rec.get('id');
						accountWin.show();
						Ext.Ajax.request({
                    		url : '${contextPath}/user/findOne.do',
                    		params : {
                    			'id' : rec.data.id
                    		},
                    		success : function(r, o){
                    			var strJson = r.responseText;
                    			var obj = eval("(" + strJson + ")");
                    			//Ext.Msg.alert("yonghu", obj.message);
                    			if(obj.success == true){
                    				orderStore.reload();
                    			}
                    		},
                    		failure : function(form, action){
                    			Ext.Msg.alert("用户", "操作失败！")
                    		}
                    	});
					}
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
                         var msg = "是否确定需要删除用户 " + orderNo;
                        Ext.Msg.confirm('提示', msg, function(ok) {
                        	/* if('yes' != ok){
                        		return;
                        	} */
                        		
                        	Ext.Ajax.request({
                        		url : '${contextPath}/user/deleteUser.do',
                        		params : {
                        			'deleteId' : rec.data.id
                        		},
                        		success : function(r, o){
                        			var strJson = r.responseText;
                        			var obj = eval("(" + strJson + ")");
                        			//Ext.Msg.alert("yonghu", obj.message);
                        			if(obj.success == true){
                        				orderStore.reload();
                        			}
                        		},
                        		failure : function(form, action){
                        			Ext.Msg.alert("用户", "操作失败！")
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