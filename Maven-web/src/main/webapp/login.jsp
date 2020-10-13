<%--
  Created by IntelliJ IDEA.
  User: vocyd
  Date: 2020/8/4
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <script>
        window.onload=function (){
            document.getElementById("img").onclick=function (){
                this.src="/mvn/checkCodeServlet?time="+new Date().getTime();
            }
        }
    </script>
</head>
<body>
    <form action="/mvn/loginServlet" method="post">
        <table>
            <tr>
                <td>用户名</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>密码</td>
                <td><input type="password" name="password"></td>
                <td><%=request.getAttribute("login_error")==null?"":request.getAttribute("login_error")%></td>
            </tr>
            <tr>
                <td>验证码</td>
                <td><input type="text" name="checkCode"></td>
                <td><%=request.getAttribute("cc_error")==null?"":request.getAttribute("cc_error")%></td>
            </tr>
            <tr>
                <td colspan="2"><img id="img" src="/mvn3/checkCodeServlet"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="登录"></td>
            </tr>
            <tr>
                <td><%=request.getAttribute("login_msg")==null?"":request.getAttribute("login_msg")%></td>
            </tr>
        </table>
    </form>


</body>
</html>
