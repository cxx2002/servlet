<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<%@include file="commom/head.jsp"%>
<h1>网页主体</h1>
<%@include file="commom/Footer.jsp"%>--%>

<%--JSP标签--%>
<jsp:include page="commom/head.jsp"></jsp:include>
<h1>网页主体、</h1>
<%--jsp标签转发可以携带参数--%>
<jsp:forward page="01.jsp">
    <jsp:param name="" value=""/>
</jsp:forward>
<jsp:include page="/commom/Footer.jsp"></jsp:include>
</body>
</html>
