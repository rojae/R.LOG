<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:29 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
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

</body>
</html>
