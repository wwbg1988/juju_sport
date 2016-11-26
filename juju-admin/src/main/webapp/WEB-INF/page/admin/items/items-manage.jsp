<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<script type="text/javascript" src="${contextPath}/app/seller-auto-complete.js"></script>
<script type="text/javascript" src="${contextPath}/app/items-auto-complete.js"></script>
<script type="text/javascript" src="${contextPath}/app/items-constants.js"></script>
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
	    
	     var itemsStore = Ext.create('Ext.data.ArrayStore', {
		        fields : ['id', 'itemName', 'itemActiveTitle', 'itemMarketPrice', 'itemFactPrice', 'itemFinalPrice', 'sellerId',
		                  'sellerName', 'sellerType', 'itemType', 'itemTypeName', 'shopId', 'shopName', 'createTime', 'itemsDesc'	                  
		                  ],
		       	pageSize : 20,
		        proxy : {
		            type : 'ajax',
		            api : {
		                read : '${contextPath}/items/items-search/find.do'
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
				    	xtype : 'combo',
				    	id : 'itemName',
				    	name : 'itemName',
				    	valueField : 'result',
				    	displayField : 'result',
				    	store : App.autocomplete.items.itemsNameStore,
				    	typeAhead : false,
				    	loadingText : 'Searching...',
				    	hideTrigger : true,				    	
				    	width : 200,
				    	fieldLabel : '商品名称',
				    	labelWidth : 60,
				    	minChars : 1,
				    	listConfig : {
				    		getInnerTpl : function() {
				    			return '<div>{result}</div>';
				    		}
				    	}
				    },
				    {
				    	xtype : 'combo',
				    	id : 'sellerNameS',
				    	name : 'sellerName',
				    	valueField : 'result',
				    	displayField : 'result',
				    	store : App.autocomplete.seller.sellerNameStore,
				    	typeAhead : false,
				    	loadingText : 'Searching...',
				    	hideTrigger : true,				    	
				    	width : 200,
				    	fieldLabel : '商家名称',
				    	labelWidth : 60,
				    	minChars : 1,
				    	listConfig : {
				    		getInnerTpl : function() {
				    			return '<div>{result}</div>';
				    		}
				    	}
				    },				  				    
				    {
				    	xtype : 'combo',
				    	id : 'shopName',
				    	name : 'shopName',
				    	valueField : 'result',
				    	displayField : 'result',
				    	store : App.autocomplete.seller.shopNameStore,
				    	typeAhead : false,
				    	loadingText : 'Searching...',
				    	hideTrigger : true,				    	
				    	width : 200,
				    	fieldLabel : '店铺名称',
				    	labelWidth : 60,
				    	minChars : 1,
				    	listConfig : {
				    		getInnerTpl : function() {
				    			return '<div>{result}</div>';
				    		}
				    	}
				    },			
				    {	      
				    	id : 'itemsType',   
				    	name : 'itemsType',   
				    	xtype : 'combo', 
		            	store : App.store.items.itemsTypeStore,
		            	emptyText : '请选择',
		            	queryMode: 'local',           	
		            	valueField : 'key',
		            	displayField : 'value',              	
		                triggerAction: 'all',	
		                width : 200,    
		                labelWidth : 60,            
				    	fieldLabel : '商品类型'		    	
				    },
				    {
						text : '查询',
						handler : function() {
							itemsStore.proxy.extraParams.sellerName=Ext.getCmp('sellerNameS').getValue();	
							itemsStore.proxy.extraParams.shopName=Ext.getCmp('shopName').value;	
							itemsStore.proxy.extraParams.itemName=Ext.getCmp('itemName').value;		
							itemsStore.proxy.extraParams.itemsType=Ext.getCmp('itemsType').value;				
							itemsStore.load();
						}
				    } 
			    ]
			}); 
			
			 var itemDetailWin = Ext.create('Ext.Window', {
            	id : 'itemDetailWin',
                title: '商品详细',
                autoScroll: true,  
                closeAction: 'hide',
                width: 500,
                height: 450,
                html : '明细',
                constrain : true
            });                
		    
		    var grid = Ext.create('Ext.grid.GridPanel', {	          
		    	id : 'grid',   
		        store: itemsStore,
		        defaultType : 'textfield',
		        tbar : [      
		        	bbar
		    		],
		        columns: [
		         {
                        sortable: false,
                        xtype: 'actioncolumn',
                        width: 60,
                        items: [
	                        {
	                            icon   : '${contextPath}/manager/images/modify.png',  // Use a URL in the icon config
	                            tooltip: '明细查询',
	                            handler: function(grid, rowIndex, colIndex) {
	                                var rec = itemsStore.getAt(rowIndex);                              
	                                itemDetailWin.show();                 
	                                var html = createItemDetailHtml(rec);
	                                itemDetailWin.update(html);               
	                            }
	                        },
	                        {
	                            icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
	                            tooltip: '取消促销',
	                            handler: function(grid, rowIndex, colIndex) {
	                                var rec = itemsStore.getAt(rowIndex);  
	                                var type = rec.get('itemType');
	                                if(type != 1){
	                                	Ext.Msg.confirm('提示', "是否要取消促销？", function(btn) {
												if(btn == 'yes'){
													Ext.Ajax.request({
								             		url : '${contextPath}/items/items-manage/cancelPromotion.do',
								             		params : {
								             			'itemId' : rec.get('id')		
								             		},
								             		success : function(r, o){
								             			var strJson = r.responseText;
								             			var obj = eval("(" + strJson + ")");
								             			Ext.Msg.alert("信息", obj.message);      
								             			itemsStore.reload();
								             		},
								             		failure : function(form, action){
								             			Ext.Msg.alert("信息", "保存失败！");
								             		}
								             	});
											}
										});
	                                }else{
	                                	return;
	                                }
	                                	
	                            }
	                        }
                        ]
                },
				{
				    text: '商品名称',
				    width: 100,
				    sortable: true,
				    dataIndex: 'itemName'
				}, {
		            text: '商家名称',
		            width: 150,
		            sortable: true,
		            dataIndex: 'sellerName'
		        }, {
		            text: '店铺名称',
		            width: 150,
		            sortable: true,
		            dataIndex: 'shopName'
		        }, {
		            text: '商品价格',
		            width: 100,
		            sortable: true,
		            dataIndex: 'itemMarketPrice'
		        }, {
		            text: '商品实际价格',
		            width: 100,
		            sortable: true,
		            dataIndex: 'itemFactPrice'
		        }, {
		            text: '聚喜价',
		            width: 100,
		            sortable: true,
		            dataIndex: 'itemFinalPrice'
		        }, {
		            text: '商品口号',
		            width: 100,
		            sortable: true,
		            dataIndex: 'itemActiveTitle'
		        }, {
		            text: '商品类型',
		            width: 100,
		            sortable: true,
		            dataIndex: 'itemTypeName'
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
		        }
		        ],
		    	bbar : Ext.create('Ext.PagingToolbar', {
					store : itemsStore,
					displayInfo : true,
					displayMsg : '显示条数 {0} - {1} of {2}',
					emptyMsg : "无数据"
				})
		    });
		    
		    var createItemDetailHtml = function(item) {
		    	var html = '<table border=1 width="480" align="center" >';
		    	html += '<tr>';
		    	html += '<td style="width:60px;" align="center" >商品名称</td><td align="center" >' + item.get('itemName') + '</td>';
		    	html += '<td align="center" >商家名称</td><td align="center" >' + item.get('sellerName') + '</td>';
		    	html += '</tr>';
		    	html += '<tr>';
		    	html += '<td align="center" >店铺名称</td><td align="center" >' + item.get('shopName') + '</td>';
		    	html += '<td align="center" >商品口号</td><td align="center" >' + item.get('itemActiveTitle') + '</td>';
		    	html += '</tr>';
		    	
		    	html += '<tr>';
		    	html += '<td align="center" >商品价格</td><td align="center" >' + item.get('itemMarketPrice') + '元</td>';
		    	html += '<td align="center" >商品实际价格</td><td align="center" >' + item.get('itemFactPrice') + '元</td>';
		    	html += '</tr>';
		    	html += '<tr>';
		    	html += '<td align="center" >聚喜价</td><td align="center" >' + item.get('itemFinalPrice') + '元</td>';
		    	html += '<td align="center" ></td><td align="center" ></td>';
		    	html += '</tr>';
		    	
		    	var desc = item.get('itemsDesc');
		    	Ext.Array.forEach(desc,function(str,index,array){ //单纯的遍历数组    
		    		var detail = eval(str);
			        html += '<tr>';
			    	html += '<td align="center" >' + detail.itemDescKey  + '</td><td colspan="3" align="center" >' + detail.itemDescTextValue + '元</td>';			    	
			    	html += '</tr>';
			    });  
		    	
		    	html += '</table>';
		    	return html;
		    }
		    
		    Ext.create('Ext.container.Viewport', {
				layout : 'fit',
				renderTo: 'order-grid',
				items : [grid]		 
			});
			
			App.store.items.itemsTypeStore.load();
		});
</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>