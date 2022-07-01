<html>
<body>
<h2>Hello World!</h2>
<%--因为浏览器使用的编码格式与自己开发文件存储内容的编码不一致导致的，所以需要在jsp页面中添加代码，说明自己代码的编码格式--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--这里提交的路径，需要寻找项目的路径--%>
<%--${pageContext.request.contextPath}代表当前的项目--%>
<form action="${pageContext.request.contextPath}/login" method="get">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    <input type="submit">
</form>

</body>
</html>
