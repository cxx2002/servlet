<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="/head.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h4>if测试</h4>
<hr>
<form action="coreif.jsp" method="get">
    <%--EL表达式获取表单中的数据，${param.参数名字}--%>
    <input type="text" name="username" value="${param.username}">
    <input type="submit" value="登录">
</form>
</body>
</html>
