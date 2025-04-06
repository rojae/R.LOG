<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!-- Page Header-->
<header class="page-header page-header-dark bg-img-cover overlay overlay-40"
        style="background-image: url(https://images.unsplash.com/photo-1522199755839-a2bacb67c546?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1352&q=80)">
    <div class="page-header-content">
        <div class="container text-center">
            <div class="row justify-content-center">
                <div class="col-lg-12" data-aos="fade-up">
                    <div id="carousel-indicators" class="carousel slide " data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-indicators" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-indicators" data-slide-to="1"></li>
                            <li data-target="#carousel-indicators" data-slide-to="2"></li>
                        </ol>
                        <div class="carousel-inner">

                            <div class="carousel-item active">
                                <img class="d-block w-100" src="${topPost[0].thumbNail}" alt="First slide">

                                <div class="carousel-caption d-block align-items-center justify-content-center">
                                    <h1 class="text-white">${topPost[0].title}</h1>
                                    <p class="d-none d-sm-block">${topPost[0].header}</p>
                                    <a class="btn-primary btn-sm" href="${topPost[0].url}">더보기</a>
                                </div>
                            </div>

                            <div class="carousel-item">
                                <img class="d-block w-100" src="${topPost[1].thumbNail}" alt="Second slide">
                                <div class="carousel-caption d-block align-items-center justify-content-center">
                                    <h1 class="text-white">${topPost[1].title}</h1>
                                    <p class="d-none d-sm-block">${topPost[1].header}</p>
                                    <a class="btn-primary btn-sm" href="${topPost[1].url}">더보기</a>
                                </div>
                            </div>

                            <div class="carousel-item">
                                <img class="d-block w-100" src="${topPost[2].thumbNail}" alt="Third slide">
                                <div class="carousel-caption d-block align-items-center justify-content-center">
                                    <h1 class="text-white">${topPost[2].title}</h1>
                                    <p class="d-none d-sm-block">${topPost[2].header}</p>
                                    <a class="btn-primary btn-sm" href="${topPost[2].url}">더보기</a>
                                </div>
                            </div>

                        </div>

                        <a class="carousel-control-prev" href="#carousel-indicators" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carousel-indicators" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="svg-border-rounded text-light">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 144.54 17.34" preserveAspectRatio="none" fill="currentColor"><path d="M144.54,17.34H0V0H144.54ZM0,0S32.36,17.34,72.27,17.34,144.54,0,144.54,0"></path></svg>
    </div>
</header>

<section class="bg-light py-10">
    <div class="container">
        <c:if test="${!empty postPage.content[0]}">
            <a class="card post-preview post-preview-featured lift mb-5" href="${postPage.content[0].url}">
                <div class="row no-gutters">
                    <div class="col-lg-5">
                        <div class="post-preview-featured-img"
                             style="background-image: url('${postPage.content[0].thumbNail}')"></div>
                    </div>
                    <div class="col-lg-7">
                        <div class="card-body">
                            <div class="py-5">
                                <h5 class="card-title">${postPage.content[0].title}</h5>
                                <p class="card-text">${postPage.content[0].header}</p>
                            </div>
                            <hr/>
                            <div class="post-preview-meta">
                                <img class="post-preview-meta-img"
                                     src="${postPage.content[0].writer.profileImage}"/>
                                <div class="post-preview-meta-details">
                                    <div class="post-preview-meta-details-name">${postPage.content[0].writer.userName}(${postPage.content[0].writer.email})</div>
                                    <div class="post-preview-meta-details-date">${postPage.content[0].modifiedDate}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </a>
        </c:if>
        <div class="row">
            <c:if test="${!empty postPage.content[1]}">
                <div class="col-md-6 col-xl-4 mb-5">
                    <a class="card post-preview lift h-100" href="${postPage.content[1].url}"
                    ><img class="card-img-top" src="${postPage.content[1].thumbNail}"
                          alt="..."/>
                        <div class="card-body">
                            <h5 class="card-title">${postPage.content[1].title}</h5>
                            <p class="card-text">${postPage.content[1].header}</p>
                        </div>
                        <div class="card-footer">
                            <div class="post-preview-meta">
                                <img class="post-preview-meta-img"
                                     src="${postPage.content[1].writer.profileImage}"/>
                                <div class="post-preview-meta-details">
                                    <div class="post-preview-meta-details-name">${postPage.content[1].writer.userName}
                                        (${postPage.content[1].writer.email})
                                    </div>
                                    <div class="post-preview-meta-details-date">${postPage.content[1].modifiedDate}</div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </c:if>
            <c:if test="${!empty postPage.content[2]}">
                <div class="col-md-6 col-xl-4 mb-5">
                    <a class="card post-preview lift h-100" href="${postPage.content[2].url}"
                    ><img class="card-img-top" src="${postPage.content[2].thumbNail}"
                          alt="..."/>
                        <div class="card-body">
                            <h5 class="card-title">${postPage.content[2].title}</h5>
                            <p class="card-text">${postPage.content[2].header}</p>
                        </div>
                        <div class="card-footer">
                            <div class="post-preview-meta">
                                <img class="post-preview-meta-img"
                                     src="${postPage.content[2].writer.profileImage}"/>
                                <div class="post-preview-meta-details">
                                    <div class="post-preview-meta-details-name">${postPage.content[2].writer.userName}
                                        (${postPage.content[2].writer.email})
                                    </div>
                                    <div class="post-preview-meta-details-date">${postPage.content[2].modifiedDate}</div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </c:if>
            <c:if test="${!empty postPage.content[3]}">
                <div class="col-md-6 col-xl-4 mb-5">
                    <a class="card post-preview lift h-100" href="${postPage.content[3].url}"
                    ><img class="card-img-top" src="${postPage.content[3].thumbNail}"
                          alt="..."/>
                        <div class="card-body">
                            <h5 class="card-title">${postPage.content[3].title}</h5>
                            <p class="card-text">${postPage.content[3].header}</p>
                        </div>
                        <div class="card-footer">
                            <div class="post-preview-meta">
                                <img class="post-preview-meta-img"
                                     src="${postPage.content[3].writer.profileImage}"/>
                                <div class="post-preview-meta-details">
                                    <div class="post-preview-meta-details-name">${postPage.content[3].writer.userName}
                                        (${postPage.content[3].writer.email})
                                    </div>
                                    <div class="post-preview-meta-details-date">${postPage.content[3].modifiedDate}</div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </c:if>
            <c:if test="${!empty postPage.content[4]}">
                <div class="col-md-6 col-xl-4 mb-5">
                    <a class="card post-preview lift h-100" href="${postPage.content[4].url}"
                    ><img class="card-img-top" src="${postPage.content[4].thumbNail}"
                          alt="..."/>
                        <div class="card-body">
                            <h5 class="card-title">${postPage.content[4].title}</h5>
                            <p class="card-text">${postPage.content[4].header}</p>
                        </div>
                        <div class="card-footer">
                            <div class="post-preview-meta">
                                <img class="post-preview-meta-img"
                                     src="${postPage.content[4].writer.profileImage}"/>
                                <div class="post-preview-meta-details">
                                    <div class="post-preview-meta-details-name">${postPage.content[4].writer.userName}
                                        (${postPage.content[4].writer.email})
                                    </div>
                                    <div class="post-preview-meta-details-date">${postPage.content[4].modifiedDate}</div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </c:if>
            <c:if test="${!empty postPage.content[5]}">
                <div class="col-md-6 col-xl-4 mb-5">
                    <a class="card post-preview lift h-100" href="${postPage.content[5].url}"
                    ><img class="card-img-top" src="${postPage.content[5].thumbNail}"
                          alt="..."/>
                        <div class="card-body">
                            <h5 class="card-title">${postPage.content[5].title}</h5>
                            <p class="card-text">${postPage.content[5].header}</p>
                        </div>
                        <div class="card-footer">
                            <div class="post-preview-meta">
                                <img class="post-preview-meta-img"
                                     src="${postPage.content[5].writer.profileImage}"/>
                                <div class="post-preview-meta-details">
                                    <div class="post-preview-meta-details-name">${postPage.content[5].writer.userName}
                                        (${postPage.content[5].writer.email})
                                    </div>
                                    <div class="post-preview-meta-details-date">${postPage.content[5].modifiedDate}</div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </c:if>
            <c:if test="${!empty postPage.content[6]}">
            <div class="col-md-6 col-xl-4 mb-5">
                <a class="card post-preview lift h-100" href="${postPage.content[6].url}"
                ><img class="card-img-top" src="${postPage.content[6].thumbNail}" alt="..."/>
                    <div class="card-body">
                        <h5 class="card-title">${postPage.content[6].title}</h5>
                        <p class="card-text">${postPage.content[6].header}</p>
                    </div>
                    <div class="card-footer">
                        <div class="post-preview-meta">
                            <img class="post-preview-meta-img"
                                 src="${postPage.content[6].writer.profileImage}"/>
                            <div class="post-preview-meta-details">
                                <div class="post-preview-meta-details-name">${postPage.content[6].writer.userName}
                                    (${postPage.content[6].writer.email})
                                </div>
                                <div class="post-preview-meta-details-date">${postPage.content[6].modifiedDate}</div>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
        </div>
        </c:if>
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
    <div class="svg-border-rounded text-dark">
        <!-- Rounded SVG Border-->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 144.54 17.34" preserveAspectRatio="none"
             fill="currentColor">
            <path d="M144.54,17.34H0V0H144.54ZM0,0S32.36,17.34,72.27,17.34,144.54,0,144.54,0"></path>
        </svg>
    </div>
</section>
