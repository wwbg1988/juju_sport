<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/manager/ext4/resources/css/ext-all.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/manager/ext4/ext-all.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/manager/ext4/ext-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/manager/base/login.js"></script>
<title>巴士客修管理平台</title>
</head>
<body style="background-color: #868789;">
	<div id="div_ext"></div>
	<div id="div_all">
		<div id="div_1"
			style="position: absolute; top: 0px; width: 100%; height: 36px; left: 0px; right: 0px; background: url('../manager/images/top.png') repeat-x;"></div>
		<div id="div_2"
			style="position: absolute; top: 36px; width: 100%; height: 48px; left: 0px; right: 0px;"></div>

		<div id="div_5"
			style="position: absolute; bottom: 247px; left: 0px; right: 0px; width: 100%; height: 235px; background-color: #868789;"
			align="center">
			<table
				style="width: 411px; height: 235px; background: url('../manager/images/login-picture.png');">
				<tr>
					<td align="center">&nbsp;</td>
				</tr>
				<tr>
					<td align="center">&nbsp;</td>
				</tr>
				<tr align="center">
					<td><input type="text" id="uc" name="userCode"
						style="width: 370px; height: 30px;"></input></td>
				</tr>
				<tr>
					<td align="center">&nbsp;</td>
				</tr>
				<tr>
					<td align="center"><input type="password" id="pwd"
						name="password" style="width: 370px; height:30px;"></input></td>
				</tr>
				<tr>
					<td align="center">
						<div id="loginButton">&nbsp;</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="div_6"
			style="position: absolute; bottom: 55px; left: 0px; right: 0px; width: 100%; height: 192px; background: url('../manager/images/under.png') repeat-x;"
			align="center">
			<img align="middle" src="../manager/images/login-back.png">
		</div>
		<div id="div_7"
			style="position: absolute; bottom: 0px; left: 0px; right: 0px; width: 100%; height: 55px; background-color: #FFFFFF;"
			align="center">
			<img src="../manager/images/foot.png"></img>
		</div>
	</div>
</body>
</html>