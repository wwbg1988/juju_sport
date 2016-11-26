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
    <script>
        function resetPWD(){
            alert("<% request.getParameter("token");%>");
        }
    </script>
</head>
<body>
<form action="user/resetPWD.do">
用户名：<input type="text" id="accountName" name="accountName"/>
密码：<input type="text" id="newpwd" name="newpwd"/>
重复密码：<input type="text" id="agpwd" name="agpwd"/>
Token:<input type="text" id="token" name="token" value="<% request.getParameter("token");%>"/>
<input type="submit" value="regUser" onclick="javascript:regPwd();"/>
</form>
</body>
</html>
