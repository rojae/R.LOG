<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:10 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>R.LOG</title>
</head>
<body>
    <c:if test = "${!empty message}">
        <c:out value = "${message}"/>
    </c:if>

    <h3>로그인 페이지</h3>
    <form name = "login"  method="post" action="/login">
        <label>
            <p>이메일</p>
            <input type="text" name = "email" value =""/>
        </label>
        <label>
            <p>비밀번호</p>
            <input type="password" name = "password" value =""/>
        </label>
        <input type="submit" value="로그인"/>
    </form>
    <button onclick="location.href='/signup'">회원가입</button>

</body>
</html>
