<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>

<%! int k = 0;   void fun() {    } %>
<%--JSP声明，放在_jspService方法外，在JSP产生的java类的类中，其他的都在生成的java类的_JspService方法里--%>
${sessionScope}${k}<%--用这个${}来取JSP生成的类的变量--%>

<%--JSP表达式--%>
<%=new java.util.Date()%>
<%=new java.util.Date()%>
<%=new java.util.Date()%>
<hr>
<%
    for (int i = 0; i < 5; i++) {

%><%--这里的${i}的作用域在for循环的外面，所以显示不出来来--%>
<h2>hello  ${i}  <%=new java.util.Date()%>
</h2>
<%
    }
%>
<%
int num = 0;
String str = "哈哈哈";
for (int i = 0; i < 100; i++) {
num += i;
}
out.println("<h1>" + num + "</h1>");
out.write(num + str);
%>
<hr>


</body>
</html>
