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
    
    //-------------------------------------
       
    var sportTypeStore = Ext.create('App.store.common.SportTypeStore');
    var pSportTypeStore = Ext.create('App.store.common.SportTypeStore');
    
    var warTypeStore = Ext.create('App.store.common.WarTypeStore');
    
    var teamPositionStore = Ext.create('App.store.common.TeamPositionStore'); 
    
    var currTeam = null;
    
    //-------------------------------------
    
    var teamStore = Ext.create('Ext.data.ArrayStore', {
    	fields : ['id','teamName','thumbnail','warType', 'warTypeName','sportId','sportName','contact','orderId','userAccountId','userAccountName',
    	          'maxNum','joinNum','createTime','lastUpdateTime','stat','nameandtime','warDesc'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/user/team/find.do'
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
			    	id : 'teamName',
			    	xtype : 'textfield',
			    	width : 160,
			    	fieldLabel : '球队名称',
			    	labelWidth : 70
			    },
			    {	      
			    	id : 'sportTypeId',    
			    	xtype : 'combo', 
	            	store : sportTypeStore,
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
						teamStore.proxy.extraParams.teamName = Ext.getCmp('teamName').value;				
						teamStore.proxy.extraParams.sportType = Ext.getCmp('sportTypeId').value;						
						teamStore.load();
					}
			    }
		       ,
			   {
					text : '新增',
					handler : function() {
						teamCreateWin.show();
						pSportTypeStore.reload();
						warTypeStore.reload();
					}
			    }
	    ]
	}); 
    
    //--------- 球队新增 ----------
    
     var teamCreateForm = Ext.create('Ext.form.Panel', {	
    	bodyPadding: 5,
    	defaultType : 'textfield',
    	border : false,	
        layout : 'form', 
        url : '${contextPath}/user/team/create.do',	
    	items : [
   	         {
               	xtype: 'filefield',	            
	            emptyText: 'Select an image',
	            fieldLabel: '球队图标',
	            name: 'picImage',
	            buttonText: '请选择图片...',
	            anchor:'90%',
	            labelWidth : 60,
	            buttonConfig: {
	                iconCls: 'upload-icon'
	            },
	            validator: function(value){
	            	if(null == value || '' == value) {
	            		return true;
	            	}
	                var arr = value.split('.');
	                var type = arr[arr.length-1];
	                if(type != 'png' && type != 'jpg' && type != 'jpeg' && type != 'bmp' && type != 'gif'){
	                  return '文件不合法！！！';
	                }else{
	                  return true;
	                }
	            }
	        }, {
                   fieldLabel: '名称',
                   name: 'teamName',
                   anchor:'90%',
   	               labelWidth : 60,
   	               maxLength : 30,
   	               allowBlank : false
               },
			    {	      
			    	xtype : 'combo', 
			    	name : 'sportId',
	            	store : pSportTypeStore,
	            	emptyText : '请选择',		            	  	
	            	valueField : 'key',
	            	displayField : 'value',              			               		                
			    	fieldLabel : '运动类型',				    	
			    	anchor:'90%',
			        labelWidth : 60,
			    	queryMode: 'local',
			    	editable : false,
			    	allowBlank : false
		        },
			    {	      
			    	xtype : 'combo', 
			    	name : 'warType',
	            	store : warTypeStore,
	            	emptyText : '请选择',		            	  	
	            	valueField : 'key',
	            	displayField : 'value',              			               		                
			    	fieldLabel : '对战类型',				    	
			    	anchor:'90%',
			        labelWidth : 60,
			    	queryMode: 'local',
			    	editable : false,
			    	allowBlank : false
		        }, {
	                   fieldLabel: '联系方式',
	                   name: 'contact',
	                   anchor:'90%',
	   	               labelWidth : 60,
	   	               regex : /(^1[3|4|5|8][0-9][0-9]{8}$)|(^(0[0-9]{2,3}-)?[0-9]{5,9}$)/, 
	   	               regexText:"错误的电话格式!"
	               }, {
	                   fieldLabel: '用户帐号',
	                   name: 'userAccountName',
	                   anchor:'90%',
	   	               labelWidth : 60
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
						teamCreateWin.hide();
						form.reset();
						teamStore.reload();
					},
					failure : function() {
						Ext.Msg.alert("信息", "更新失败!");
					},
					waitMsg : 'plz waiting...'
				});			
			}
		}]
    });
    
    var teamCreateWin = Ext.create('Ext.Window', {
    	id : 'teamCreateWin',
        title: '球队新增',            
        modal : true,
        closeAction: 'hide',
        width: 400,
        height: 300,
        layout : 'fit',
        items: [teamCreateForm]
    });
    
    //----------------------- 球队更改
    
     var teamUpdateForm = Ext.create('Ext.form.Panel', {	
    	bodyPadding: 5,
    	defaultType : 'textfield',
    	border : false,	
        layout : 'form', 
        url : '${contextPath}/user/team/update.do',	
    	items : [
			{
			    fieldLabel: 'id',
			    name: 'id',
			    anchor:'90%',
			    labelWidth : 60,
			    hidden : true
			},
   	         {
               	xtype: 'filefield',	            
	            emptyText: 'Select an image',
	            fieldLabel: '球队图标',
	            name: 'picImage',
	            buttonText: '请选择',
	            anchor:'90%',
	            labelWidth : 60,
	            buttonConfig: {
	                iconCls: 'upload-icon'
	            },
	            validator: function(value){
	            	if(null == value || '' == value) {
	            		return true;
	            	}
	                var arr = value.split('.');
	                var type = arr[arr.length-1];
	                if(type != 'png' && type != 'jpg' && type != 'jpeg' && type != 'bmp' && type != 'gif'){
	                  return '文件不合法！！！';
	                }else{
	                  return true;
	                }
	            }
	        }, {
                   fieldLabel: '名称',
                   name: 'teamName',
                   anchor:'90%',
   	               labelWidth : 60,
   	               allowBlank : false
               },
			    {	      
			    	xtype : 'combo', 
			    	name : 'sportId',
	            	store : pSportTypeStore,
	            	emptyText : '请选择',		            	  	
	            	valueField : 'key',
	            	displayField : 'value',              			               		                
			    	fieldLabel : '运动类型',				    	
			    	anchor:'90%',
			        labelWidth : 60,
			    	queryMode: 'local',
			    	editable : false,
			    	allowBlank : false
		        },
			    {	      
			    	xtype : 'combo', 
			    	name : 'warType',
	            	store : warTypeStore,
	            	emptyText : '请选择',		            	  	
	            	valueField : 'key',
	            	displayField : 'value',              			               		                
			    	fieldLabel : '对战类型',				    	
			    	anchor:'90%',
			        labelWidth : 60,
			    	queryMode: 'local',
			    	editable : false,
			    	allowBlank : false
		        }, {
	                   fieldLabel: '联系方式',
	                   name: 'contact',
	                   anchor:'90%',
	   	               labelWidth : 60,
	   	               regex : /(^1[3|4|5|8][0-9][0-9]{8}$)|(^(0[0-9]{2,3}-)?[0-9]{5,9}$)/, 
	   	               regexText:"错误的电话格式!"
	            }, {
	                   fieldLabel: '用户帐号',
	                   name: 'userAccountName',
	                   anchor:'90%',
	                   disabled:true,
	                   labelWidth : 60
	            }, {
	            	   xtype: "textarea",
	                   fieldLabel: '简介',
	                   name: 'warDesc',
	                   anchor:'90%',
	                   labelWidth : 60
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
						Ext.Msg.alert("信息", "更新成功!");
						teamUpdateWin.hide();
						form.reset();
						teamStore.reload();
					},
					failure : function() {
						Ext.Msg.alert("信息", "更新失败!");
					},
					waitMsg : 'plz waiting...'
				});			
			}
		}]
    });
    
    var teamUpdateWin = Ext.create('Ext.Window', {
    	id : 'teamUpdateWin',
        title: '球队修改',            
        modal : true,
        closeAction: 'hide',
        width: 400,
        height: 320,
        layout : 'fit',
        items: [teamUpdateForm]
    });
    
    //------------------------成员管理
    
    var memberbbar =  Ext.create('Ext.Toolbar', {
  	border : 0,
  	layout : {
  		type : 'table',
  		columns : 5
  	},   	
	    items: [	       
		    {
				text : '刷新',
				handler : function() {
					memberStore.proxy.extraParams.teamId = currTeam.get('id');	
					memberStore.load();
				}
		    },
		    {
				text : '添加成员',
				handler : function() {					
					//userModifyWin.show();
					userWin.show();
					userStore.proxy.extraParams.teamId=currTeam.get('id');				
					userStore.load();
					
				}
		    }
	    ]
	}); 

  
  var memberStore = Ext.create('Ext.data.ArrayStore', {
  	fields : ['id', 'pic', 'teamId', 'memberNum', 'chineseName', 'englishName', 'nickName', 'dateOfBorn', 'gender', 'eihnic','nativePlace', 'company', 'provinceId', 'provinceName', 'cityId', 'cityName',
  	        'countryId', 'countryName', 'height', 'weight', 'telephone', 'mobile', 'email', 'documentNo', 'position', 'stat', 'createTime', 'teamPosition', 'userAccount', 'teamPositionName'],
     	pageSize : 20,
      proxy : {
          type : 'ajax',
          api : {
              read : '${contextPath}/user/team-member/findMemberByTeamId.do'
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
  
  var membergrid = Ext.create('Ext.grid.GridPanel', {	              	
      store: memberStore,
      defaultType : 'textfield',     
      tbar : [      
			memberbbar
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
				                var rec = memberStore.getAt(rowIndex);
				                var memberId = rec.get('id');
				                //var teamId = currTeam.get('id'); 
				                var chineseName =  rec.get('chineseName');
				                if(null == chineseName || '' == chineseName) {
				                	chineseName = '此用户';
				                }
				                
				                var msg = "是否确定需要删除 " + chineseName;
		                        Ext.Msg.confirm('提示', msg, function(ok) {
		                        	if('yes' != ok){
		                        		return;
		                        	} 		                        		
					            	Ext.Ajax.request({
					            		url : '${contextPath}/user/team-member/deleteTeamMember.do',
					            		params : {
					            			'memberId' : memberId
					            		},
					            		success : function(r, o){
					            			var strJson = r.responseText;
					            			var obj = eval("(" + strJson + ")");
					            			Ext.Msg.alert("删除成功", obj.message);
					            			memberStore.remove(rec);
					            		},
					            		failure : function(form, action){
					            			Ext.Msg.alert("用户", "操作失败！");
					            		}
					            	});
		                        });
				            }
				        },
				        {
				            icon   : '${contextPath}/manager/images/modify.png',  // Use a URL in the icon config
				            tooltip: '修改',
				            handler: function(grid, rowIndex, colIndex) {
				                var rec = memberStore.getAt(rowIndex);
				                userModifyWin.show();
				                userModifyForm.loadRecord(rec);
				            }
				        }
				    ]
				},
				{
    		    	text: '头像',
    			    width: 80,
    			    sortable: false,
    			    dataIndex: 'pic',
    			    align: 'center',
    			    renderer : function(v) {
    			    	//return "<img height=60 with=60 src='${contextPath}" + v + "'/>";
    			    	return "<img height=60 with=60 src='/" + v + "'/>";
    			    }
    		    },
    		    {	          	
    		    	text: '用户账号',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'userAccount'
    	        },
    	        {	          	
    		    	text: '姓名',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'chineseName'
    	        },     
    		    {
    		    	text: '号码',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'memberNum'
    		    },   		   
    	        {	          	
    		    	text: '位置',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'position'
    	        },
    	        {	          	
    		    	text: '职位',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'teamPositionName'
    	        },
    	      	{	          	
    		    	text: '英文名',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'englishName'
    	        },   	      	
    	      	{	          	
    		    	text: '生日',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'dateOfBorn'
    	        },    	 
    	      	{	          	
    		    	text: '昵称',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'nickName'
    	        },
    	        {	          	
    		    	text: '用户账号',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'gender'
    	        },
    	        {	          	
    		    	text: '用户账号',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'eihnic'
    	        },
    	      
    	        {	          	
    		    	text: '用户账号',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'nativePlace'
    	        },
    	        {	          	
    		    	text: '单位',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'company'
    	        },
    	        {	          	
    		    	text: '省',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'provinceName'
    	        },
    	        {	          	
    		    	text: '市',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'cityName'
    	        },
    	        {	          	
    		    	text: '区',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'countryName'
    	        },
    	        {	          	
    		    	text: '身高',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'height'
    	        },
    	        {	          	
    		    	text: '体重',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'weight'
    	        },
    	        {	          	
    		    	text: '座机号',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'telephone'
    	        },
    	        {	          	
    		    	text: '手机号',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'mobile'
    	        },
    	        {	          	
    		    	text: '邮箱',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'email'
    	        },
    	        {	          	
    		    	text: '证件号',
    			    width: 100,
    			    sortable: true,
    			    dataIndex: 'documentNo'
    	        }    	     	      
		        
      ],
  	bbar : Ext.create('Ext.PagingToolbar', {
			store : memberStore,
			displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}',
			emptyMsg : "无数据"
		})
  });
	
	
  var memberWin = Ext.create('Ext.Window', {
      title: '球队编辑',            
      modal : true,
      closeAction: 'hide',
      width: 700,
      height: 450,
      layout : 'fit',
      items: [membergrid]
  });
    
    //--------- 信息修改window ----------
        
    var userModifyForm = Ext.create('Ext.form.Panel', {	
    	bodyPadding: 5,
    	defaultType : 'textfield',
    	border : false,	
        layout : 'form', 
        url : '${contextPath}/user/team-member/updateTeamMember.do',	
    	items : [
			{	    			
				hidden : true,
				anchor:'95%',
				name : 'id'
			},
   	         {
               	xtype: 'filefield',	        
	            emptyText: 'Select an image',
	            fieldLabel: '图片',
	            name: 'picImage',
	            buttonText: '请选择',
	            anchor:'95%',
	            buttonConfig: {
	                iconCls: 'upload-icon'
	            },
	            validator: function(value){
	            	if(null == value || '' == value) {
	            		return true;
	            	}
	            	var arr = value.split('.');
	            	var type = arr[arr.length-1];
	            	if(type != 'png' && type != 'jpg' && type != 'jpeg' && type != 'bmp' && type != 'gif'){
	            	  return '文件不合法！！！';
	            	}else{
	            	  return true;
	            	}
	            }
	        }, {	      
		    	xtype : 'combo', 
		    	name : 'teamPosition',
            	store : teamPositionStore,
            	emptyText : '请选择',		            	  	
            	valueField : 'key',
            	displayField : 'value',              			               		                
		    	fieldLabel : '球队职位',				    	
		    	anchor:'95%',			     
		    	queryMode: 'local',
		    	editable : false,
		    	allowBlank : false
	        }, {
                fieldLabel: '位置',
                name: 'position',
                anchor:'95%'
            }, {
                fieldLabel: '号码',
                name: 'memberNum',
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
						userModifyWin.hide();
						memberStore.reload();
					},
					failure : function() {
						Ext.Msg.alert("信息", "更新失败!");
					},
					waitMsg : 'plz waiting...'
				});			
			}
		}]
    });
    
    var userModifyWin = Ext.create('Ext.Window', {
    	id : 'accountWin',
        title: '用户信息修改',            
        modal : true,
        closeAction: 'hide',
        width: 350,
        height: 250,
        layout : 'fit',
        items: [userModifyForm]
    });
    
    //------------
    
     var userStore = Ext.create('Ext.data.ArrayStore', {
    	fields : ['id','userAccountId','userAccount','realName','job','jobName', 'provinceid', 'provinceName', 'cityid', 'cityName', 'countryid', 'countryName',
    	          'address','email','mobileNo','age', 'userImage','brithday','nickName','chargeType','venueType', 'venueTypeNames', 'userLevel','userScore','createTime','stat'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            api : {
                read : '${contextPath}/user/team-member/findUserNotInTeam.do'
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
    
    var userbbar =  Ext.create('Ext.Toolbar', {
    	border : 0,
    	layout : {
    		type : 'table',
    		columns : 3
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
		    	fieldLabel : '真实姓名',
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
					userStore.proxy.extraParams.teamId=currTeam.get('id');
					userStore.proxy.extraParams.userAccount=Ext.getCmp('userAccount').value;
					userStore.proxy.extraParams.realName=Ext.getCmp('realName').value;
					userStore.proxy.extraParams.provinceId=Ext.getCmp('province').value;
					userStore.proxy.extraParams.cityId=Ext.getCmp('city').value;
					userStore.proxy.extraParams.countryId=Ext.getCmp('districts').value;
					//新增参数stat_01    增加查询条件users.stat =1 and account.user_account is not null
					//userStore.proxy.extraParams.stat_info='stat_01';
					userStore.load();
				}
		    } 
	    ]
	}); 

    var userGrid = Ext.create('Ext.grid.GridPanel', {	          
        store: userStore,
        defaultType : 'textfield',
        tbar : [      
			userbbar
    		],
        columns: [       
		        {
		        	text: '操作',		       
		            sortable: false,
		            xtype: 'actioncolumn',
		            width: 60,
		            items: [
		
		                {
		                    icon   : '${contextPath}/manager/images/add.png',  // Use a URL in the icon config
		                    tooltip: '添加',
		                    handler: function(grid, rowIndex, colIndex) {
		                        var rec = userStore.getAt(rowIndex);
		                      	var userId = rec.get('id');
		                      	var teamId = currTeam.get('id');
	                        	Ext.Ajax.request({
	                        		url : '${contextPath}/user/team-member/addUserJoinTeam.do',
	                        		params : {
	                        			'userId' : userId,
	                        			'teamId' : teamId
	                        		},
	                        		success : function(r, o){
	                        			var strJson = r.responseText;
	                        			var obj = eval("(" + strJson + ")");	                        		
	                        			if(obj.success == true){
	                        				userStore.remove(rec);
	                        				memberStore.proxy.extraParams.teamId = currTeam.get('id');	
	                    					memberStore.load();
	                    					userStore.load();
	                        			}
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
      		    	text: '用户帐号',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'userAccount'
      		    },
      		    {
      		    	text: '真实姓名',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'realName'
      		    },
      		    {	          	
      		    	text: '职业',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'jobName'
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
      		    	text: '年龄',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'age'
      	        },
      	        {	           		    	
      		    	text: '手机号码',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'mobileNo'
      	        },
      	        {	           		    	
      		    	text: '邮箱',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'email'
      	        },
      	        {	           		    	
      		    	text: '出生年月',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'brithday'
      	        },
      	        {	            		    	
      		    	text: '昵称',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'nickName'
      	        },
      	      {	            		    	
      		    	text: '收费类型',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'chargeType'
      	      },
      	      {	           		    	
      		    	text: '场馆类型',
      			    width: 150,
      			    sortable: true,
      			    dataIndex: 'venueTypeNames'
      	      },
      	      {	           		    	
      		    	text: '用户等级',
      			    width: 80,
      			    sortable: true,
      			    dataIndex: 'userLevel'
      	      },
      	    {	           		    	
    		    	text: '用户积分',
    			    width: 80,
    			    sortable: true,
    			    dataIndex: 'userScore'
    	      },
		        {
		            text: '创建时间',
		            width: 80,
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
			store : userStore,
			displayInfo : true,
			displayMsg : '显示条数 {0} - {1} of {2}',
			emptyMsg : "无数据"
		})
    });
    
    var userWin = Ext.create('Ext.Window', {
        title: '添加成员',            
        modal : true,
        closeAction: 'hide',
        width: 600,
        height: 500,
        layout : 'fit',
        items: [userGrid]
    });
    
    //-------------------------------------
  	//实现修改框的数据填充
    var loadUser = function(rec){
    	userModifyForm.loadRecord(rec);
    	var createTime = rec.get('createTime'); 
    	Ext.getCmp('fcreateTime').setValue(new Date(createTime));    
    	
    }
    
    var grid = Ext.create('Ext.grid.GridPanel', {	          
    	id : 'grid',   
        store: teamStore,
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
			  	text: '图片',
				    width: 80,
				    sortable: true,
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
					    icon   : '${contextPath}/manager/images/modify.png',  // Use a URL in the icon config
					    tooltip: '修改',
					    handler: function(grid, rowIndex, colIndex) {
					    	currTeam = teamStore.getAt(rowIndex);
					    	teamUpdateForm.loadRecord(currTeam);
					    	teamUpdateWin.show();					
					    }
					},
		            {
			            icon   : '${contextPath}/manager/images/detail.png',  // Use a URL in the icon config
			            tooltip: '球队编辑',
			            handler: function(grid, rowIndex, colIndex) {
			            	currTeam = teamStore.getAt(rowIndex);
			                memberWin.show();
			                
							memberStore.proxy.extraParams.teamId = currTeam.get('id');	
							memberStore.load();
			            }
			        },
			        {
			            icon   : '${contextPath}/manager/images/del.png',  // Use a URL in the icon config
			            tooltip: '禁用',
			            handler: function(grid, rowIndex, colIndex) {
			                var rec = teamStore.getAt(rowIndex);
			                var teamId = rec.get('id');			         
			                var teamName = rec.get('teamName');
			                var stat = rec.get('stat');	
			                var msg = "是否确定需要禁用队伍 " + teamName;
			                if(stat==1){
			                Ext.Msg.confirm('提示', msg, function(ok) {
	                        	if('yes' != ok){
	                        		return;
	                        	} 
				            	Ext.Ajax.request({
				            		url : '${contextPath}/user/team/delete.do',
				            		params : {			            			
				            			'teamId' : teamId
				            		},
				            		success : function(r, o){
				            			var strJson = r.responseText;
				            			var obj = eval("(" + strJson + ")");
				            			Ext.Msg.alert("禁用提示", obj.message);
				            			teamStore.reload();
				            		},
				            		failure : function(form, action){
				            			Ext.Msg.alert("用户", "操作失败！");
				            		}
				            	});
			                  });
			                }else{
			                	Ext.Msg.alert("提示", "该球队已经被禁用!！");
			                }
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
    
    Ext.create('Ext.container.Viewport', {
		layout : 'fit',
		renderTo: 'order-grid',
		items : [grid]		 
	});
    
    pStore.load();
    pfStore.load();
    //App.store.common.jobStore.load();
    fUserStatStore.load();
    bUserStatStore.load();
    
    sportTypeStore.load();
    pSportTypeStore.load();
    warTypeStore.load();
    teamPositionStore.load();
});

</script>
</head>
<body>
<div id="order-grid"></div>
</body>
</html>