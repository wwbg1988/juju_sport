<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manager/inc/headroot.jsp" %>

<!--<img id="basic" src="../images/in-index.jpg"></img>-->
<script type="text/javascript">
	Ext.onReady(function() {

//		var width = document.body.clientWidth;
//		var height = document.body.clientHeight;

//		var basic = Ext.create('Ext.resizer.Resizer', { id : 'picture', target : 'basic', handles : 'null',
//			width : width, height : height, minWidth : 100, minHeight : 50 });

		Ext.create('Ext.container.Viewport', {
// 			layout : {
// 				type:'table',
// 				columns:2,
// 				tableAttrs:{style:'width:100%;'}
//				tdAttrs:{style:'align:top;'}
// 			},
			layout:'border',
			items:[
			       {
				xtype:'panel',
				title:'公告',
				region:'north',
				//colspan:2,
				//width:"100%",
				height:"50%",
				html:"<table><tr><td width='30px'>1</td><td>aaaaa。</td></tr>"+
					"<tr><td>2</td><td>bbbbbb。</td></tr>"+
					"<tr><td>3</td><td>ccccccc。</td></tr>" +
					"<tr><td>4</td><td>dddddddd。</td></tr></table>"
			},{
				xtype:'panel',
				title:'待办事项',
				//width:"70%",
				//height:"100",
				region:'center',
				html:"<table><tr><td width='30px'>1</td><td>bbbbbbbb。</td></tr>"+
				"<tr><td width='30px'>2</td><td>eeeeeeeeee。</td></tr></table>"
			},{
				xtype:'panel',
				title:'警示信息',
				//width:'30%',
				//height:'100',
				region:'east',
				html:"<table><tr><td width='400px;'>无</td></tr></table>"
			}]
		});
	});


	
</script>
<body>
</body>