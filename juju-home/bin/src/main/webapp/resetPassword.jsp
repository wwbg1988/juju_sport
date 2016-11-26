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
    <script type="text/javascript" src="js/verifyCode.js"></script>
    <script type="text/javascript" src="js/jquery.gzjs"></script>
    <script>
        function resetPassword(){
            alert("00000");
        }
    </script>
</head>
<body>
<form action="user/resetPassword.do">
用户名：<input type="text" id="accountName" name="accountName"/>
验证码：<input type="text" id="checkCode" name="checkCode"/>
        <img id="imgObj" alt="" src="/home/user/image.do"/>
        <a href="#" onclick="changeImg()">换一张</a>
<input type="submit" value="check" onclick="javascript:resetPassword();"/>
</form>
</body>
</html>
