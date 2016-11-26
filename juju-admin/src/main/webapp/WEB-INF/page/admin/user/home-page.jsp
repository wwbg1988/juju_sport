<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<script type="text/javascript" src="${contextPath}/js/dateutil.js"></script>
<script type="text/javascript" src="${contextPath}/app/area.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>首页配置</title>
<script type="text/javascript">
Ext.onReady(function() {
    Ext.QuickTips.init();
    // setup the state provider, all state information will be saved to a cookie
    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
    
    if (window.location.href.indexOf('debug') !== -1) {
        Ext.getBody().addCls('x-debug');
    }
    
    var pStore = Ext.create('App.area.AreaStore');
    var cStore = Ext.create('App.area.AreaStore');
    var dStore = Ext.create('App.area.AreaStore');
    
	pStore.proxy.extraParams.level=1;
	cStore.proxy.extraParams.level=2;
	dStore.proxy.extraParams.level=3;
    
    Ext.define("HomePageStore", {
        extend: 'Ext.data.Store',
        fields : ['id', 'title', 'image', 'description', 'score', 'date', 'href', 'node'],
        pageSize : 20,
        proxy : {
           type : 'ajax',
           url : '${contextPath}/user/homepage/find.do',
           reader : {
               type : 'json',
               root : 'results',
               successProperty : 'success',
               totalProperty : 'total'
           },
           timeout : 180000
        }
    });
    
    var carouselStore = Ext.create('HomePageStore');
    var crecommendVenuesStore = Ext.create('HomePageStore');
    var freeVenuesStore = Ext.create('HomePageStore');
    var hotVenuesStore = Ext.create('HomePageStore');
    var listTeamStore = Ext.create('HomePageStore');
    var hotTeamStore = Ext.create('HomePageStore');
    var hotPersonStore = Ext.create('HomePageStore');
    
    //'carousel(首页轮播图),recommend-venues(推荐场馆)，free_venues(免费场馆),hot_venues(热门场馆) list_team(战队情报)，hot_team(最牛战队),hot_person(最牛选手)'      
    
    var loadCarouselStore = function(){
    	carouselStore.proxy.extraParams.key = 'carousel';
    	carouselStore.load();
    }
    
    var loadCrecommendVenuesStore = function(){
    	crecommendVenuesStore.proxy.extraParams.key = 'recommend_venues';
    	crecommendVenuesStore.load();
    }
    
    var loadFreeVenuesStore = function(){
    	freeVenuesStore.proxy.extraParams.key = 'free_venues';
    	freeVenuesStore.load();
    }
    
    var loadHotVenuesStore = function(){
    	hotVenuesStore.proxy.extraParams.key = 'hot_venues';
    	hotVenuesStore.load();
    }
    
    var loadListTeamStore = function(){
    	listTeamStore.proxy.extraParams.key = 'list_team';
    	listTeamStore.load();
    }
    
    var loadHotTeamStore = function(){
    	hotTeamStore.proxy.extraParams.key = 'hot_team';
    	hotTeamStore.load();
    }
    
    var loadHotPersonStore = function(){
    	hotPersonStore.proxy.extraParams.key = 'hot_person';
    	hotPersonStore.load();
    }
    
    var reloadVenuesStore = function(dataKey) {
    	if(dataKey == 'carousel') {
    		carouselStore.reload();
    	} else if(dataKey == 'recommend_venues') {
    		crecommendVenuesStore.reload();
    	} else if(dataKey == 'free_venues') {
    		freeVenuesStore.reload();
    	} else if(dataKey == 'hot_venues') {
    		hotVenuesStore.reload();
    	} else if(dataKey == 'list_team') {
    		listTeamStore.reload();
    	} else if(dataKey == 'hot_team') {
    		hotTeamStore.reload();
    	} else if(dataKey == 'hot_person') {
    		hotPersonStore.reload();
    	}
    }
    
    //----------新增场馆win
    
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
    		columns : 6
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
				text : '查询',
				handler : function() {
					venuesStore.proxy.extraParams.userAccount=Ext.getCmp('userAccount').value;
					venuesStore.proxy.extraParams.nickName=Ext.getCmp('realName').value;
					venuesStore.proxy.extraParams.provinceId=Ext.getCmp('province').value;
					venuesStore.proxy.extraParams.cityId=Ext.getCmp('city').value;
					venuesStore.proxy.extraParams.countryId=Ext.getCmp('districts').value;					
					venuesStore.load();
				}
		    } 
	    ]
	}); 
    
    var pageKey = 'carousel'; 
    
    var venuesGrid = Ext.create('Ext.grid.GridPanel', {	          
    	id : 'grid',   
        store: venuesStore,
        defaultType : 'textfield',
        tbar : [      
        	bbar
    		],
        columns: [      
				{
					text: '操作',
				    width: 100,
				    sortable: false,
				    xtype: 'actioncolumn',
				    width: 60,
				    items: [
				        {
				            icon   : '${contextPath}/manager/images/add.png',  // Use a URL in the icon config
				            tooltip: '添加',
				            handler: function(grid, rowIndex, colIndex) {
				                var rec = venuesStore.getAt(rowIndex);
			                		
			                	Ext.Ajax.request({
			                		url : '${contextPath}/user/homepage/add.do',
			                		params : {
			                			'venuesId' : rec.get('userAccountId'),
			                			'pageKey' : pageKey
			                		},
			                		success : function(r, o){
			                			var strJson = r.responseText;
			                			var obj = eval("(" + strJson + ")");
			                			Ext.Msg.alert("添加提示", obj.message);
			                			reloadVenuesStore(pageKey);
			                		},
			                		failure : function(form, action){
			                			Ext.Msg.alert("用户", "操作失败！")
			                		}
			                	});
				            	
				            }
				        }
				    ]
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
        }
        ],
    	bbar : Ext.create('Ext.PagingToolbar', {
			store : venuesStore,
			displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}',
			emptyMsg : "无数据"
		})
    });
    
     var searchVenusWin = Ext.create('Ext.Window', {
    	id : 'searchVenusWin',
        title: '场馆新增',            
        modal : true,
        closeAction: 'hide',
        width: 880,
        height: 400,
        layout : 'fit',
        items: [venuesGrid]
    });
    
    //---------------------
    
    
    var deleteById = function(id) {
    	Ext.Ajax.request({
    		url : '${contextPath}/user/homepage/delete.do',
    		params : {
    			'configId' : id
    		},
    		success : function(r, o){
    			var strJson = r.responseText;
    			var obj = eval("(" + strJson + ")");	                        			                      			
    			Ext.Msg.alert("删除提示", obj.message);
    			reloadVenuesStore(pageKey);
    		},
    		failure : function(form, action){
    			Ext.Msg.alert("用户", "操作失败！");
    		}
    	});
    }
    
    
    var columns =  [
          		    {
          		    	text: '名称',
          			    width: 150,
          			    sortable: true,
          			    dataIndex: 'title'
          		    },          	   
          	      	{
        		    	text: '图片',
        			    width: 80,
        			    sortable: false,
        			    dataIndex: 'image',
        			    align: 'center',
        			    renderer : function(v) {       			    
        			    	return "<img height=60 with=60 src='/" + v + "'/>";
        			    }
        		    },
          	      	{	          	
          		    	text: '描述',
          			    width: 150,
          			    sortable: true,
          			    dataIndex: 'description'
          	        },   	      	
          	      	{	          	
          		    	text: '积分',
          			    width: 100,
          			    sortable: true,
          			    dataIndex: 'score'
          	        },
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
    		                        var rec;
    		                        var tabTitle = this.up('panel').up('panel').title;
    		                    	if(tabTitle=='首页轮播图'){
    		                        	rec = carouselStore.getAt(rowIndex);
    		                     	} else if(tabTitle=='推荐场馆'){
    		                     		rec = crecommendVenuesStore.getAt(rowIndex);
    		                     	} else if(tabTitle=='免费场馆'){
    		                     		rec = freeVenuesStore.getAt(rowIndex);
    		                     	} else if(tabTitle=='热门场馆'){
    		                     		rec = hotVenuesStore.getAt(rowIndex);
    		                     	} else if(tabTitle=='战队情报'){
    		                     		rec = listTeamStore.getAt(rowIndex);
    		                     	} else if(tabTitle=='最牛战队'){
    		                     		rec = hotTeamStore.getAt(rowIndex);
    		                     	} else if(tabTitle=='最牛选手'){
    		                     		rec = hotPersonStore.getAt(rowIndex);
    		                     	}
    		                        var id = rec.get('id');
    		                        deleteById(id);
    		                    }
    		                }
    		            ]
    		     }
            ];
    
    Ext.define("VenuesBar", {
        extend: 'Ext.Toolbar',
        border : 0,
	    items: [	       
		    {
				text : '新增',
				handler : function() {
					alert(this.up('panel').up('panel').pageKey);
					searchVenusWin.show();
				}
		    } 
	    ]
    });
    
    var carouselBar = Ext.create('Ext.Toolbar', {    	
 	    items: [	       
 		    {
 				text : '新增',
 				handler : function() {
 					pageKey = 'carousel'; 
 					searchVenusWin.show();
 				}
 		    } 
 	    ]
	}); 

    var crecommendVenuesBar = Ext.create('Ext.Toolbar', {    	
 	    items: [	       
 	 		    {
 	 				text : '新增',
 	 				handler : function() {
 	 					pageKey = 'recommend_venues'; 
 	 					searchVenusWin.show();
 	 				}
 	 		    } 
 	 	    ]
 		}); 

    var freeVenuesBar = Ext.create('Ext.Toolbar', {    	
 	    items: [	       
 	 		    {
 	 				text : '新增',
 	 				handler : function() {
 	 					pageKey = 'free_venues'; 
 	 					searchVenusWin.show();
 	 				}
 	 		    } 
 	 	    ]
 		}); 
    
    var hotVenuesBar = Ext.create('Ext.Toolbar', {    	
 	    items: [	       
 	 		    {
 	 				text : '新增',
 	 				handler : function() {
 	 					pageKey = 'hot_venues'; 
 	 					searchVenusWin.show();
 	 				}
 	 		    } 
 	 	    ]
 		}); 
    
    var listTeamBar = Ext.create('Ext.Toolbar', {    	
 	    items: [	       
 	 		    {
 	 				text : '新增',
 	 				handler : function() {
 	 					pageKey = 'list_team'; 
 	 					searchVenusWin.show();
 	 				}
 	 		    } 
 	 	    ]
 		}); 
    
    var hotTeamBar = Ext.create('Ext.Toolbar', {    	
 	    items: [	       
 	 		    {
 	 				text : '新增',
 	 				handler : function() {
 	 					pageKey = 'hot_team'; 
 	 					searchVenusWin.show();
 	 				}
 	 		    } 
 	 	    ]
 		}); 
    
    var hotPersonBar = Ext.create('Ext.Toolbar', {    	
 	    items: [	       
 	 		    {
 	 				text : '新增',
 	 				handler : function() {
 	 					pageKey = 'hot_person'; 
 	 					searchVenusWin.show();
 	 				}
 	 		    } 
 	 	    ]
 		}); 
  
    
    var carouselGrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: carouselStore,
        defaultType : 'textfield',     
        tbar : [      
			carouselBar
        		],
        columns : columns
    });
    
    var crecommendVenuesGrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: crecommendVenuesStore,
        defaultType : 'textfield',     
        tbar : [      
			crecommendVenuesBar				
        		],
        columns : columns
    });
    
    var freeVenuesGrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: freeVenuesStore,
        defaultType : 'textfield',     
        tbar : [      
			freeVenuesBar				
        		],
        columns : columns
    });
	
    var hotVenuesGrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: hotVenuesStore,
        defaultType : 'textfield',     
        tbar : [      
			hotVenuesBar		
        		],
        columns : columns
    });
	    
    var listTeamGrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: listTeamStore,
        defaultType : 'textfield',     
        tbar : [      
			listTeamBar	
        		],
        columns : columns
    });
	
    var hotTeamGrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: hotTeamStore,
        defaultType : 'textfield',     
        tbar : [      
			hotTeamBar
        		],
        columns : columns
    });
    
    var hotPersonGrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: hotPersonStore,
        defaultType : 'textfield',     
        tbar : [      
			hotPersonBar	
        		],
        columns : columns
    });

    var homePageForm = Ext.create('Ext.form.Panel', {	
    	border : false,			    	      
        layout : 'fit',       
        fieldDefaults : {
        	anchor : '0', labelWidth : 80, labelSeparator : ": "
        },
    	items : [
    	     {
            xtype:'tabpanel',
            plain:true,
            activeTab: 0,         
            items:[
	             {
	            	title:'首页轮播图',
		            layout : 'fit',
	                items: [
	                   //carousel      
	                   carouselGrid
	             	],
	                listeners: { activate: handleActivate }
	            }, {
	                title:'推荐场馆',
	                layout : 'fit',
	                items: [
						//recommend-venues
						crecommendVenuesGrid
	                ],
	                listeners: { activate: handleActivate}
	            },
	            {
	                title:'免费场馆',
	                layout : 'fit',
	                items: [
						//free_venues
						freeVenuesGrid
	                ],
	                listeners: { activate: handleActivate}
	            },
	            {
	                title:'热门场馆',
	                layout : 'fit',
	                items: [
						//hot_venues
						hotVenuesGrid
	                ],
	                listeners: { activate: handleActivate }
	            },
	            {
	                title:'战队情报',
	                layout : 'fit',
	                items: [
						//list_team
						listTeamGrid
	                ],
	                listeners: { activate: handleActivate}
	            },
	            {
	                title:'最牛战队',
	                layout : 'fit',
	                items: [
						//hot_team
						hotTeamGrid
	                ],
	                listeners: { activate: handleActivate}
	            },
	            {
	                title:'最牛选手',
	                layout : 'fit',
	                items: [
						//hot_person
						hotPersonGrid
	                ],
	                listeners: { activate: handleActivate }
	            }]
	        }		
    	]
    });
    
    //切换tab后触发的事件
    function handleActivate(tab) {
    	//切换不同tab页面时候，需要给每个页面重新赋值pageKey属性值;
    	var tabTitle=tab.title;
        if(tabTitle=='首页轮播图'){
    		pageKey='carousel';
    	} else if(tabTitle=='推荐场馆'){
    		pageKey='recommend_venues';
    	} else if(tabTitle=='免费场馆'){
    		pageKey='free_venues';
    	} else if(tabTitle=='热门场馆'){
    		pageKey='hot_venues';
    	} else if(tabTitle=='战队情报'){
    		pageKey='list_team';
    	} else if(tabTitle=='最牛战队'){
    		pageKey='hot_team';
    	} else if(tabTitle=='最牛选手'){
    		pageKey='hot_person';
    	}
       
    }
    
    Ext.create('Ext.container.Viewport', {
		layout : 'fit',
		renderTo: 'order-grid',
		items : [homePageForm]		 
	});
        
    loadCarouselStore();
    loadCrecommendVenuesStore();
    loadFreeVenuesStore();
    loadHotVenuesStore();
    loadListTeamStore();
    loadHotTeamStore();
    loadHotPersonStore();
    pStore.load();
    cStore.load();
    dStore.load();
});
</script>
</head>
<body>
	<div id="order-grid"></div>
</body>
</html>