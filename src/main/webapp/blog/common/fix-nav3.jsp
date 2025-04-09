<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<body>
<div th:fragment="header">
    <!-- Navbar-->
    <nav class="navbar navbar-marketing navbar-expand-lg bg-white navbar-light fixed-top">
        <div class="container">
            <a class="navbar-brand text-primary" href="/">R.LOG</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><i
                    data-feather="menu"></i></button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto mr-lg-5">
                    <!-- 메뉴 1: 자바프로그래밍 -->
                    <li class="nav-item dropdown dropdown-xl no-caret">
                        <a class="nav-link dropdown-toggle" id="navbarDropdownDemos" href="#" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">자바프로그래밍<i
                                class="fas fa-chevron-right dropdown-arrow"></i></a>
                        <!-- 메뉴 펼침 구조 (축약) -->
                    </li>

                    <!-- 메뉴 2: 개발문서 -->
                    <li class="nav-item dropdown dropdown-xl no-caret">
                        <a class="nav-link dropdown-toggle" id="navbarDropdownPages" href="#" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">개발문서<i
                                class="fas fa-chevron-right dropdown-arrow"></i></a>
                        <!-- 메뉴 펼침 구조 (축약) -->
                    </li>

                    <!-- 메뉴 3: 일상 -->
                    <li class="nav-item dropdown no-caret">
                        <a class="nav-link dropdown-toggle" id="navbarDropdownDocs" href="#" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">일상<i
                                class="fas fa-chevron-right dropdown-arrow"></i></a>
                        <!-- 메뉴 펼침 구조 (축약) -->
                    </li>

                    <li class="nav-item"><a class="nav-link" href="/guestbook">방명록</a></li>
                </ul>

                <form class="form-inline form-inline-sm" th:action="@{/index}">
                    <input name="keyword" class="form-control form-control-sm mr-sm-2"
                           type="search" placeholder="Search" aria-label="Search"
                           th:value="${keyword}">
                    <button class="btn btn-sm rounded-sm btn-outline-primary my-2 my-sm-0" type="submit">
                        <i class="fas fa-search"></i>
                    </button>
                </form>

                <sec:authorize access="isAnonymous()">
                    <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="goLogin();">
                        로그인<i class="ml-2" data-feather="arrow-right"></i>
                    </a>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="goManage();">
                            관리자<i class="ml-2" data-feather="arrow-right"></i>
                        </a>
                    </sec:authorize>
                    <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="myInfo();">
                        내 정보<i class="ml-2" data-feather="arrow-right"></i>
                    </a>
                    <a class="btn font-weight-500 ml-lg-2 btn-danger btn-sm" href="#" onclick="goLogout();">
                        로그아웃<i class="ml-2" data-feather="arrow-right"></i>
                    </a>
                </sec:authorize>
            </div>
        </div>
    </nav>
</div>
</body>
</html>
