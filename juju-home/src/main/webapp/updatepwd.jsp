<%--
  Created by IntelliJ IDEA.
  User: chuweifeng
  Date: 14-9-15
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script>
        function modifyPwd(){
            alert("00000");
        }
    </script>
</head>
<body>
原密码：<input type="text" id="oldpwd" name="oldpwd"/>
新密码：<input type="text" id="newpwd" name="newpwd"/>
确认密码：<input type="text" id="confirmpwd" name="confirmpwd"/>
<input type="button" value="modify pwd" onclick="javascript:modifyPwd();"/>

</body>
</html>
