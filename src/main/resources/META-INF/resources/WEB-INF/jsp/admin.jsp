<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:10 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>R.LOG</title>
</head>
<body>
<c:if test = "${!empty message}">
    <c:out value = "${message}"/>
</c:if>

<h3>관리자 페이지</h3>
<p>어서오세요 관리자님</p>
<p><a href = '/main'>사용자 페이지</a></p>
<p><a href = '/write'>글 작성 (개발 진행)</a></p>
<p><a href = '/logout'>로그아웃</a></p>
</body>
</html>
