<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<script type="text/javascript" src="${contextPath}/js/dateutil.js"></script>
<script type="text/javascript" src="${contextPath}/app/area.js"></script>
<script type="text/javascript" src="${contextPath}/app/common-constants.js"></script>
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
    
    var pStore = Ext.create('App.area.AreaStore');
    var cStore = Ext.create('App.area.AreaStore');
    var dStore = Ext.create('App.area.AreaStore');
    
	pStore.proxy.extraParams.level=1;
	cStore.proxy.extraParams.level=2;
	dStore.proxy.extraParams.level=3;
	
	var pfStore = Ext.create('App.area.AreaStore');
    var cfStore = Ext.create('App.area.AreaStore');
    var dfStore = Ext.create('App.area.AreaStore');
    
	pfStore.proxy.extraParams.level=1;
	cfStore.proxy.extraParams.level=2;
	dfStore.proxy.extraParams.level=3;	
	
	var fUserStatStore = Ext.create('App.store.common.UserStatStore');
    var bUserStatStore = Ext.create('App.store.common.UserStatStore');
    
    var chargeTypeStore = Ext.create("Ext.data.ArrayStore", {
    	fields : ["key", "value"],
    	data :[[0, "免费"],[1, "收费"]]
    });
    
    var venuesStore = Ext.create('Ext.data.ArrayStore', {
    	fields : ['id','userAccountId','userAccount','provinceid','provinceName','cityid','cityName','countryid','countryName','address',
    	          'contacts','mobileNo','descs','chargeType','chargeTypeName','venueType','otherServices','email','nickName','userLevel',
    	          'userScore','createTime','lastUpdateTime','stat','venueImg','maxNum','teamType','isFalse','startNum','pageSize','venueTypeNames'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/user/venues/find.do'
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
		    	id : 'userAccount',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '用户帐号',
		    	labelWidth : 70
		    },
		    {
		    	id : 'realName',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '场馆名称',
		    	labelWidth : 70
		    },
		    {	      
		    	id : 'province',     
		    	xtype : 'combo', 
            	store : pStore,
            	emptyText : '请选择',
            	queryMode: 'local',            	
            	valueField : 'id',
            	displayField : 'name',              	
                triggerAction: 'all',
                width : 160,
		    	fieldLabel : '省份',
		    	labelWidth : 50,
		    	listeners : {
		    		change : function(combo, newValue, oldValue, eOpts){
		    			Ext.getCmp('city').setValue();
		    			Ext.getCmp('districts').setValue();
		    			cStore.load({
		    				params : {
		    					parentId : newValue
		    				}
		    			});
		    		}
		    	}
	        },
	        {	      
		    	id : 'city',     
		    	xtype : 'combo', 
            	store : cStore,
            	emptyText : '请选择',		            	       	
            	valueField : 'id',
            	displayField : 'name',              			            
                width : 160,
		    	fieldLabel : '城市',
		    	labelWidth : 50,
		    	queryMode: 'local',
		    	listeners : {
		    		change : function(combo, newValue, oldValue, eOpts){
		    			Ext.getCmp('districts').setValue();
		    			dStore.load({
		    				params : {
		    					parentId : newValue
		    				}
		    			});
		    		}
		    	}
	        },
	        {	      
		    	id : 'districts',     
		    	xtype : 'combo', 
            	store : dStore,
            	emptyText : '请选择',		            	  	
            	valueField : 'id',
            	displayField : 'name',              			               
                width : 160,
		    	fieldLabel : '地区',
		    	labelWidth : 50,
		    	queryMode: 'local'
	        },
	        {	      
		    	id : 'stat',    
		    	xtype : 'combo', 
            	store : bUserStatStore,
            	emptyText : '请选择',		            	  	
            	valueField : 'key',
            	displayField : 'value',              			               		                
		    	fieldLabel : '状态',				    	
		    	width : 160,
		    	labelWidth : 50,
		    	queryMode: 'local'
	        },
		    {
				text : '查询',
				handler : function() {
					venuesStore.proxy.extraParams.userAccount=Ext.getCmp('userAccount').value;
					venuesStore.proxy.extraParams.nickName=Ext.getCmp('realName').value;
					venuesStore.proxy.extraParams.provinceId=Ext.getCmp('province').value;
					venuesStore.proxy.extraParams.cityId=Ext.getCmp('city').value;
					venuesStore.proxy.extraParams.countryId=Ext.getCmp('districts').value;
					venuesStore.proxy.extraParams.stat=Ext.getCmp('stat').value;
					venuesStore.load();
				}
		    } 
	    ]
	}); 
	
    
    //--------- 信息修改window ----------
    
    var userModifyForm = Ext.create('Ext.form.Panel', {	
    	defaults : {xtype : 'panel', flex : 1},	    			    	      
        layout : {type : 'hbox', align : 'stretch'},       
    	items : [
    		{	
    			bodyPadding: 5,
    			border: false,
		        fieldDefaults: {
		            labelWidth: 80
		        },		 
    			defaultType: 'textfield',
    			items : [
	    		{
	    			hidden : true,
	    			name : 'id',
	    			anchor:'95%'
	    		},
	    		{
	    			fieldLabel : '用户帐号',
	    			name : 'userAccount',
	    			readOnly : true, 
	    			anchor:'95%',
	    			allowBlank : true
	    		},
	    		{
	    			fieldLabel : '场馆名称',
	    			name : 'nickName',
	    			anchor:'95%',
	    			allowBlank : true,
	    			maxLength : 20
	    		},
				{	      
			    	id : 'sprovince',   
			    	name : 'provinceid',   
			    	xtype : 'combo', 
	            	store : pfStore,
	            	emptyText : '请选择',
	            	queryMode: 'local',           	
	            	valueField : 'id',
	            	displayField : 'name',              	
	                triggerAction: 'all',	
	                editable : false,
			    	fieldLabel : '省份',			    	
			    	anchor:'95%',
			    	listeners : {
			    		change : function(combo, newValue, oldValue, eOpts){
			    			var scity = Ext.getCmp('scity').getValue();
			    			if(scity != null && oldValue != null) {
			    				Ext.getCmp('scity').setValue();
			    			}
			    			
			    			var sdistricts = Ext.getCmp('sdistricts').getValue();
			    			if(sdistricts != null && oldValue != null) {
			    				Ext.getCmp('sdistricts').setValue();
			    			}
			    			cfStore.load({
			    				params : {
			    					parentId : newValue
			    				}
			    			});
			    		}
			    	}
		        },
		        {	      
			    	id : 'scity',   
			    	name : 'cityid',   
			    	xtype : 'combo', 
	            	store : cfStore,
	            	emptyText : '请选择',		            	       	
	            	valueField : 'id',
	            	displayField : 'name',              			            		                
			    	fieldLabel : '城市',				    	
			    	queryMode: 'local',
			    	editable : false,
			    	anchor:'95%',
			    	listeners : {
			    		change : function(combo, newValue, oldValue, eOpts){
			    			var sdistricts = Ext.getCmp('sdistricts').getValue();
			    			if(sdistricts != null && oldValue != null) {
			    				Ext.getCmp('sdistricts').setValue();
			    			}
			    			dfStore.load({
			    				params : {
			    					parentId : newValue
			    				}
			    			});
			    		}
			    	}
		        },
		        {	      
			    	id : 'sdistricts', 
			    	name : 'countryid',     
			    	xtype : 'combo', 
	            	store : dfStore,
	            	emptyText : '请选择',		            	  	
	            	valueField : 'id',
	            	displayField : 'name',              			               		                
			    	fieldLabel : '地区',				    	
			    	queryMode: 'local',
			    	editable : false,
			    	anchor:'95%'	 
		        },
	    		
	    		{
	    			fieldLabel : '地址',
	    			name : 'address',
	    			anchor:'95%',
	    			allowBlank : true
	    		}
	    		]
	    	},
	    	{
	    		bodyPadding: 5,
    			border: false,
		        fieldDefaults: {
		            labelWidth: 80
		        },		 
    			defaultType: 'textfield',
	    		items : [
		    	{
	    			fieldLabel : '邮箱',
	    			vtype:'email',
	    			name : 'email', 
	    			anchor:'95%'
	    		},{
	    			fieldLabel : '手机号码',
	    			name : 'mobileNo',
	    			anchor:'95%',
	    			regex : /^1[3|4|5|8][0-9][0-9]{8}$/, 
	   	            regexText:"错误的手机格式!"
	    		},{	      			    	
			    	xtype : 'combo', 
			    	name : 'chargeType',    
	            	store : chargeTypeStore,
	            	emptyText : '请选择',		            	  	
	            	valueField : 'key',
	            	displayField : 'value',              			               	               
			    	fieldLabel : '收费类型',			
			    	queryMode: 'local',
			    	anchor:'95%'
		        },{
	    			fieldLabel : '场馆类型',
	    			name : 'venueTypeNames',
	    			anchor:'95%',
	    			readOnly : true
	    		},{		     
		    		id : 'fcreateTime', 
		        	name : 'createTime',
	    			xtype : 'datefield',
	    			fieldLabel : '创建时间',		    			
	    			anchor:'95%',
	    			format: 'Y-m-d',
	    			readOnly : true
	    		},
		        {	      
			    	name : 'stat',     
			    	xtype : 'combo', 
	            	store : fUserStatStore,
	            	emptyText : '请选择',		            	  	
	            	valueField : 'key',
	            	displayField : 'value',              			               		                
			    	fieldLabel : '状态',		
			    	editable : false,
			    	queryMode: 'local',
			    	anchor:'95%'
		        }
	    		]
	    	}
    	],
        buttons : [{
						text : '保存',
						handler : function() {
							var me = this.up('panel');
							if(!me.form.isValid()){
								return;
							}
							
							me.form.submit({
								url : '${contextPath}/user/venues/update.do',
								method : 'POST',  
								success : function(form, action) {
									Ext.Msg.alert("信息", action.result.message);
									//me.form.reset();
									userModifyWin.hide();
									venuesStore.reload();									
								},
								failure : function(form, action) {
									Ext.Msg.alert("信息", "更新失败");
								},
								waitMsg : 'plz waiting...'
							});
						}
					}]
    });
    
    var userModifyWin = Ext.create('Ext.Window', {
    	id : 'accountWin',
        title: '场馆信息修改',            
        modal : true,
        closeAction: 'hide',
        width: 550,
        height: 280,
        layout : 'fit',
        items: [userModifyForm]
    });
    
  	//实现修改框的数据填充
    var loadUser = function(rec){
    	userModifyForm.loadRecord(rec);
    	var createTime = rec.get('createTime'); 
    	Ext.getCmp('fcreateTime').setValue(new Date(createTime));    
    	
    }
    
    var grid = Ext.create('Ext.grid.GridPanel', {	          
    	id : 'grid',   
        store: venuesStore,
        defaultType : 'textfield',
        tbar : [      
        	bbar
    		],
        columns: [
				{
				    text     : '状态',
				    width    : 60,
				    sortable : true,
				    renderer : function(val) {
				        if (val == 0) {
				            return '<span style="color:red";>' + '禁用' + '</span>';
				        } else if (val == 1) {
				            return '<span style="color:green";>' + '正常' + '</span>';
				        }
				        return '未知';
				    },
				    dataIndex: 'stat'
				},      	      	
      	        {
    		    	text: '场馆图片',
    			    width: 80,
    			    sortable: false,
    			    dataIndex: 'venueImg',
    			    align: 'center',
    			    renderer : function(v) {
    			    	//return "<img height=60 with=60 src='${contextPath}" + v + "'/>";
    			    	return "<img height=60 with=60 src='/" + v + "'/>";
    			    }
    		    },
    		    {
      		    	text: '场馆名称',
      			    width: 120,
      			    sortable: true,
      			    dataIndex: 'nickName'
      		    },
				{
      		    	text: '用户帐号',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'userAccount'
      		    },
      		    {
      		    	text: '省份',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'provinceName'
      		    },
      		    {	          	
      		    	text: '城市',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'cityName'
      	        },
      	        {	            		    	
      		    	text: '地区',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'countryName'
      	        },
      	        {	         	
      		    	text: '地址',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'address'
      	        },
      	      {	         	
      		    	text: '联系方式',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'contacts'
      	        },
      	        {	            		    	
      		    	text: '手机号码',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'mobileNo'
      	        },
      	        {	      
      		    	text: '描述',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'descs'
      	        },
      	        {	           		    	
      		    	text: '收费类型',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'chargeType',
      			    renderer : function(v) {
	  			    	if(v == 0) {
	  			    		return "免费";
	  			    	}else if(v == 1) {
	  			    		return "收费";
	  			    	}			    	
  			    	}
      	        },
      	        {	           		    	
      		    	text: '场馆类型',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'venueTypeNames'
      	        },      	     
      	        {	            		    	
      		    	text: '邮箱',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'email'
      	        },     	     
        {
            text: '创建时间',
            width: 100,
            sortable: true,
            dataIndex: 'createTime',
            renderer : function(value) {
            	if(value != null){
            		return Ext.util.Format.date(new Date(value), 'Y-m-d');
            	}
            }
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
                    	var rec = venuesStore.getAt(rowIndex);                         
                    	userModifyWin.show();					
                    	loadUser(rec);
					}
                },
                {
                    icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
                    tooltip: '禁用',
                    handler: function(grid, rowIndex, colIndex) {
                        var rec = venuesStore.getAt(rowIndex);
                        var deleteId = rec.get('id');
                        var userAccount = rec.get('nickName');   
                        var stat = rec.get('stat');  
                         var msg = "是否确定需要禁用场馆: " + userAccount+" ?";
                     	if(stat==1){
                            Ext.Msg.confirm('提示', msg, function(ok) {
                        	if('yes' != ok){
                        		return;
                        	} 
                        	
                        	Ext.Ajax.request({
                        		url : '${contextPath}/user/venues/delete.do',
                        		params : {
                        			'id' : deleteId
                        		},
                        		success : function(r, o){
                        			var strJson = r.responseText;
                        			var obj = eval("(" + strJson + ")");
                        			Ext.Msg.alert("提示", obj.message);
                        			if(obj.success == true){
                        				venuesStore.reload();
                        			}
                        		},
                        		failure : function(form, action){
                        			Ext.Msg.alert("用户", "操作失败！")
                        		}
                        	});
                    	 });
                       }else{
                        	  Ext.Msg.alert("提示信息", "该场馆已经被禁用！")
                      }
                    }
                }
            ]
     }
        ],
    	bbar : Ext.create('Ext.PagingToolbar', {
			store : venuesStore,
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
    
    pStore.load();
    pfStore.load();
    App.store.common.jobStore.load();
    fUserStatStore.load();
    bUserStatStore.load();
});

</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>