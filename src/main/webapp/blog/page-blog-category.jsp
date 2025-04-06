<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- Page Header-->
<header class="page-header page-header-dark bg-img-cover overlay overlay-60"
        style="background-image: url(https://cdn.pixabay.com/photo/2016/03/26/22/21/books-1281581_960_720.jpg)">
    <div class="page-header-content">
        <div class="container text-center">
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <h1 class="page-header-title mb-3"><c:out value="${categoryName}"/></h1>
                    <p class="page-header-text">카테고리 글 조회</p>
                </div>
            </div>
        </div>
    </div>
    <div class="svg-border-rounded text-light">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 144.54 17.34" preserveAspectRatio="none"
             fill="currentColor">
            <path d="M144.54,17.34H0V0H144.54ZM0,0S32.36,17.34,72.27,17.34,144.54,0,144.54,0"></path>
        </svg>
    </div>
</header>

<section class="bg-light py-10">
    <div class="container">

        <div class="d-flex align-items-center justify-content-between">
            <h6 class="mb-0"><c:out value="${categoryName}"/></h6>
        </div>
        <div class="row">
            <div class="col-lg-10 col-xl-10">
                <hr class="mb-4"/>
                <c:forEach var="item" items="${postPage.content}" varStatus="status">
                    <c:if test="${!empty item}">
                        <div class="media">
                            <div class="media-body">
                                <a class="text-dark" href="#!"><h5 class="mt-0">${item.title}</h5></a>
                                <p>${item.header}</p>
                                <a class="text-arrow-icon small" href="${item.url}">Read more<i data-feather="arrow-right"> </i></a>
                            </div>
                            <a href="${item.url}"><img class="align-self-start rounded shadow media-img ml-4"
                                                                      src="${item.thumbNail}" alt="..."/></a>
                        </div>
                        <hr class="my-4"/>
                    </c:if>
                </c:forEach>
            </div>
            <div class="col-lg-5 col-xl-4 d-none">
                <div class="card">
                    <div class="card-body">
                        <h6>관련 글 댓글</h6>
                        <hr/>
                        <div class="d-flex mb-4">
                            <div class="avatar avatar-lg"><img class="avatar-img"
                                                               src="/assets/img/illustrations/profiles/profile-1.png"/>
                            </div>
                            <div class="ml-3">
                                <a class="text-dark" href="#!"><h6 class="mb-1">테스트</h6></a>
                                <div class="small text-gray-500">by <a class="text-gray-500" href="#!">Valerie Luna</a>
                                </div>
                            </div>
                        </div>
                        <div class="d-flex mb-4">
                            <div class="avatar avatar-lg"><img class="avatar-img"
                                                               src="/assets/img/illustrations/profiles/profile-2.png"/>
                            </div>
                            <div class="ml-3">
                                <a class="text-dark" href="#!"><h6 class="mb-1">테스트</h6></a>
                                <div class="small text-gray-500">by <a class="text-gray-500" href="#!">Ewan Rogers</a>
                                </div>
                            </div>
                        </div>
                        <div class="d-flex mb-4">
                            <div class="avatar avatar-lg"><img class="avatar-img"
                                                               src="/assets/img/illustrations/profiles/profile-3.png"/>
                            </div>
                            <div class="ml-3">
                                <a class="text-dark" href="#!"><h6 class="mb-1">테스트</h6></a>
                                <div class="small text-gray-500">by <a class="text-gray-500" href="#!">Alicia Allen</a>
                                </div>
                            </div>
                        </div>
                        <div class="d-flex mb-4">
                            <div class="avatar avatar-lg"><img class="avatar-img"
                                                               src="/assets/img/illustrations/profiles/profile-4.png"/>
                            </div>
                            <div class="ml-3">
                                <a class="text-dark" href="#!"><h6 class="mb-1">테스트</h6></a>
                                <div class="small text-gray-500">by <a class="text-gray-500" href="#!">Kolby Brock</a>
                                </div>
                            </div>
                        </div>
                        <div class="d-flex">
                            <div class="avatar avatar-lg"><img class="avatar-img"
                                                               src="/assets/img/illustrations/profiles/profile-5.png"/>
                            </div>
                            <div class="ml-3">
                                <a class="text-dark" href="#!"><h6 class="mb-1">테스트</h6></a>
                                <div class="small text-gray-500">by <a class="text-gray-500" href="#!">William Cole</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <nav aria-label="Page navigation">
            <ul class="pagination pagination-blog justify-content-center">
                <c:choose>
                    <c:when test="${postPage.first}">
                        <li class="page-item disabled">
                            <a class="page-link" href="?page=${postPage.number}" aria-label="Previous">
                                <span aria-hidden="true">«</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="?page=$0">처음</a>
                        </li>

                        <li class="page-item">
                            <a class="page-link" href="?page=${postPage.number-1}" aria-label="Previous">
                                <span aria-hidden="true">«</span>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>

                <!--
                    1. 글이 없는 경우 예외 처리
                    2. 페이징 컨텐츠
                -->
                <c:choose>
                    <c:when test="${startBlockPage >= endBlockPage}">
                        <li class="page-item active">
                            <a class="page-link" href="?page=0">${postPage.pageable.pageNumber+1}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
                            <c:choose>
                                <c:when test="${postPage.pageable.pageNumber+1  == i}">
                                    <li class="page-item active">
                                        <a class="page-link" href="?page=${i-1}">${i}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${i-1}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>


                <!-- 다음 -->
                <c:choose>
                    <c:when test="${postPage.last}">
                        <li class="page-item disabled">
                            <a class="page-link" href="?page=${postPage.number}" aria-label="Previous">
                                <span aria-hidden="true">»</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item ">
                            <a class="page-link" href="?page=${postPage.number+1}">»</a>
                        </li>
                        <li class="page-item ">
                            <a class="page-link" href="?page=${postPage.totalPages-1}">마지막</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>

    </div>
</section>
