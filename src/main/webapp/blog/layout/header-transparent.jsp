<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:importAttribute name="menu"/>

<nav class="navbar navbar-marketing navbar-expand-lg bg-transparent navbar-light fixed-top" data-aos="fade-down">
    <div class="container">
        <a class="navbar-brand text-primary" href="/">R.LOG</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><i
                data-feather="menu"></i></button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto mr-lg-5">
                <!-- 1 Depth -->
                <c:forEach var="item" items="${menu.subCategories}" varStatus="status">

                    <li class="nav-item dropdown dropdown-xl no-caret">
                        <a class="nav-link dropdown-toggle" id="navbarDropdownPages" href="#" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><c:out
                                value="${item.categoryName}"/><i
                                class="fas fa-chevron-right dropdown-arrow"></i>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right mr-lg-n20 mr-xl-n15 animated--fade-in-up"
                             aria-labelledby="navbarDropdownPages">
                            <div class="row no-gutters">
                                <!-- 2 Depth -->
                                <c:if test="${!empty item.subCategories}">
                                    <c:forEach var="subItem" items="${item.subCategories}" varStatus="status">
                                        <div class="col-lg-4 p-lg-5">
                                            <h6 class="dropdown-header text-primary"><c:out
                                                    value="${subItem.categoryName}"/></h6>
                                            <c:if test="${!empty subItem.subCategories}">
                                                <c:forEach var="last" items="${subItem.subCategories}"
                                                           varStatus="status">
                                                    <a class="dropdown-item" href="/category/${last.categoryId}/posts">${last.categoryName}</a>
                                                </c:forEach>
                                            </c:if>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </li>
                </c:forEach>
                <li class="nav-item"><a class="nav-link" href="/guestbook">방명록</a></li>

            </ul>

            <form class="form-inline form-inline-sm" action="/index">
                <input name="keyword" class="form-control form-control-sm mr-sm-2" type="search" placeholder="Search"
                       aria-label="Search" value="<c:out value = '${keyword}'/>">
                <button class="btn btn-sm rounded-sm btn-outline-primary my-2 my-sm-0" type="submit"><i
                        class="fas fa-search"></i></button>
            </form>

            <sec:authorize access="isAnonymous()">
                <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="goLogin();">로그인<i
                        class="ml-2" data-feather="arrow-right"></i></a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="goManage();">관리자<i
                            class="ml-2" data-feather="arrow-right"></i></a>
                </sec:authorize>
                <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="myInfo();">내 정보<i
                        class="ml-2" data-feather="arrow-right"></i></a>
                <a class="btn font-weight-500 ml-lg-2 btn-danger btn-sm" href="#" onclick="goLogout();">로그아웃<i
                        class="ml-2" data-feather="arrow-right"></i></a>
            </sec:authorize>
        </div>
    </div>
</nav>