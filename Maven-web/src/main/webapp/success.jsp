<%--
  Created by IntelliJ IDEA.
  User: vocyd
  Date: 2020/8/4
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆成功</title>
</head>
<body>
  <h1><%=request.getSession().getAttribute("user")%></h1>
</body>
</html>
