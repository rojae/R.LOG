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

<h3>글 상세조회</h3>
<c:if test = "${!empty post}">
제목 : <c:out value = "${post.title}"/>
    <br/>
작성자 : <c:out value = "${post.writer.userName}"/>
    <br/>
내용 : <c:out value = "${post.content}"/>
</c:if>
<p><a href = '/posts'>리스트로 돌아가기</a></p>

</body>
</html>
