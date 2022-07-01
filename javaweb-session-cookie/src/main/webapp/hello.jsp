
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%--源码
final javax. servlet. jsp. PageContext pageContext;//页面上下文
javax. servlet. http . HttpSession session = nu17;//session
final javax. servlet. Servletcontext application; //applicati onContext
fina7 javax. servlet. ServletConfig config;//config
javax. serv1et. jsp. JspWriter out = nu11;//out
final java. lang.object page = this;//page:当前
HttpServ1etRequest request//请求
HttpServ1etResponse response//响应
页面前增加的代码
response . setContentType ("text/htm1");//设置响应的页面类型
pageContext = _ _jspxFactory . getPageContext(this，request， response,nu11，true, 8192，true);
_jspx_ page_ context = pageContext;
application = pageContext. getServletContext();
config = pageContext. getServletConfigO);
session = pageContext. getSession() ;
out = pageContext. getout();
_jspx_out = out;
--%>
<body>

<%String name = "哈哈";%>
<%=name%>

</body>
</html>
