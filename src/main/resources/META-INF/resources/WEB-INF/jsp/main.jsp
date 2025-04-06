<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:29 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<head>
    <title>R.LOG</title>
</head>
<body>
    <c:if test = "${!empty message}">
        <c:out value = "${message}"/>
        <br/>
    </c:if>

    <c:if test = "${!empty email}">
        <c:out value = "${email}"/>
        <br/>
    </c:if>

    <c:if test = "${!empty role}">
        <c:out value = "${role}"/>
        <br/>
    </c:if>

    <sec:authorize access="hasRole('ROLE_ADMIN')" >
        <p><a href = '/admin'>관리자 페이지로 이동</a></p>
    </sec:authorize>
    <p><a href = '/posts'>글 리스트 조회</a></p>
    <p><a href = '/categories'>카테고리 테스트 조회</a></p>
    <p><a href = '/logout'>로그아웃</a></p>

</body>
</html>
