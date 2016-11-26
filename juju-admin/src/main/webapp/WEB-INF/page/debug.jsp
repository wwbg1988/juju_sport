<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 14-9-14
  Time: 下午5:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>系统调试</title>
</head>
<body>
<table width="100%" border="1">
    <tr>
        <td>Spring容器对象(beanName)</td>
        <td>方法名称</td>
        <td>返回类型</td>
        <td>方法参数</td>
        <td>测试</td>
    </tr>
    <c:forEach items="${debug_method_key}" var="method">
    <tr>
        <td>${param.beanName}</td>
        <td>${method.name}</td>
        <td>${method.returnType}</td>
        <td>
        <c:forEach var="per" items="${method.parameterTypes}">
            ${per}
        </c:forEach>
        </td>
        <td><a href="#">执行</a></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
