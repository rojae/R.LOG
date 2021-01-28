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
    <h3>회원가입 페이지</h3>

    <form name = "signUp" action="/signup" method="post">
        <input type="text" name = "userName" value =""/>
        <input type="password" name = "password" value =""/>
        <input type="text" name = "email" value =""/>
        <input type="text" name = "role" value =""/>
        <input type="submit" value="가입"/>
    </form>
</body>
</html>
