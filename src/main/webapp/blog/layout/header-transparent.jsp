<nav class="navbar navbar-marketing navbar-expand-lg bg-transparent navbar-light fixed-top" data-aos="fade-down">
    <div class="container">
        <a class="navbar-brand text-primary" href="/">R.LOG</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <i data-feather="menu"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto mr-lg-5">
                <!-- 1 Depth -->
                <th:block th:each="item : ${menu.subCategories}">
                    <li class="nav-item dropdown dropdown-xl no-caret">
                        <a class="nav-link dropdown-toggle" id="navbarDropdownPages" href="#" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span th:text="${item.categoryName}"></span><i class="fas fa-chevron-right dropdown-arrow"></i>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right mr-lg-n20 mr-xl-n15 animated--fade-in-up"
                             aria-labelledby="navbarDropdownPages">
                            <div class="row no-gutters">
                                <!-- 2 Depth -->
                                <th:block th:if="${item.subCategories != null}">
                                    <th:block th:each="subItem : ${item.subCategories}">
                                        <div class="col-lg-4 p-lg-5">
                                            <h6 class="dropdown-header text-primary" th:text="${subItem.categoryName}"></h6>
                                            <th:block th:if="${subItem.subCategories != null}">
                                                <th:block th:each="last : ${subItem.subCategories}">
                                                    <a class="dropdown-item" th:href="@{/category/{categoryId}/posts(id=${last.categoryId})}" th:text="${last.categoryName}"></a>
                                                </th:block>
                                            </th:block>
                                        </div>
                                    </th:block>
                                </th:block>
                            </div>
                        </div>
                    </li>
                </th:block>
                <li class="nav-item"><a class="nav-link" href="/guestbook">방명록</a></li>
            </ul>

            <form class="form-inline form-inline-sm" action="/index">
                <input name="keyword" class="form-control form-control-sm mr-sm-2" type="search" placeholder="Search" aria-label="Search" th:value="${keyword}">
                <button class="btn btn-sm rounded-sm btn-outline-primary my-2 my-sm-0" type="submit">
                    <i class="fas fa-search"></i>
                </button>
            </form>

            <!-- 로그인 버튼 (익명 사용자만) -->
            <sec:authorize access="isAnonymous()">
                <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="goLogin();">로그인<i class="ml-2" data-feather="arrow-right"></i></a>
            </sec:authorize>

            <!-- 인증된 사용자만 -->
            <sec:authorize access="isAuthenticated()">
                <!-- 관리자 권한이 있는 사용자 -->
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="goManage();">관리자<i class="ml-2" data-feather="arrow-right"></i></a>
                </sec:authorize>
                <!-- 내 정보 보기 -->
                <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="myInfo();">내 정보<i class="ml-2" data-feather="arrow-right"></i></a>
                <!-- 로그아웃 -->
                <a class="btn font-weight-500 ml-lg-2 btn-danger btn-sm" href="#" onclick="goLogout();">로그아웃<i class="ml-2" data-feather="arrow-right"></i></a>
            </sec:authorize>
        </div>
    </div>
</nav>
