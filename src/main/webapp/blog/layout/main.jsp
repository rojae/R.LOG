<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../common/top-meta.jsp"/>
    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <script>

        <sec:authorize access="hasRole('ROLE_ADMIN')">

        function goWrite() {
            window.location.href = '/admin/write';
        }

        </sec:authorize>

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
            <!--공통으로 쓸 메인헤더-->
            <tiles:insertAttribute name="header"/>

            <!--요청에의해 바뀌는 body부분-->
            <tiles:insertAttribute name="body"/>
        </main>
    </div>
    <!-- 공통 footer -->
    <tiles:insertAttribute name="footer"/>
    <jsp:include page="../common/modal.jsp"/>
    <jsp:include page="../common/sticky.jsp"/>
    <jsp:include page="../common/buttom-meta.jsp"/>
</div>
</body>
</html>
