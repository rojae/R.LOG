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
<p><a href = '/main'>메인 페이지로 돌아가기</a></p>

<table border="1" style="text-align: center;width: 70%;margin: auto;">
    <tr>
        <th>순서</th>
        <th>작성자</th>
        <th>제목</th>
        <th>내용</th>
        <th>작성일</th>
        <th>수정일</th>
    </tr>
    <c:forEach var="post" items="${posts}" varStatus="status">
        <tr onclick="location.href='/post/${post.id}'">
            <Td>${status.count }</Td>
            <Td>${post.writer.userName }</Td>
            <Td>${post.title }</Td>
            <Td>${post.content }</Td>
            <Td>${post.createdDate }</Td>
            <Td>${post.modifiedDate }</Td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
