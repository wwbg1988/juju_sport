<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/manager/inc/headroot.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	Ext.onReady(function(){
		 Ext.QuickTips.init();
		    // setup the state provider, all state information will be saved to a cookie
		 Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
		 
		 var bbar =  Ext.create('Ext.Toolbar',{
			 items : [
			 	{
			 		text : '新建',
			 		handler : function(){
			 			newWindow.show();
			 		}
			 	}         
			 ]
		 });
		 
		 var newWindow = Ext.create('Ext.window',{
			 
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