<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:05 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>테스트 페이지</title>
</head>
<body>
    <form name = "signUp" action="/signup" method="post">
        <input name = "userName" value =""/>
        <input name = "password" value =""/>
        <input name = "email" value =""/>
        <input name = "role" value =""/>
        <input type="submit" value="가입"/>
    </form>
</body>
</html>
