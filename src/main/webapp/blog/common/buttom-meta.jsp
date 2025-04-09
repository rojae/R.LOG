<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<body>
<div th:fragment="scripts">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
    <script src="/js/scripts.js"></script>
    <script src="/js/ajax.js"></script>
    <script src="https://unpkg.com/aos@next/dist/aos.js"></script>
    <script>
        AOS.init({
            disable: 'mobile',
            duration: 600,
            once: true,
        });
    </script>

    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <script src="/js/admin.js"></script>
    </sec:authorize>
</div>
</body>
</html>
