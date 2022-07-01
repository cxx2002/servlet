<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--JSP转发--%>
<%
    pageContext.forward("/index.jsp");//等价于servlet的request.getRequestDispatcher("/index.jsp").forward(req,resp);
%>
<%--内置对象--%>
<%--   request:客户端向服务器发送请求，产生的数据，用户看完就没用了，比如:新闻，用户看完没用的!
       session:客户端向服务器发送请求，产生的数据，用户用完一会还有用， 比如:购物车; ;
       application:客户端向服务器发送请求,产生的数据，-一个用户用完了，其他用户还可能使用，比如:聊天数据;
--%>
<%
    pageContext . setAttribute( "name1", "1号"); //保存的数据只在一个页面中有效
    request. setAttribute( "name2","2号"); // 保存的数据只在一次请求中有效，请求转发会携带这个数据
    session. setAttribute("name3", "3号"); // 保存的数据只在一次会话中有效， 从打开浏览器到关闭浏览
    application. setAttribute("name4","4号"); //保存的数据只在服务 器中有效，从打开服务器到关闭
%>
<%--脚本片段中的代码，会被原封不动生成到. JSP. java
要求:这里面的代码:必须保证Java语法的正确性--%>
<%
    //从pageContext取出,我们通过寻找的方式来
//find从底层到高层(作用域)，从page开始，没有回去找request，再找session，再找application，没有才返回null :
// get只会在page里找
String name1 = (String) pageContext . findAttribute ("name1");
String name2 = (String) pageContext . getAttribute("name2");
String name3 = (String) pageContext . findAttribute( "name3" );
String name4 = (String) pageContext. getAttribute( "name4");
String name5 = (String) pageContext. findAttribute("name5"); // 不存在
%>
<%--使用EL表达式输出${} --%>
<h1>取出的值为: </h1>
<h3>${name1}</h3>
<h3>${name2}</h3>
<h3>${name3}</h3>
<h3>${name4}</h3>
<h3>${name5}</h3>
<h3>HHHH</h3>
<h3> <%=name5%> </h3>
</body>
</html>

</body>
</html>
