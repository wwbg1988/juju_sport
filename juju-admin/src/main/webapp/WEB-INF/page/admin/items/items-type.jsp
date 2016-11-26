<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
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
	    
	     var itemsTypeStore = Ext.create('Ext.data.ArrayStore', {
		        fields : ['id', 'typeName' ],
		       	pageSize : 20,
		        proxy : {
		            type : 'ajax',
		            api : {
		                read : '${contextPath}/items/items-type/findAllType.do'
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
						text : '新增',
						handler : function() {
							Ext.Msg.prompt('输入', "请输入商品类目", function(btn,value) {
									if(btn == 'ok'){
										Ext.Ajax.request({
	                                		url : '${contextPath}/items/items-type/createType.do',
	                                		params : {
	                                			'typeName' : value
	                                		},
	                                		success : function(r, o){
	                                			var strJson = r.responseText;
	                                			var obj = eval("(" + strJson + ")");
	                                			Ext.Msg.alert("信息", obj.message);    
	                                			itemsTypeStore.reload();                            			
	                                		},
	                                		failure : function(form, action){
	                                			Ext.Msg.alert("信息", "保存失败！")
	                                		}
	                                	});
									}
								}
							);
						}
				    },
				    {
						text : '刷新',
						handler : function() {
							itemsTypeStore.reload();
						}
					}
			    ]
			}); 	
			
			//---------- items type property win
			var typeId;
			
			Ext.define('PropertyType', {
			    extend: 'Ext.data.Model',
			    fields: ['id', 'itemsTypeId', 'propertyName', 'propertyType', 'propertyTypeName', 'required']
			});
			
			var propertyTypeStore = Ext.create('Ext.data.Store', {
	    	//outoLoad : true,
	        fields : ['key', 'value'],      
	        proxy : {
		            type : 'ajax',
		            api : {
		                read : '${contextPath}/items/constants/propertyType.do'
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
		        fields : ['id', 'itemsTypeId', 'propertyName', 'propertyType', 'propertyTypeName', 'required'],      
		        proxy : {
		            type : 'ajax',
		            api : {
		                read : '${contextPath}/items/items-type/findByTypeId.do'
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
	                		url : '${contextPath}/items/items-type/saveOrUpdateProperty.do',
	                		params : {
	                			'id' : rec.get('id'),
	                			'itemsTypeId' : typeId,
	                			'propertyName' : rec.get('propertyName'),
	                			'propertyType' : rec.get('propertyType'),
	                			'propertyTypeName' : rec.get('propertyTypeName'),
	                			'required' : rec.get('required')             			
	                		},
	                		success : function(r, o){
	                			var strJson = r.responseText;
	                			var obj = eval("(" + strJson + ")");
	                			Ext.Msg.alert("信息", obj.message);      
	                			reflushProperties();
	                		},
	                		failure : function(form, action){
	                			Ext.Msg.alert("信息", "保存失败！");
	                		}
	                	});
		            }
		        }
		    });
	    
		    var typePropertGrid = Ext.create('Ext.grid.Panel', {	       
		        plugins: [rowEditing],	      
		        store: store,
		        selType: 'rowmodel',
		        defaultType : 'textfield',
		        columns: [{
		            header: '属性名称',
		            width: 120,
		            sortable: true,
		            dataIndex: 'propertyName',
		            field: {
		                xtype: 'textfield',
		                allowBlank: false
		            }
		        }, 
		        {
		            header: '类型',
		            width: 120,	            
		            dataIndex: 'propertyTypeName',
		            editor : new Ext.form.field.ComboBox({
		            	store : propertyTypeStore,
		            	emptyText : '请选择',
		            	mode : 'local',	            	
		            	valueField : 'value',
		            	displayField : 'value',
		            	editable : false,
		            	allowBlank: false,	            	
	                    triggerAction: 'all',
	                    listeners:{                    
	                        'select': function(combo, records, eOpts){                        	
	                        	 var selection = grid.getView().getSelectionModel().getSelection()[0];
	                        	 selection.set('propertyTypeName', combo.value);
	                        }
	                   }
		            })
		        }, {
		            header: '必填',
		            width: 50,
		            sortable: false,
		            dataIndex: 'required',
		            editor: {
		                xtype: 'checkbox',
		                cls: 'x-grid-checkheader-editor'
		            }
		        }],
		        dockedItems: [{
		            xtype: 'toolbar',
		            items: [{
		                text: '新增',
		                iconCls: 'icon-add',
		                handler: function(){
		                	var type = new PropertyType();
		                	var propertyTypeName = propertyTypeStore.getAt(0).get('propertyTypeName');
		                	type.set('propertyTypeName', propertyTypeName);
		                    store.insert(0, type);
		                    rowEditing.startEdit(0, 0);
		                }
		            }, '-', {
		                itemId: 'delete',
		                text: '删除',
		                iconCls: 'icon-delete',
		                disabled: true,
		                handler: function(){
		                    var selection = typePropertGrid.getView().getSelectionModel().getSelection()[0];
		                    Ext.Msg.confirm('提示', "确认要删除菜单项？", function(ok) {
		                    	if ('yes' != ok) {
		                    		return;
		                    	}
		                    	deleteProperty(selection);
		                    });
		                }
		            },{
		                text: '刷新',
		                iconCls: 'icon-add',
		                handler: function(){
		                	reflushProperties();
		                }
		            }
		            
		            ]
		        }]
		    });
		    
		    typePropertGrid.getSelectionModel().on('selectionchange', function(selModel, selections){
		        typePropertGrid.down('#delete').setDisabled(selections.length === 0);
		    });
	    
		    var deleteProperty = function(selection){
		    	 if (selection) {
	            	 
	             	Ext.Ajax.request({
	             		url : '${contextPath}/items/items-type/deleteProperty.do',
	             		params : {
	             			'propertyId' : selection.get('id')		
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
			
			
			 var typePropertyWin = Ext.create('Ext.Window', {
            	id : 'typePropertyWin',
                title: '类目属性配置',
                closeAction: 'hide',
                width: 380,
                height: 450,
                layout : 'fit',
                items: [typePropertGrid]
            });                      
			
			var reflushProperties = function(){
				 store.removeAll();
				 store.load({
                	params : {
                		itemTypeId : typeId
                	},
                	scope : store,
                	add : false
                });  
			}
			
			//---------------------------		
		    
		    var grid = Ext.create('Ext.grid.GridPanel', {	          
		    	id : 'grid',   
		        store: itemsTypeStore,
		        defaultType : 'textfield',
		        tbar : [      
		        	bbar
		    		],
		        columns: [
					{
					    text: '商品类目',
					    width: 150,
					    sortable: false,
					    dataIndex: 'typeName'
					},
				    {
                        sortable: false,
                        xtype: 'actioncolumn',
                        width: 60,
                        items: [
	                        {
	                            icon   : '${contextPath}/manager/images/modify.png',  // Use a URL in the icon config
	                            tooltip: '类目属性',
	                            handler: function(grid, rowIndex, colIndex) {
	                                var rec = itemsTypeStore.getAt(rowIndex);
	                                typeId = rec.get('id');
	                                typePropertyWin.show();
	                                reflushProperties();
	                                                            
	                            }
	                        },
	                        {
	                            icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
	                            tooltip: '删除',
	                            handler: function(grid, rowIndex, colIndex) {
	                                var rec = itemsTypeStore.getAt(rowIndex);                                         
	                                var msg = "是否确定需要删除产品类目" + rec.get('typeName');
	                              	Ext.Msg.confirm('提示', msg, function(btn) {
	                                	if(btn == 'yes'){
		                                	Ext.Ajax.request({
							             		url : '${contextPath}/items/items-type/deleteType.do',
							             		params : {
							             			'typeId' : rec.get('id')		
							             		},
							             		success : function(r, o){
							             			var strJson = r.responseText;
							             			var obj = eval("(" + strJson + ")");
							             			Ext.Msg.alert("信息", obj.message);      
							             			itemsTypeStore.remove(rec);
							             		},
							             		failure : function(form, action){
							             			Ext.Msg.alert("信息", "保存失败！");
							             		}
							             	});
							             }
	                                });
	                            }
	                        }
                        ]
                    }
		        ],
		    	bbar : Ext.create('Ext.PagingToolbar', {
					store : itemsTypeStore,
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
			
			itemsTypeStore.load();
			propertyTypeStore.load();
		});
</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>