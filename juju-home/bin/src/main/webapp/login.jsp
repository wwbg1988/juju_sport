<%--
  Created by IntelliJ IDEA.
  User: tank
  Date: 14-9-15
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" language="javascript" src="http://lib.rrs.com/brand/static/admin/assets/scripts/jquery-1.4.4.min.js"></script>
    <script>
        function loginPwd(){
            var userName = $('#accountName').val();
            var pwd = $('#pwd').val();
			jquery.ajax({
				
				type: "POST",
              url: "/user/login.do",
              async: false,      //是否ajax同步
			  dataType : 'json',
			  data:{accountName:accountName,pwd:pwd},
              error: function(msg) {
			  alert("载入失败");
              },
			  success:function(msg){
			  var jsondata = msg.data.result;
			  }
				
			});
        }
		$('#registBut').click(function(){
			alert('123');
			var registName = $('#registName').val();
			var newPwd = $('#newpwd').val();
			var agPwd = $('#agpwd').val();
			if(registName==''){
				alert("请输入账号");
			}
			reg = /\w{4,20}/
			if(!reg.test(registName)){
				alert("4-20位支持英文字母及数字");
			}
			reg2 = /\w{6,20}/
			if(!reg2.test(newPwd)){
				alert("6-20位支持英文字母及数字");
			}
			if(newPwd==''){
				alert("请输入密码");
			}
			if(agPwd==''){
				alert("请输入确认密码");
			}
			if(!$('#agreement').attr("checked")){
				alert("请同意聚喜网注册条款");
			}
			if(newPwd == agPwd){
		jquery.ajax({
				
			type: "POST",
              url: "/user/regUser.do",
              async: false,      //是否ajax同步
			  dataType : 'json',
			  data:{accountName:accountName,newpwd:newPwd,agpwd:agPwd},
              error: function(msg) {
			  alert("注册失败");
              },
			  success:function(msg){
			  var jsondata = msg.data.result;
			  }
				
			});
			}else{
				alert('两次密码输入不相同')
			}
			
		});
		
    </script>
</head>
<body>
    <form action="user/login.do">
    用户名：<input type="text" id="accountName" name="accountName"/>
    密码：<input type="password" id="pwd" name="pwd"/>
    <input type="submit" value="login" onclick="javascript:loginPwd();"/>
    </form>
	账号:<input type="text" id="registName"/>
	密码:<input type="password" id="newpwd"/>
	二次密码:<input type="password" id="agpwd"/>
		<input name="Fruit" type="checkbox" id="agreement" />同意XXXX </label> 
	<input type="button" value="regist" id="registBut"/>
    <form id="form1" method="post" action="/file/api/upload.do" enctype="multipart/form-data">
        <tr>
            <td width="25%" align="right">上传文件：</td>
            <td><input id="files" type="file" NAME="files" style="width:300px;"></td>
        </tr>
        <tr align="center" valign="middle">
            <td height="60" colspan="2"><input type="submit" ID="BtnOK" value="确认上传"></td>
        </tr>
    </form>
</body>
</html>
