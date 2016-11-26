<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>
<script type="text/javascript" src="${contextPath}/js/dateutil.js"></script>
<script type="text/javascript" src="${contextPath}/app/area.js"></script>
<script type="text/javascript" src="${contextPath}/app/common-constants.js"></script>
<script type="text/javascript" src="${contextPath}/js/htmlEditorUploadImg.js"></script>
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
    
    if (window.location.href.indexOf('debug') !== -1) {
        Ext.getBody().addCls('x-debug');
    }
    
    var bSportTypeStore = Ext.create('App.store.common.SportTypeStore');
    var pSportTypeStore = Ext.create('App.store.common.SportTypeStore');
    var cSportTypeStore = Ext.create('App.store.common.SportTypeStore');
    var neSportTypeStore = Ext.create('App.store.common.SportTypeStore');
    
    var infoTypeStore = Ext.create('App.store.common.InfoTypeStore');
    
    var currRace;
    //-------------------------
    
    var orderStore = Ext.create('Ext.data.ArrayStore', {
    	fields : ['id', 'sportTypeId', 'infoType', 'sportName', 'title', 'pic', 'organizers', 'createTime', 'lastUpdateTime', 'stat', 'context'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/game/race/find.do'
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
    
    var imageStore = Ext.create('Ext.data.ArrayStore', {
    	fields : ['id', 'name', 'url', 'createTime', 'lastUpdateTime', 'stat', 'infactUrl'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/common/images/find.do'
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
    		columns : 5
    	},   	
	    items: [	       
		    {
		    	id : 'title',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '图片名称',
		    	labelWidth : 70
		    },
		    {
		    	id : 'organizers',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '承办方',
		    	labelWidth : 70
		    },
	        {	      
		    	id : 'sportTypeId',    
		    	xtype : 'combo', 
            	store : bSportTypeStore,
            	emptyText : '请选择',		            	  	
            	valueField : 'key',
            	displayField : 'value',              			               		                
		    	fieldLabel : '类型',				    	
		    	width : 160,
		    	labelWidth : 50,
		    	queryMode: 'local'
	        },
		    {
				text : '查询',
				handler : function() {
					orderStore.proxy.extraParams.title=Ext.getCmp('title').value;
					orderStore.proxy.extraParams.organizers=Ext.getCmp('organizers').value;
					orderStore.proxy.extraParams.sportTypeId=Ext.getCmp('sportTypeId').value;
					orderStore.load();
				}
		    },
		    {
				text : '新建',
				handler : function() {
					raceCreateWin.show();
					cSportTypeStore.reload();
				}
		    }
	    ]
	}); 
    
    //------------------------图片资源新增
    
    var imageCreateForm = Ext.create('Ext.form.Panel', {	
    	bodyPadding: 5,
    	defaultType : 'textfield',
    	border : false,	
        layout : 'form', 
        url : '${contextPath}/common/images/create.do',	
    	items : [
   	         {
               	 xtype: 'filefield',	        
	            emptyText: 'Select an image',
	            fieldLabel: '图片',
	            name: 'image',
	            buttonText: '请选择',
	            anchor:'95%',
	            buttonConfig: {
	                iconCls: 'upload-icon'
	            }
	        }, {
                   fieldLabel: '名称',
                   name: 'name',
                   anchor:'95%'
               }
    	],
    	buttons : [{
			text : '保存',
			handler : function() {
				var form = this.up('panel').getForm();
				if(!form.isValid()){
					return;
				}
				
				form.submit({														
					success : function() {
						Ext.Msg.alert("信息", "新增成功!");
						imageCreateWin.hide();
						imageStore.reload();
					},
					failure : function() {
						Ext.Msg.alert("信息", "更新失败!");
					},
					waitMsg : 'plz waiting...'
				});			
			}
		}]
    });
    
    var imageCreateWin = Ext.create('Ext.Window', {
    	id : 'imageCreateWin',
        title: '图片新增',            
        modal : true,
        closeAction: 'hide',
        width: 400,
        height: 250,
        layout : 'fit',
        items: [imageCreateForm]
    });
    
    //----------------------未参加比赛
    
    var notEnterForRaceTeambbar =  Ext.create('Ext.Toolbar', {
    	border : 0,
    	layout : {
    		type : 'table',
    		columns : 5
    	},   	
	    items: [	       
            {
		    	id : 'notEnterForRaceTeamName',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '球队名称',
		    	labelWidth : 70
		    },
		    {	      
		    	id : 'notEnterForRaceSportTypeId',    
		    	xtype : 'combo', 
            	store : neSportTypeStore,
            	emptyText : '请选择',		            	  	
            	valueField : 'key',
            	displayField : 'value',              			               		                
		    	fieldLabel : '类型',				    	
		    	width : 160,
		    	labelWidth : 50,
		    	queryMode: 'local'
	        },
		    {
				text : '查询',
				handler : function() {
					notEnterForRaceTeamStore.proxy.extraParams.raceTeamName = Ext.getCmp('notEnterForRaceTeamName').value;				
					notEnterForRaceTeamStore.proxy.extraParams.sportType = Ext.getCmp('notEnterForRaceSportTypeId').value;
					notEnterForRaceTeamStore.proxy.extraParams.raceId = currRace.get('id');				
					notEnterForRaceTeamStore.load();
				}
		    }
	    ]
	}); 

    
    var notEnterForRaceTeamStore = Ext.create('Ext.data.ArrayStore', {
    	fields : ['id','teamName','thumbnail','warType', 'warTypeName', 'sportId','sportName','contact','orderId','userAccountId','userAccountName','maxNum','joinNum','createTime','lastUpdateTime','stat','nameandtime','warDesc'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/game/team/findNotEnterForRaceTeam.do'
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
    
    var notEnterForRaceTeamGrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: notEnterForRaceTeamStore,
        defaultType : 'textfield',     
        tbar : [      
			notEnterForRaceTeambbar
        		],
        columns: [
{
	text: '操作',
    sortable: false,
    xtype: 'actioncolumn',
    width: 50,
    items: [		               
				{
				            icon   : '${contextPath}/manager/images/add.png',  // Use a URL in the icon config
				            tooltip: '添加',
				            handler: function(grid, rowIndex, colIndex) {
				                var rec = notEnterForRaceTeamStore.getAt(rowIndex);
				                var teamId = rec.get('id');
				                var raceId = currRace.get('id');
				               				                		
			                	Ext.Ajax.request({
			                		url : '${contextPath}/game/team/addRaceTeam.do',
			                		params : {
			                			'raceId' : raceId,
			                			'teamId' : teamId				                			
			                		},
			                		success : function(r, o){
			                			//var strJson = r.responseText;
			                			//var obj = eval("(" + strJson + ")");
			                			//Ext.Msg.alert("删除成功", obj.message);
			                			//imageStore.reload();
			                			notEnterForRaceTeamStore.remove(rec);
			                			teamStore.proxy.extraParams.raceId = currRace.get('id');	
			        					teamStore.load();
			        					notEnterForRaceTeamStore.load();
			                		},
			                		failure : function(form, action){
			                			Ext.Msg.alert("用户", "操作失败！");
			                		}
			                	});

				            }
				        }
				    ]
				},
				{
      		    	text: '图片',
      			    width: 80,
      			    sortable: false,
      			    dataIndex: 'thumbnail',
      			    align: 'center',
      			    renderer : function(v) {
      			    	//return "<img height=60 with=60 src='${contextPath}" + v + "'/>";
      			    	return "<img height=60 with=60 src='/" + v + "'/>";
      			    }
      		    },
      		    {
      		    	text: '名称',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'teamName'
      		    },
      		    {	          	
      		    	text: '运动类型',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'sportName'
      	        },     
      	      	{	          	
      		    	text: '对战类型',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'warTypeName'
      	        },
      	      	{	          	
      		    	text: '运动类型',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'sportName'
      	        },
      	      	{	          	
      		    	text: '联系方式',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'contact'
      	        },
      	      	{	          	
      		    	text: '用户账号',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'userAccountName'
      	        }
		        
        ],
    	bbar : Ext.create('Ext.PagingToolbar', {
			store : notEnterForRaceTeamStore,
			displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}',
			emptyMsg : "无数据"
		})
    });
    
    var notEnterForRaceTeamWin = Ext.create('Ext.Window', {
    	id : 'notEnterForRaceTeamWin',
        title: '球队新增',            
        modal : true,
        closeAction: 'hide',
        width: 500,
        height: 400,
        layout : 'fit',
        items: [notEnterForRaceTeamGrid]
    });
    
    //------------------------球队管理
    
      var teambbar =  Ext.create('Ext.Toolbar', {
    	border : 0,
    	layout : {
    		type : 'table',
    		columns : 5
    	},   	
	    items: [	 {
	    	id : 'notEnterForRaceTeamName_1',
	    	xtype : 'textfield',
	    	width : 160,
	    	fieldLabel : '球队名称',
	    	labelWidth : 70
	    },
	    {	      
	    	id : 'notEnterForRaceSportTypeId_1',    
	    	xtype : 'combo', 
        	store : neSportTypeStore,
        	emptyText : '请选择',		            	  	
        	valueField : 'key',
        	displayField : 'value',              			               		                
	    	fieldLabel : '类型',				    	
	    	width : 160,
	    	labelWidth : 50,
	    	queryMode: 'local'
        },       
		    {
				text : '查询',
				handler : function() {
					teamStore.proxy.extraParams.raceTeamName = Ext.getCmp('notEnterForRaceTeamName_1').value;	
					teamStore.proxy.extraParams.sportType = Ext.getCmp('notEnterForRaceSportTypeId_1').value;
					teamStore.proxy.extraParams.raceId = currRace.get('id');	
					teamStore.load();
				}
		    },
		    {
				text : '添加球队',
				handler : function() {
					notEnterForRaceTeamWin.show();
					notEnterForRaceTeamStore.proxy.extraParams.raceId = currRace.get('id');				
					notEnterForRaceTeamStore.load();
					neSportTypeStore.reload();
				}
		    }
	    ]
	}); 

    
    var teamStore = Ext.create('Ext.data.ArrayStore', {
    	fields : ['id','teamName','thumbnail','warType', 'warTypeName','sportId','sportName','contact','orderId','userAccountId','userAccountName','maxNum','joinNum','createTime','lastUpdateTime','stat','nameandtime','warDesc'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/game/team/findRaceTeam.do'
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
    
    var teamgrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: teamStore,
        defaultType : 'textfield',     
        tbar : [      
			teambbar
        		],
        columns: [
				{
      		    	text: '图片',
      			    width: 80,
      			    sortable: false,
      			    dataIndex: 'thumbnail',
      			    align: 'center',
      			    renderer : function(v) {
      			    	//return "<img height=60 with=60 src='${contextPath}" + v + "'/>";
      			    	return "<img height=60 with=60 src='/" + v + "'/>";
      			    }
      		    },
      		    {
      		    	text: '名称',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'teamName'
      		    },
      		    {	          	
      		    	text: '运动类型',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'sportName'
      	        },     
      	      	{	          	
      		    	text: '对战类型',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'warTypeName'
      	        },   	      	
      	      	{	          	
      		    	text: '联系方式',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'contact'
      	        },
      	      	{	          	
      		    	text: '用户账号',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'userAccountName'
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
		                        var rec = teamStore.getAt(rowIndex);
		                        var teamId = rec.get('id');
				                var raceId = currRace.get('id'); 
		                        		                        		
	                        	Ext.Ajax.request({
	                        		url : '${contextPath}/game/team/deleteRaceTeam.do',
	                        		params : {
	                        			'raceId' : raceId,
			                			'teamId' : teamId
	                        		},
	                        		success : function(r, o){
	                        			teamStore.reload();
	                        			var strJson = r.responseText;
	                        			var obj = eval("(" + strJson + ")");	                        			                      			
	                        			Ext.Msg.alert("删除成功", obj.message);
	                        		},
	                        		failure : function(form, action){
	                        			Ext.Msg.alert("用户", "操作失败！");
	                        		}
	                        	});
		                    	
		                    }
		                }
		            ]
		     }
        ],
    	bbar : Ext.create('Ext.PagingToolbar', {
			store : teamStore,
			displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}',
			emptyMsg : "无数据"
		})
    });
	
    
    
    //------------------------图片资源
    
    var imagebbar =  Ext.create('Ext.Toolbar', {
    	border : 0,
    	layout : {
    		type : 'table',
    		columns : 5
    	},   	
	    items: [	       
		    {
		    	id : 'imageName',
		    	xtype : 'textfield',
		    	width : 160,
		    	fieldLabel : '图片名称',
		    	labelWidth : 70
		    },
		    {
				text : '查询',
				handler : function() {
					imageStore.proxy.extraParams.name = Ext.getCmp('imageName').value;				
					imageStore.load();
				}
		    },
		    {
				text : '新建',
				handler : function() {
					imageCreateWin.show();
				}
		    }
	    ]
	}); 

    
    var imagegrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: imageStore,
        defaultType : 'textfield',     
        selType: 'cellmodel',  
        plugins:[  
                 Ext.create('Ext.grid.plugin.CellEditing',{  
                     clicksToEdit:1 //设置单击单元格编辑  
                 })  
        ],
        tbar : [      
			imagebbar
    		],
        columns: [
				{
      		    	text: '图片',
      			    width: 100,
      			    sortable: false,
      			    dataIndex: 'url',
      			    align: 'center',
      			    renderer : function(v) {
      			    	//return "<img height=80 with=80 src='${contextPath}" + v + "'/>";
      			    	return "<img height=80 with=80 src='/" + v + "'/>";
      			    }
      		    },
      		    {
      		    	text: '名称',
      			    width: 80,
      			    sortable: false,
      			    dataIndex: 'name'
      		    },
      		    {	          	
      		    	text: 'url',
      			    width: 350,
      			    sortable: false,
      			    dataIndex: 'infactUrl',     			   
      			  	editor:{  
                      	allowBlank:true  
                  	}
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
		                        var rec = imageStore.getAt(rowIndex);
		                        var deleteId = rec.get('id');
		                        var title = rec.get('name');       
		                         var msg = "是否确定需要删除图片  " + title;
		                        Ext.Msg.confirm('提示', msg, function(ok) {
		                        	if('yes' != ok){
		                        		return;
		                        	} 
		                        		
		                        	Ext.Ajax.request({
		                        		url : '${contextPath}/common/images/delete.do',
		                        		params : {
		                        			'id' : deleteId
		                        		},
		                        		success : function(r, o){
		                        			var strJson = r.responseText;
		                        			var obj = eval("(" + strJson + ")");
		                        			Ext.Msg.alert("删除成功", obj.message);
		                        			imageStore.reload();
		                        		},
		                        		failure : function(form, action){
		                        			Ext.Msg.alert("用户", "操作失败！");
		                        		}
		                        	});
		                    	});
		                    }
		                }
		            ]
		     }
        ],
    	bbar : Ext.create('Ext.PagingToolbar', {
			store : imageStore,
			displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}',
			emptyMsg : "无数据"
		})
    });
    
    //---------- 赛事积分 ----------    
    
     var scoreTeamStore = Ext.create('Ext.data.ArrayStore', {
    	fields : ['id','teamName','thumbnail','warType', 'warTypeName','sportId','sportName','contact','orderId','userAccountId','userAccountName','maxNum','joinNum','createTime','lastUpdateTime','stat','nameandtime','warDesc'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/game/team/findRaceTeamNotInScoreboard.do'
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
    
    var scoreTeamGrid = Ext.create('Ext.grid.GridPanel', {	              	
        store: scoreTeamStore,
        defaultType : 'textfield',     
        columns: [

				{
					text: '操作',
				    sortable: false,
				    xtype: 'actioncolumn',
				    width: 60,
				    items: [		               
				        {
				            icon   : '${contextPath}/manager/images/add.png',  // Use a URL in the icon config
				            tooltip: '新增',
				            handler: function(grid, rowIndex, colIndex) {
				                var rec = scoreTeamStore.getAt(rowIndex);
				                var teamId = rec.get('id');
				                var raceId = currRace.get('id'); 
				                		                        		
				            	Ext.Ajax.request({
				            		url : '${contextPath}/game/team/addRaceScoreboard.do',
				            		params : {
				            			'raceId' : raceId,
				            			'teamId' : teamId
				            		},
				            		success : function(r, o){
				            			var strJson = r.responseText;
				            			var obj = eval("(" + strJson + ")");
				            			//Ext.Msg.alert("添加成功", obj.message);
				            			scoreTeamStore.proxy.extraParams.raceId = currRace.get('id');	
				            			scoreTeamStore.reload();
				            			
				            			scroeStore.proxy.extraParams.raceId = currRace.get('id');	
				                    	scroeStore.reload();
				            		},
				            		failure : function(form, action){
				            			Ext.Msg.alert("用户", "操作失败！");
				            		}
				            	});
				            	
				            }
				        }
				    ]
				},
				{
      		    	text: '图片',
      			    width: 80,
      			    sortable: false,
      			    dataIndex: 'thumbnail',
      			    align: 'center',
      			    renderer : function(v) {
      			    	//return "<img height=60 with=60 src='${contextPath}" + v + "'/>";
      			    	return "<img height=60 with=60 src='/" + v + "'/>";
      			    }
      		    },
      		    {
      		    	text: '名称',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'teamName'
      		    },
      		    {	          	
      		    	text: '运动类型',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'sportName'
      	        },     
      	      	{	          	
      		    	text: '对战类型',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'warTypeName'
      	        },   	      	
      	      	{	          	
      		    	text: '联系方式',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'contact'
      	        },
      	      	{	          	
      		    	text: '用户账号',
      			    width: 100,
      			    sortable: true,
      			    dataIndex: 'userAccountName'
      	        }
        ],
    	bbar : Ext.create('Ext.PagingToolbar', {
			store : scoreTeamStore,
			displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}',
			emptyMsg : "无数据"
		})
    });
    
    var scoreTeamCreateWin = Ext.create('Ext.Window', {
    	id : 'scoreTeamCreateWin',
        title: '赛事新增',            
        modal : true,
        closeAction: 'hide',
        width: 600,
        height: 500,
        layout : 'fit',
        items: [scoreTeamGrid]
    });
    
    //----------------------------------------
    
    Ext.define('RaceScoreboard', {
	    extend: 'Ext.data.Model',
	    fields: ['id','teamId','teamName','raceInfoId','raceInfoTitle','teamGroup','won','drawn','lost','goalsScored','goalsAgainst','goalsDifference','points','createTime','lastUpdateTime','stat']
	});
    
    var groupStore = Ext.create('Ext.data.Store', {
    	//outoLoad : true,
        fields : ['id', 'menuGroupName'],      
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/menu-group/findAll.do'
            },
            reader : {
                type : 'json',
                root : 'results',
                successProperty : 'success'
            },
            timeout : 180000
        }
    });
    
    var scroeStore = Ext.create('Ext.data.ArrayStore', {
        fields : ['id','teamId','teamName','raceInfoId','raceInfoTitle','teamGroup','won','drawn','lost','goalsScored','goalsAgainst','goalsDifference','points','createTime','lastUpdateTime','stat'],      
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/game/team/findRaceScroeboard.do'
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
    
    var scroeRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    	saveBtnText: '保存',
        cancelBtnText: "取消", 
    	clicksToEdit: 2,
    	errorSummary : false,
        listeners: {
            cancelEdit: function(rowEditing, context) {
                // Canceling editing of a locally added, unsaved record: remove it
                if (context.record.phantom) {
                	scroeStore.remove(context.record);
                }
            },
            edit:function(rowEditing, context) {
				var rec = context.record;
				'id','teamId','teamName','raceInfoId','raceInfoTitle','teamGroup','won','drawn','lost','goalsScored','goalsAgainst','goalsDifference','points','createTime','lastUpdateTime','stat'
				Ext.Ajax.request({
            		url : '${contextPath}/game/team/updateRaceScoreboard.do',
            		params : {
            			'id' : rec.get('id'),
            			'teamId' : rec.get('teamId'),
            			'teamName' : rec.get('teamName'),
            			'raceInfoId' : rec.get('raceInfoId'),
            			'raceInfoTitle' : rec.get('raceInfoTitle'),
            			'teamGroup' : rec.get('teamGroup'),
            			'won' : rec.get('won'),
            			'drawn' : rec.get('drawn'),
            			'lost' : rec.get('lost'),
            			'goalsScored' : rec.get('goalsScored'),
            			'goalsAgainst' : rec.get('goalsAgainst'),
            			'goalsDifference' : rec.get('goalsDifference'),
            			'points' : rec.get('points'),
            			'stat' : rec.get('stat')         			
            		},
            		success : function(r, o){
            			var strJson = r.responseText;
            			var obj = eval("(" + strJson + ")");
            			Ext.Msg.alert("信息", obj.message);      
            			//scroeStore.reload();
            		},
            		failure : function(form, action){
            			Ext.Msg.alert("信息", "保存失败！");
            		}
            	});
            }
        }
    });
    
    var scroeGrid = Ext.create('Ext.grid.Panel', {	       
        plugins: [scroeRowEditing],	      
        frame: true,       
        store: scroeStore,
        selType: 'rowmodel',
        defaultType : 'textfield',
        columns: [
                  {
  		        	text: '删除', 		         
  		            sortable: false,
  		            xtype: 'actioncolumn',
  		            width: 50,
  		            items: [
  		                {
  		                    icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
  		                    tooltip: '删除用户',
  		                    handler : function(grid, rowIndex, colIndex) {
  		                    	var rec = scroeStore.getAt(rowIndex);      		                   	
  		                    	
  		                    	Ext.Ajax.request({
	  		                   		url : '${contextPath}/game/team/deleteRaceScoreboard.do',
	  		                   		params : {
	  		                   			'id' : rec.get('id')		
	  		                   		},
	  		                   		success : function(r, o){
	  		                   			var strJson = r.responseText;
	  		                   			var obj = eval("(" + strJson + ")");
	  		                   			Ext.Msg.alert("信息", obj.message);      
	  		                   			scroeStore.remove(rec);
	  		                   		},
	  		                   		failure : function(form, action){
	  		                   			Ext.Msg.alert("信息", "删除失败！");
	  		                   		}
  		                   		});
  							}
  		                }
  		            ]
  		     },          
        {
            text: '球队名称',
            width: 150,
            sortable: true,
            dataIndex: 'teamName'
        }, {
            text: '分组',
            width: 100,
            sortable: false,
            dataIndex: 'teamGroup',
            field: {
                xtype: 'textfield',
                allowBlank: false
            }
        }, {
            text: '胜',
            width: 50,
            sortable: false,
            dataIndex: 'won',
            field: {
                xtype: 'textfield'
            }
        }, {
            text: '平',
            width: 50,
            sortable: false,
            dataIndex: 'drawn',
            field: {
                xtype: 'textfield'
            }
        }, {
            text: '负',
            width: 50,
            sortable: false,
            dataIndex: 'lost',
            field: {
                xtype: 'textfield'
            }
        }, {
            text: '进球',
            width: 50,
            sortable: false,
            dataIndex: 'goalsScored',
            field: {
                xtype: 'textfield'
            }
        }, {
            text: '失球',
            width: 50,
            sortable: false,
            dataIndex: 'goalsAgainst',
            field: {
                xtype: 'textfield'
            }
        }, {
            text: '净胜球',
            width: 50,
            sortable: false,
            dataIndex: 'goalsDifference',
            field: {
                xtype: 'textfield'
            }
        }, {
            text: '积分',
            width: 50,
            sortable: false,
            dataIndex: 'points',
            field: {
                xtype: 'textfield'
            }
        }],
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '新增',
                buttonAlign  : 'left',
                handler: function(){
                	//var record = new RaceScoreboard();                	
                	//scroeStore.insert(0, record);
                	//scroeRowEditing.startEdit(0, 0);
                	
                	scoreTeamCreateWin.show();
                	scoreTeamStore.proxy.extraParams.raceId = currRace.get('id');	
                	scoreTeamStore.load();
                }
            },"-",{
                text: '刷新',
                handler: function(){
                	scroeStore.proxy.extraParams.raceId = currRace.get('id');	
                	scroeStore.load();
                }
            }
            
            ]
        }]
    });
    
    scroeGrid.getSelectionModel().on('selectionchange', function(selModel, selections){
    	//scroeGrid.down('#delete').setDisabled(selections.length === 0);
    });
    
    var scroeDeleteMenu = function(selection){
    	 if (selection) {
        	 
         	Ext.Ajax.request({
         		url : '${contextPath}/menu/deleteMenu.do',
         		params : {
         			'menuId' : selection.get('id')		
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
    
    //--------- 信息修改window ----------
    
    var raceUpdateForm = Ext.create('Ext.form.Panel', {	
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
	            	id : 'raceUpdateBaseInfo',
	                title : '基本信息',
	                xtype : 'form',
	                fileUpload : true,
	                defaultType : 'textfield',
	                buttonAlign  : 'left',
	                url : '${contextPath}/game/race/update.do',	
	                items: [
	                 {
	                	xtype: 'filefield',			           
			            emptyText: 'Select an image',
			            fieldLabel: '赛事标志',
			            name: 'picImage',
			            buttonText: '请选择',
			            anchor:'50%',
			            buttonConfig: {
			                iconCls: 'upload-icon'
			            }
			        },{	    			
		    			hidden : true,
		    			anchor:'50%',
		    			name : 'id'
		    		}, {
	                    fieldLabel: '名称',
	                    anchor:'50%',
	                    name: 'title',
	                    maxLength : 30
	                }, {
	                    fieldLabel: '承办方',
	                    anchor:'50%',
	                    name: 'organizers',
	                    maxLength : 30
	                }, {	      	    		 
	    		    	xtype : 'combo', 
	    		    	anchor:'50%',
	                	store : pSportTypeStore,
	                	emptyText : '请选择',		            	  	
	                	valueField : 'key',
	                	displayField : 'value',              			               		                
	    		    	fieldLabel : '类型',				    	
	    		    	queryMode: 'local',
	    		    	name : 'sportTypeId'
	    	        }],
	                buttons : [{
						text : '修改',
						handler : function() {
							var form = this.up('panel').getForm();
							if(!form.isValid()){
								return;
							}
							
							form.submit({														
								success : function() {
									Ext.Msg.alert("信息", "更新成功!");
									orderStore.reload();								
								},
								failure : function() {
									Ext.Msg.alert("信息", "更新失败!");
								},
								waitMsg : 'plz waiting...'
							});			
						}
					},
					{
						text : '取消',
						handler : function() {
							raceUpdateWin.hide();
						}
					}]
	            }, {
	                title:'球队信息',
	                layout : 'fit',
	                items: [
						teamgrid
	                ]
	            },
	            {   
	            	id : 'raceUpdateContextForm',
	                title: '赛事描述',
	                buttonAlign  : 'left',
	              	layout : "border",
	                xtype :'form',
	                name : 'context',
	                defaultType : 'textfield',
	                url : '${contextPath}/game/race/updateContext.do',	
	                items: [
	                     
					{	      	    		 
						xtype : 'combo', 				
						store : infoTypeStore,
						emptyText : '请选择',		            	  	
						valueField : 'key',
						//上  右  下 左
						padding :'0 0 0 10',
						displayField : 'value',              			               		                
						fieldLabel : '类型',				    	
						queryMode: 'local',
						name : 'infoType',
						region : "north"
					},	       
	                {
	                	id : 'contextHtmleditor',
	                    xtype: 'htmleditor',
	                    border: true,
	                    name: 'context',
	                    region : "center", 
	                    plugins: [
	                         Ext.create('Ext.zc.form.HtmlEditorImage')
	                    ]
	                },{	    			
		    			hidden : true,
		    			name : 'id'
		    		}],
	                buttons : [{
						text : '保存',
						handler : function() {
							var form = this.up('panel').getForm();
							
							if(!form.isValid()){
								return;
							}
							
							form.submit({														
								success : function() {
									Ext.Msg.alert("信息", "更新成功!");
									orderStore.reload();								
								},
								failure : function() {
									Ext.Msg.alert("信息", "更新失败!");
								},
								waitMsg : 'plz waiting...'
							});			
						}
					}]
	            },
	            {
	                title:'图片资源',
	                layout : 'fit',
	                items: [
						imagegrid
	                ]
	            },
	            {
	                title:'赛事积分',
	                layout : 'fit',
	                items: [
						scroeGrid
	                ]
	            }]
	        }		
    	]
    });
    
    var raceUpdateWin = Ext.create('Ext.Window', {
        title: '赛事修改',            
        modal : true,
        closeAction: 'hide',
        width: 800,
        height: 450,
        layout : 'fit',
        items: [raceUpdateForm]
    });
    
    //----------信息新增
    
     var raceCreateForm = Ext.create('Ext.form.Panel', {	
    	bodyPadding: 5,
    	defaultType : 'textfield',
    	border : false,	
        layout : 'form', 
        url : '${contextPath}/game/race/create.do',	
    	items : [
   	         {
               	 xtype: 'filefield',	            
	            emptyText: 'Select an image',
	            fieldLabel: '赛事标志',
	            name: 'picImage',
	            buttonText: '请选择',
	            anchor:'95%',
	            buttonConfig: {
	                iconCls: 'upload-icon'
	            }
	        }, {
                   fieldLabel: '名称',
                   name: 'title',
                   anchor:'95%',
                   maxLength : 30
               }, {
                   fieldLabel: '承办方',
                   name: 'organizers',
                   anchor:'95%',
                   maxLength : 30
               }, {	      	    		 
   		    	xtype : 'combo', 
               	store : cSportTypeStore,
               	emptyText : '请选择',		            	  	
               	valueField : 'key',
               	displayField : 'value',              			               		                
   		    	fieldLabel : '类型',				    	
   		    	queryMode: 'local',
   		    	name : 'sportTypeId',
   		    	anchor:'95%'
   	        }
    	],
    	buttons : [{
			text : '保存',
			handler : function() {
				var form = this.up('panel').getForm();
				if(!form.isValid()){
					return;
				}
				
				form.submit({														
					success : function() {
						Ext.Msg.alert("信息", "新增成功!");
						raceCreateWin.hide();
						form.reset();
						orderStore.reload();
					},
					failure : function() {
						Ext.Msg.alert("信息", "更新失败!");
					},
					waitMsg : 'plz waiting...'
				});			
			}
		}]
    });
    
    var raceCreateWin = Ext.create('Ext.Window', {
    	id : 'raceCreateWin',
        title: '赛事新增',            
        modal : true,
        closeAction: 'hide',
        width: 400,
        height: 300,
        layout : 'fit',
        items: [raceCreateForm]
    });
    
    
  	//实现修改框的数据填充
    var loadRaceInfo = function(rec){
  		var form = Ext.getCmp('raceUpdateBaseInfo');
  		var cform = Ext.getCmp('raceUpdateContextForm');
  		form.loadRecord(rec);
  		cform.loadRecord(rec);
  		var context = rec.get('context');
  		Ext.getCmp('contextHtmleditor').setValue(context);
    	
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
      		    	text: '标志',
      			    width: 140,
      			    sortable: false,
      			    dataIndex: 'pic',
      			    align: 'center',
      			    renderer : function(v) {
      			    	//return "<img height=100 with=100 src='${contextPath}/" + v + "'/>";
      			    	return "<img height=100 with=100 src='/" + v + "'/>";
      			    }
      		    },
      		    {
      		    	text: '类型',
      			    width: 110,
      			    sortable: true,
      			    dataIndex: 'sportName'
      		    },
      		    {	          	
      		    	text: '名称',
      			    width: 150,
      			    sortable: true,
      			    dataIndex: 'title'
      	        },
      	        {	            		    	
      		    	text: '承办方',
      			    width: 150,
      			    sortable: true,
      			    dataIndex: 'organizers'
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
		                    	var rec = orderStore.getAt(rowIndex);       
		                    	currRace = rec;
		                    	raceUpdateWin.show();	
		                    	var form = Ext.getCmp('raceUpdateBaseInfo').getForm();
		                  		var cform = Ext.getCmp('raceUpdateContextForm').getForm();
		                  		form.reset( );
		                  		cform.reset();
		                    	loadRaceInfo(rec);
		                    	
		                    	teamStore.proxy.extraParams.raceId=rec.get('id');							
		                    	teamStore.load();
		                    	
		                    	scroeStore.proxy.extraParams.raceId = rec.get('id');	
		                    	scroeStore.load();
							}
		                },
		                {
		                    icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
		                    tooltip: '删除',
		                    handler: function(grid, rowIndex, colIndex) {
		                        var rec = orderStore.getAt(rowIndex);
		                        var deleteId = rec.get('id');
		                        var title = rec.get('title');       
		                         var msg = "是否确定需要删除赛事 " + title;
		                        Ext.Msg.confirm('提示', msg, function(ok) {
		                        	if('yes' != ok){
		                        		return;
		                        	} 
		                        		
		                        	Ext.Ajax.request({
		                        		url : '${contextPath}/game/race/delete.do',
		                        		params : {
		                        			'id' : deleteId
		                        		},
		                        		success : function(r, o){
		                        			var strJson = r.responseText;
		                        			var obj = eval("(" + strJson + ")");
		                        			Ext.Msg.alert("删除成功", obj.message);
		                        			orderStore.reload();
		                        		},
		                        		failure : function(form, action){
		                        			Ext.Msg.alert("用户", "操作失败！");
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
    
    infoTypeStore.load();
    bSportTypeStore.load();
    pSportTypeStore.load();
    cSportTypeStore.load();
    neSportTypeStore.load();
});

</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>