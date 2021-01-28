<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:10 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>로그인 페이지</h3>
    <form name = "login"  method="post" action="/login">
        <input type="text" name = "userName" value =""/>
        <input type="password" name = "password" value =""/>
        <input type="submit" value="로그인"/>
    </form>
</body>
</html>
