<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>R.LOG</title>
</head>
<body>
<p><a th:href="@{/main}">메인 페이지로 돌아가기</a></p>

<h1>카테고리 출력 테스트</h1>

<!-- 1 Depth -->
<div th:each="item : ${categories.subCategories}">
    <span th:text="${item.categoryName}"></span>

    <!-- 2 Depth -->
    <div th:if="${not #lists.isEmpty(item.subCategories)}">
        <div th:each="subItem : ${item.subCategories}">
            <br/>
            <span th:text="'----' + ${subItem.categoryName}"></span>

            <!-- 3 Depth -->
            <div th:if="${not #lists.isEmpty(subItem.subCategories)}">
                <div th:each="last : ${subItem.subCategories}">
                    <br/>
                    <span th:text="'--------' + ${last.categoryName}"></span>
                </div>
            </div>
        </div>
    </div>
    <br/>
</div>

</body>
</html>
