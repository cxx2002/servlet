<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%//此时name1和name2取不到，因为作用域在pageContext和request，再另外一个页面JSP中就不存在了
    //从pageContext取出,我们通过寻找的方式来
//find从底层到高层(作用域)，从page开始，没有回去找request，再找session，再找application，没有才返回null :
// get只会在page里找
    String name1 = (String) pageContext . findAttribute ("name1");
    String name2 = (String) pageContext . getAttribute("name2");
    String name3 = (String) pageContext . findAttribute( "name3" );
    String name4 = (String) pageContext. getAttribute( "name4");
    String name5 = (String) pageContext. findAttribute("name5"); // 不存在
%>

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
