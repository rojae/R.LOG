<!DOCTYPE html>
<html lang="en">
<head>
    <!-- 공통 상단 메타 정보 -->
    <th:block th:include="../common/top-meta.html"/>

    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <script>
        <!-- 관리자 역할을 가진 사용자만 '글쓰기' 버튼을 보이도록 설정 -->
        <sec:authorize access="hasRole('ROLE_ADMIN')">
        function goWrite() {
            window.location.href = '/admin/write';
        }
        </sec:authorize>

        <!-- 관리자 역할이 아닌 사용자에게 알림창을 띄우고 새로고침 -->
        <sec:authorize access="!hasRole('ROLE_ADMIN')">
        function goWrite() {
            showModal('알림창', '글쓰기는 블로그 주인만 가능합니다.');
            setTimeout(function () {
                window.location.reload();
            }, 2000);
        }
        </sec:authorize>
    </script>

    <script data-ad-client="ca-pub-8310034986810669" async
            src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>

</head>

<body>
<div id="layoutDefault">
    <div id="layoutDefault_content">
        <main>
            <!-- 공통으로 사용할 헤더 -->
            <th:block th:replace="~{../common/header.html}"/>

            <!-- 요청에 따라 변하는 body 부분 -->
            <th:block th:replace="${body}"/>
        </main>
    </div>

    <!-- 공통 footer -->
    <th:block th:replace="~{../common/footer.html}"/>

    <!-- 공통 modal -->
    <th:block th:replace="~{../common/modal.html}"/>

    <!-- 공통 sticky -->
    <th:block th:replace="~{../common/sticky.html}"/>

    <!-- 공통 하단 메타 -->
    <th:block th:replace="~{../common/buttom-meta.html}"/>
</div>
</body>
</html>
