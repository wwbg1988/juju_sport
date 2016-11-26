<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/headroot.jsp"%>

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
				title:'公司公告',
				region:'north',
				//colspan:2,
				//width:"100%",
				height:"50%",
				html:"<table><tr><td width='30px'>1</td><td>网络报修平台试运行，运行过程中遇到问题请及时收集，反馈技术质量部。</td></tr>"+
					"<tr><td>2</td><td>进入夏季，请注意车辆水温高的接待报修。</td></tr>"+
					"<tr><td>3</td><td>各报修员请加强个人登录账号的管理。</td></tr>" +
					"<tr><td>4</td><td>客户报修请及时接待，生成单据。</td></tr></table>"
			},{
				xtype:'panel',
				title:'待办事项',
				//width:"70%",
				//height:"100",
				region:'center',
				html:"<table><tr><td width='30px'>1</td><td>报修编码中缺少：R灯不亮报修编码。</td></tr>"+
				"<tr><td width='30px'>2</td><td>车辆信息W0B213所属路线信息发生更改，请及时调整。</td></tr></table>"
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
