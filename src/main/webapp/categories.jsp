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
<p><a href='/main'>메인 페이지로 돌아가기</a></p>

<h1>카테고리 출력 테스트</h1>

        <!-- 1 Depth -->
        <c:forEach var="item" items="${categories.subCategories}" varStatus="status">

            <c:out value="${item.categoryName}"/>

                <!-- 2 Depth -->
                <c:if test="${!empty item.subCategories}">
                    <c:forEach var="subItem" items="${item.subCategories}" varStatus="status">

                        <br/>
                        <c:out value="----${subItem.categoryName}"/>

                            <!-- 3 Depth -->
                            <c:if test="${!empty subItem.subCategories}">
                                <c:forEach var="last" items="${subItem.subCategories}" varStatus="status">
                                    <br/>
                                    <c:out value="--------${last.categoryName}"/>
                                </c:forEach>

                            </c:if>

                        </c:forEach>
                     </c:if>

            <br/>
        </c:forEach>

</body>
</html>
