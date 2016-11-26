<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<script type="text/javascript" src="${contextPath}/app/seller-constants.js"></script>
<script type="text/javascript" src="${contextPath}/app/area.js"></script>
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
			
			var sellerStore = Ext.create('Ext.data.ArrayStore', {
		        fields : [
		        		  'id', 'name', 'address', 'fullAddress', 'createTime', 'type', 'typeName', 'parentId',
		                  'parentName', 'level', 'linsence', 'buildTime', 'domain', 'orgCode', 'idCardA',
		                  'idCardB', 'logo', 'startTime', 'endTime', 'withdraw', 'withdrawName',
		                  'internalStatus', 'internalStatusName', 'provinceName', 'cityName', 'regionName',
		                  'provinceCode', 'cityCode', 'regionCode', 'domainId'
		                  ],
		       	pageSize : 20,
		        proxy : {
		            type : 'ajax',
		            api : {
		                read : '${contextPath}/seller/seller-manage/find.do'
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
		    
		    //--------- 图片上传window ----------
		    var shellerUploadForm = Ext.create('Ext.form.Panel', {	
		    	defaults : {xtype : 'panel', flex : 1},	    			    	      
		        layout : {type : 'hbox', align : 'stretch'},       
		    	items : [
		    		{	
		    			bodyPadding: 5,
		    			border: false,				        	 
		    			defaultType: 'fileuploadfield',
		    			fileUpload : true,
		    			fieldDefaults: {
				            labelWidth: 50
				        },	
		    			items : [
				    		{				    			
				    			name : 'domain', 
				    			anchor:'95%',
				    			inputType : 'file'
				    		}
			    		]
			    	},
			    	{
			    		bodyPadding: 5,
		    			border: false,				       	 
		    			defaultType: 'fileuploadfield',
		    			fileUpload : true,
		    			fieldDefaults: {
				            labelWidth: 50
				        },
			    		items : [
					    	{  			                   
			                    id: 'form-file',  
			                    emptyText: 'Select an image',  
			                    fieldLabel: 'Photo',  
			                    name: 'photo-path',  
			                    buttonText: '选择',
			                    anchor:'95%'
							},
							{
								xtype : 'box',  
								id : 'logoPicid',
								fieldLabel: '图片预览',
								width : 150,  
								height : 100,  
								autoEl : {
								    tag : 'img',
									src : '/attachment/image/20110527163130.gif',    
									style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);' 
								}
							} 
			    		]
			    	}
		    	],
		        buttons : [{
								text : '保存',
								handler : function() {
									
								}
							}, {
								text : '重置',
								handler : function() {
																   
								}
							}]
		    	})
		    
		    var shellerUploadWin = Ext.create('Ext.Window', {
            	id : 'shellerUploadWin',
                title: '商家信息修改',            
                modal : true,
                closeAction: 'hide',
                width: 600,
                height: 280,
                layout : 'fit',
                items: [shellerUploadForm]
            });  
                       	    
		    
		    //--------- 信息修改window ----------
		    
		    var shellerModifyForm = Ext.create('Ext.form.Panel', {	
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
			    			id : 'ssellerId',
			    			hidden : true,
			    			name : 'id',
			    			anchor:'95%'
			    		},
			    		{
			    			fieldLabel : '商家名称',
			    			name : 'name',
			    			anchor:'95%',
			    			allowBlank : false
			    		}, {	      
					    	id : 'sprovince',   
					    	name : 'provinceCode',   
					    	xtype : 'combo', 
			            	store : pfStore,
			            	emptyText : '请选择',
			            	queryMode: 'local',           	
			            	valueField : 'id',
			            	displayField : 'name',              	
			                triggerAction: 'all',	                
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
					    	name : 'cityCode',   
					    	xtype : 'combo', 
			            	store : cfStore,
			            	emptyText : '请选择',		            	       	
			            	valueField : 'id',
			            	displayField : 'name',              			            		                
					    	fieldLabel : '城市',				    	
					    	queryMode: 'local',
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
					    	name : 'regionCode',     
					    	xtype : 'combo', 
			            	store : dfStore,
			            	emptyText : '请选择',		            	  	
			            	valueField : 'id',
			            	displayField : 'name',              			               		                
					    	fieldLabel : '地区',				    	
					    	queryMode: 'local',
					    	anchor:'95%'	 
				        },
			    		
			    		{
			    			fieldLabel : '地址',
			    			name : 'address',
			    			anchor:'95%',
			    			allowBlank : false
			    		},{	      			
			    			id : 'ssellerType',	    	 
					    	xtype : 'combo', 
					    	name : 'type',
			            	store : App.store.seller.sellerTypeStore,
			            	emptyText : '请选择',
			            	queryMode: 'local',           	
			            	valueField : 'key',
			            	displayField : 'value',              	
			                triggerAction: 'all',		            
					    	fieldLabel : '商家类型',
					    	anchor:'95%'
				        },{
				        	id : 'sBuildTime',
				        	name : 'buildTime',
			    			xtype : 'datefield',
			    			fieldLabel : '创建时间',		    			
			    			anchor:'95%',
			    			format: 'Y-m-d',
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
			    			fieldLabel : '网址',
			    			name : 'domain', 
			    			anchor:'95%'
			    		},{
			    			fieldLabel : '组织机构代码证',
			    			name : 'orgCode',
			    			anchor:'95%'
			    		},{
			    			id : 'sStartTime',
			    			name : 'startTime',
			    			xtype : 'timefield',
			    			fieldLabel : '营业时间',		    			
			    			format : 'H:i',
			    			anchor:'95%'
			    		},{
			    			id : 'sEndTime',
			    			name : 'endTime',
			    			xtype : 'timefield',
			    			fieldLabel : '结束时间',		    			
			    			format : 'H:i',
			    			anchor:'95%'
			    		},{	    		
			    			id : 'sinternalStatus',	
			    			fieldLabel : '审核状态',
			    			name : 'internalStatus',
			    			xtype : 'combo', 
			            	store : App.store.seller.sellerInternalStatusStore,
			            	emptyText : '请选择',
			            	queryMode: 'local',           	
			            	valueField : 'key',
			            	displayField : 'value',              	
			                triggerAction: 'all',		            				
					    	anchor:'95%'
			    		},{	    			
			    			fieldLabel : '提现状态',
			    			name : 'withdrawName',
			    			anchor:'95%'
				    	},{	    			
			    			hidden : true,
			    			name : 'domainId',
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
										url : '${contextPath}/seller/seller-curd/saveOrUpdate.do',
										method : 'POST',  
										success : function(form, action) {
											Ext.Msg.alert("信息", action.result.message);
											//me.form.reset();
											shellerModifyWin.hide();
											
											if(currRec != null){												
												sellerStore.reload();
											}	
										},
										failure : function(form, action) {
											Ext.Msg.alert("信息", action.result.message);
										},
										waitMsg : 'plz waiting...'
									});
								}
							}, {
								text : '重置',
								handler : function() {
									if(currRec == null){
										shellerModifyForm.form.reset();
									}else{
										loadSeller(currRec);
									}									   
								}
							}]
		    });
		    
		    var shellerModifyWin = Ext.create('Ext.Window', {
            	id : 'shellerModifyWin',
                title: '商家信息修改',            
                modal : true,
                closeAction: 'hide',
                width: 550,
                height: 280,
                layout : 'fit',
                items: [shellerModifyForm]
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
				    	id : 'name',
				    	xtype : 'textfield',
				    	width : 160,
				    	fieldLabel : '商家名称',
				    	labelWidth : 60
				    },
			        {	      
				    	id : 'sellerType',     
				    	xtype : 'combo', 
		            	store : App.store.seller.sellerTypeStore,
		            	emptyText : '请选择',
		            	queryMode: 'local',            	
		            	valueField : 'key',
		            	displayField : 'value',              	
		                triggerAction: 'all',
		                width : 160,
				    	fieldLabel : '商家类型',
				    	labelWidth : 50
			        },
			        {	      
				    	id : 'internalStatus',     
				    	xtype : 'combo', 
		            	store : App.store.seller.sellerInternalStatusStore,
		            	emptyText : '请选择',
		            	queryMode: 'local',          	
		            	valueField : 'key',
		            	displayField : 'value',              	
		                triggerAction: 'all',
		                width : 160,
				    	fieldLabel : '审核状态',
				    	labelWidth : 50
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
				    	queryMode: 'local',
			        },
				    {
						text : '查询',
						handler : function() {
							sellerStore.proxy.extraParams.name=Ext.getCmp('name').value;
							sellerStore.proxy.extraParams.sellerType=Ext.getCmp('sellerType').value;
							sellerStore.proxy.extraParams.internalStatus=Ext.getCmp('internalStatus').value;
							sellerStore.proxy.extraParams.province=Ext.getCmp('province').value;
							sellerStore.proxy.extraParams.city=Ext.getCmp('city').value;
							sellerStore.proxy.extraParams.districts=Ext.getCmp('districts').value;
							
							sellerStore.load();
						}
				    },
				    {
						text : '新增',
						handler : function() {
							shellerModifyForm.form.reset();
							shellerModifyWin.show();
							currRec = null;
						}
				    }
			    ]
			}); 
			
			var currRec = null;
			
			var loadSeller = function(rec){
				
                shellerModifyForm.loadRecord(rec);  
                var buildTime = rec.get('buildTime');  
                Ext.getCmp('sBuildTime').setValue(new Date(buildTime));        
                var startTime = rec.get('startTime');      
                Ext.getCmp('sStartTime').setValue(new Date(startTime));    
                var endTime = rec.get('endTime');      
                Ext.getCmp('sEndTime').setValue(new Date(endTime));    
                Ext.getCmp('ssellerType').setValue(rec.get('type'));
                Ext.getCmp('sinternalStatus').setValue(rec.get('internalStatus'));
                
                Ext.getCmp('sprovince').setValue(rec.get('provinceCode'));   	         
                cfStore.load({
    				params : {
    					parentId : rec.get('provinceCode')  
    				},
    				callback: function(records, operation, success) {
    				 	Ext.getCmp('scity').setValue(rec.get('cityCode')); 	
    				}
    			});	 
                dfStore.load({
    				params : {
    					parentId : rec.get('cityCode')
    				},
    				callback: function(records, operation, success) {
    				 	Ext.getCmp('sdistricts').setValue(rec.get('regionCode'));
    				}
    			});	 	       
			}
			
			 var grid = Ext.create('Ext.grid.GridPanel', {	          
		    	id : 'grid',   
		        store: sellerStore,		
		        tbar : [      
		        	bbar
		    	],      
		        columns: [
		         {
                        sortable: false,
                        xtype: 'actioncolumn',
                        width: 50,
                        items: [
	                        {
	                            icon   : '${contextPath}/manager/images/modify.png',  
	                            tooltip: '信息修改',
	                            handler: function(grid, rowIndex, colIndex) {
	                                currRec = sellerStore.getAt(rowIndex);	                                
	                                loadSeller(currRec);                                                 
                               		shellerModifyWin.show();    
	                            }
	                        },
	                        {
	                            icon   : '${contextPath}/manager/images/add.png',  
	                            tooltip: '资料上传',
	                            handler: function(grid, rowIndex, colIndex) {
	                               shellerUploadWin.show();
	                            }
	                        }
                        ]
                 },
				{
				    text: '名称',
				    width: 100,
				    sortable: false,
				    dataIndex: 'name'
				}, {
		            text: '地址',
		            width: 150,
		            sortable: true,
		            dataIndex: 'fullAddress'
		        }, {
		            text: '行业类型',
		            width: 100,
		            sortable: false,
		            dataIndex: 'typeName'
		        }, {
		            text: '上级公司',
		            width: 100,
		            sortable: false,
		            dataIndex: 'parentName'
		        }, {
		            text: '公司成立时间',
		            width: 100,
		            sortable: false,
		            dataIndex: 'buildTime',
		            renderer : function(value) {
		            	if(value != null){
		            		return Ext.util.Format.date(new Date(value), 'Y-m-d');
		            	}
		            }
		        }, {
		            text: '网址',
		            width: 100,
		            sortable: false,
		            dataIndex: 'domain'
		        }, {
		            text: '组织机构代码证',
		            width: 100,
		            sortable: false,
		            dataIndex: 'orgCode'
		        }, {
		            text: '营业时间',
		            width: 100,
		            sortable: false,
		            dataIndex: 'startTime',
		            format : 'H:i',
		            renderer : function(value) {
		            	if(value != null){
		            		return Ext.util.Format.date(new Date(value), 'H:i:s');
		            	}
		            }
		        }, {
		            text: '结束时间',
		            width: 100,
		            sortable: false,
		            dataIndex: 'endTime',
		            format : 'H:i',
		            renderer : function(value) {
		            	if(value != null){
		            		return Ext.util.Format.date(new Date(value), 'H:i:s');
		            	}
		            }
		        }, {
		            text: '审核状态',
		            width: 100,
		            sortable: false,
		            dataIndex: 'internalStatusName'
		        }, {
		            text: '提现状态',
		            width: 100,
		            sortable: false,
		            dataIndex: 'withdrawName'
		        }
		        ],
		    	bbar : Ext.create('Ext.PagingToolbar', {
					store : sellerStore,
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
	App.store.seller.sellerTypeStore.load();
	App.store.seller.sellerInternalStatusStore.load();
		});
	</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html