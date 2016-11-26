<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/manager/inc/headroot.jsp" %>
<title>聚运动后台管理</title>
<script type="text/javascript">
		Ext.onReady(function() {
			Ext.widget('button', {
						renderTo : 'loginButton',
						text : '登录',
						scale : 'large',
						width : 200,
						handler : function() {
							if (Ext.getDom('uc').value == '') {
								Ext.Msg.alert("信息", "请输入用户名！");
								return;
							}
							if (Ext.getDom('pwd').value == '') {
								Ext.Msg.alert("信息", "请输入密码！");
								return;
							}
							login();
						}
					});
		
			var clearHtml = function() {
				var ob = document.getElementById("div_all");
				ob.parentNode.removeChild(ob);
			};
			
			//给密码输入添加回车事件;
			Ext.get("pwd").on("keydown",function(e,t){
				//如果是点击回车了，则调用login()方法;
				if(e.keyCode==13){
					login();
				}
				
			});
			var login = function() {
				Ext.Ajax.request({
							url : '${contextPath}/login/login.do',
							params : {
								"userCode" : Ext.getDom('uc').value,
								"password" : Ext.getDom('pwd').value
							},
							method : 'POST',
							success : function(r, o) {
								var strJson = r.responseText;
								var obj = eval("(" + strJson + ")");	//菜单树
								if (obj.success == true) {
		
									Ext.Ajax.request({
												url : '${contextPath}/login/getUser.do',
												method : 'POST',
												success : function(r, o) {
													var strJson = r.responseText;
													var obj2 = eval("(" + strJson + ")");	//用户信息
													//if (obj2.success == true) {
														clearHtml();
														//console.log(obj);
														showIndex(obj2,
																obj2.realName,
																obj2.groupName);
		
													//}
												},
												failure : function(form, action) {
												}
											});
		
								} else {
									Ext.Msg.alert("信息", obj.message);
								}
							},
							failure : function(form, action) {
							}
						});
			};
		
			var showIndex = function(action, name, group) {
		
				var createCenter = function() {
					var tabpanel = Ext.widget('tabpanel', {
								id : 'tabPanel',
								region : 'center',
								enableTabScroll : true
							});
					var indexTab = Ext.widget('panel', {
						id : 'indexTab',
						closable : false,
						title : '主页',
						html : '<iframe src = "${contextPath}/login/main.do" width = "100%" height = "100%" scrolling="no" ></iframe>'
					});
					Ext.getCmp('tabPanel').add(indexTab);
					Ext.getCmp('tabPanel').setActiveTab(indexTab);
					return tabpanel;
				};
		
				var pwModifyForm = Ext.create('Ext.form.FormPanel', {
							id : 'modifyForm',
							frame : true,
							defaultType : 'textfield',
							items : [{
										fieldLabel : '原始密码',
										inputType: 'password',
										id : 'oldpw',
										name : 'oldPW',
										allowBlank : false
									}, {
										fieldLabel : '修改密码',
										inputType: 'password',
										id : 'newPW',
										name : 'newPW',
										allowBlank : false
									}, {
										fieldLabel : '确认密码',
										inputType: 'password',
										id : 'againPW',
										name : 'newPWAgain',
										allowBlank : false
									}],
							buttons : [{
								text : '保存',
								handler : function() {
									var me=this.up('panel');
									if (!me.form.isValid()) {
										return;
									}
		
									if (Ext.getCmp('newPW').getValue() != Ext
											.getCmp('againPW').getValue()) {
										Ext.Msg.alert("信息", "两次输入的密码不一致，请重新输入");
										return;
									}
									me.form.submit({
												url : '${contextPath}/manager/account/resetPasswd.do',
												method : 'POST',
												success : function(form, action) {
													Ext.Msg.alert("信息",
															action.result.message);
													me.form.reset();
													passwordWindow.hide();
												},
												failure : function(form, action) {
													Ext.Msg.alert("信息",
															action.result.message);
												},
												waitMsg : '正在处理，请稍候……'
											});
								}
							}, {
								text : '重置',
								handler : function() {
									var me=this.up('panel');
									me.form.reset();
								}
							}]
						});
		
				var passwordWindow = Ext.create('Ext.Window', {
							id : 'passwordWindow',
							width : 300,
							height : 150,
							resizable : false,
							modal : true,
							closeAction : 'hide',
							items : [pwModifyForm]
						});
		
				var createWest = function() {
					return {
						region : 'west',
						id : 'mainMenu',
						collapsible : true,
						autoScroll : true,
						title : '功能菜单',
						split : false,
						width : 200,
						minWidth : 150,
						maxWidth : 300,
						tbar : [{
									text : '修改密码',
									icon : '${contextPath}/manager/images/password-change.png',
									handler : function() {
										pwModifyForm.form.reset();
										passwordWindow.show();
									}
								}, {
									text : '退出',
									icon : '${contextPath}/manager/images/logout.gif',
									handler : function() {
										Ext.MessageBox.show({
													title : '确认',
													msg : '确认退出吗？',
													width : 300,
													buttons : Ext.MessageBox.OKCANCEL,
													fn : function(btn) {
														if (btn == 'ok') {
															location.reload();
														}
													}
												});
		
									}
								}]
					};
				};
		
				var createNorth = function() {
					return {
						region : 'north',
						height : 58,
						html : '<div id="div_1" style="position: absolute; top: 0px; width: 100%; height: 58px; left: 0px; right: 0px; background: url(\'${contextPath}/manager/images/headback.png\') repeat-x;"><div style="position: absolute; top: 4px; width: 100%; height: 56px; left: 20px; right: 0px;" align="left"><!--<img src=\'${contextPath}/manager/images/logo.png\'/>--><div style="position: absolute; top: 0px; width: 100%; height: 56px; left: 0px; right: 0px;" align="center"><!--<img src=\'${contextPath}/manager/images/text.png\'/>--><div style="position: absolute; top: 2px; width: 100%; height: 56px; right: 50px;" align="right"><font size = "3" color="#B9B5B5" face="微软雅黑"><img src=\'${contextPath}/manager/images/user.png\'/>'
								+ name
								+ '<br><img src=\'${contextPath}/manager/images/gears.png\'/>'
								+ group + '</font></div></div></div></div>'
					};
				};
		
				Ext.create('Ext.Viewport', {
							renderTo : Ext.getBody(),
							layout : {
								type : 'border',
								padding : 5
							},
							items : [createCenter(), createWest(), createNorth()]
		
						});
		
				var showMain = function(action) {
		
					openTab = function(a) {
						var url = a.raw.menuFunctionAction;
						var name = a.raw.menuFunctionName;
						var openType = a.raw.openType;
						if ('' + openType == '1') {
							var width = 1015;
							var height = 690;
							var ah = (screen.height - 80 - height) / 2;
							var aw = (screen.width - 20 - width) / 2;
							var fulls = "top=" + ah + ",left=" + aw
									+ ",resizable=no,location=no,";
							if (window.screen) {
								fulls += ",height=" + height;
								fulls += ",width=" + width;
							}
		
							//var openWindow = window.open('../' + url, '', fulls);
						} else {
							if (Ext.getCmp(url) == null) {
								var randomnumber=Math.floor(Math.random()*100000);
								
								var openTabView = new Ext.Panel({
									id : url,
									closable : true,
									title : name,
									html : '<iframe src = "${contextPath}/'
											+ url+"?rdn=" +randomnumber
											+ '" width = "100%" height = "100%" scrolling="auto" ></iframe>'
								});
								Ext.getCmp('tabPanel').add(openTabView);
								Ext.getCmp('tabPanel').setActiveTab(openTabView);
							} else {
								Ext.getCmp('tabPanel').setActiveTab(Ext.getCmp(url));
							}
						}
					};
		
					var r = action.groups;
					for (var i = 0; i < r.length; i++) {
						var title = r[i].menuGroupName;
						var menus = r[i].functions;
						for (var j = 0; j < menus.length; j++) {
							var child = menus[j];
							child.text = child.menuFunctionName;
							child.funcType = child.menuFunctionAction;
							child.leaf = true;
						}
		
						var node = new Ext.create('Ext.tree.Panel', {
									title : title,
									collapsible : true,
									rootVisible : false,
									autoScroll : true,
									root : {
										editable : false,
										expanded : true,
										txt : 'root',
										draggable : false,
										children : menus
									},
									listeners : {
										itemclick : function(node, a, item) {
											openTab(a);
										}
									}
								});
						Ext.getCmp('mainMenu').add(node);
		
					}
		
				};
		
				showMain(action);
			};
		
		});

</script>
</head>
<body style="background-color: #868789;">
	<div id="div_ext"></div>
	<div id="div_all">
		<div id="div_1"
			style="position: absolute; top: 0px; width: 100%; height: 36px; left: 0px; right: 0px; background: url('${contextPath}/manager/images/top.png') repeat-x;"></div>
		<div id="div_2"
			style="position: absolute; top: 36px; width: 100%; height: 48px; left: 0px; right: 0px;"></div>

		<div id="div_5"
			style="position: absolute; bottom: 247px; left: 0px; right: 0px; width: 100%; height: 235px; background-color: #868789;"
			align="center">
			<table
				style="width: 411px; height: 235px; background: url('${contextPath}/manager/images/login-picture.png');">
				<tr>
					<td align="center">&nbsp;</td>
				</tr>
				<tr>
					<td align="center">&nbsp;</td>
				</tr>
				<tr align="center">
					<td><input type="text" id="uc" name="userCode"
						style="width: 370px; height: 30px;padding-left: 10px;"></input></td>
				</tr>
				<tr>
					<td align="center">&nbsp;</td>
				</tr>
				<tr>
					<td align="center"><input type="password" id="pwd"
						name="password" style="width: 370px; height:30px;padding-left: 10px;"></input></td>
				</tr>
				<tr>
					<td align="center">
						<div id="loginButton">&nbsp;</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="div_6"
			style="position: absolute; bottom: 55px; left: 0px; right: 0px; width: 100%; height: 192px; background: url('${contextPath}/manager/images/under.png') repeat-x;"
			align="center">
			<img align="middle" src="${contextPath}/manager/images/login-back.png">
		</div>
		<div id="div_7"
			style="position: absolute; bottom: 0px; left: 0px; right: 0px; width: 100%; height: 55px; background-color: #FFFFFF;"
			align="center">
			 <img src="${contextPath}/manager/images/juju.png"></img> 
		</div>
	</div>
</body>
</html>