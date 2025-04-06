<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<script>
    function guestbookEdit(bookId, isSecret) {
        let footer = `
            <div class="custom-control custom-switch">
                <input name="secretSwitchModal" type="checkbox" class="custom-control-input" id="secretSwitchModal">
                <label class="custom-control-label" for="secretSwitchModal">블로그 주인만 보기</label>
               <button class = "btn btn-sm btn-primary btn-submit ml-3">작성하기</button>
            </div>
        `;

        if (isSecret)
            footer = `
            <div class="custom-control custom-switch">
                <input name="secretSwitchModal" checked type="checkbox" class="custom-control-input" id="secretSwitchModal">
                <label class="custom-control-label" for="secretSwitchModal">블로그 주인만 보기</label>
               <button class = "btn btn-sm btn-primary btn-submit ml-3">작성하기</button>
            </div>
        `;

        $.ajax({
            url: '/guestbook/' + bookId,
            method: "post",
            dataType: 'html',
            success: function (data) {
                showModalWithFooter('방명록 수정하기', data, footer);
            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    function deleteCall(bookId) {
        if (confirm("정말로 삭제하시겠습니까?")) {
            guestbookDelete(bookId);
        }
    }

    function guestbookDelete(bookId) {
        $.ajax({
            url: '/guestbook/' + bookId,
            method: "delete",
            dataType: 'json',
            success: function (data) {
                showModal('R.LOG 글 관리', data.response);

                setTimeout(function () {
                    window.location.reload();
                }, 2000);

            }
        });
    }

    function sendGuestbook(parentId = 0) {
        // 하위 방명록의 경우, modal의 textarea 값읅 가져옴
        let content = (parentId === 0) ? $('textarea[name=content]').val() : $('div.modal-body').find('textarea[name="content"]').val();
        let statusCheck = (parentId === 0) ? $("input:checkbox[name='secretSwitch']").is(":checked") : $('div.modal-footer').find("input:checkbox[name='secretSwitchModal']").is(":checked");
        let status = "ENABLE";
        if (!textCheck(content)) {
            showModal('알림창', '빈 내용은 작성할 수 없습니다.');
            return;
        }

        if (statusCheck)
            status = "SECRET";

        let guestbook = {
            content: content,
            status: status,
            parentId: parentId
        };

        $.ajax({
            url: '/guestbook',
            method: "post",
            data: JSON.stringify(guestbook),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                showModal('알림창', data.response);

                if (data.code === '403') {
                    setTimeout(function () {
                        window.location.href = '/login';
                    }, 2000);
                } else {
                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);
                }

            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    function replyCall(parentId = 0) {
        let footer = `
            <div class="custom-control custom-switch">
                <input name="secretSwitchModal" type="checkbox" class="custom-control-input" id="secretSwitchModal">
                <label class="custom-control-label" for="secretSwitchModal">비밀로 남기기</label>
               <button type="button" class = "btn btn-sm btn-primary btn-submit ml-3" onClick="sendGuestbook(\${parentId});">작성하기</button>
            </div>`;

        showModalWithFooter('방명록 댓글작성'
            , ` <textarea name="content" class="form-control ml-1 shadow-none textarea"
                  placeholder="여러분들의 소중한 의견을 남겨주세요"></textarea>`
            , footer);
    }
</script>

<!-- Page Header-->
<header class="page-header page-header-dark bg-img-cover overlay overlay-60"
        style="background-image: url(https://source.unsplash.com/xZgvBXDB9wE/1600x900)">
    <div class="page-header-content">
        <div class="container text-center">
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <h1 class="page-header-title mb-3">방명록</h1>
                    <p class="page-header-text mb-0">블로그 작성자에게 글을 남겨보세요</p>
                </div>
            </div>
        </div>
    </div>
    <div class="svg-border-rounded text-light">
        <!-- Rounded SVG Border-->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 144.54 17.34" preserveAspectRatio="none"
             fill="currentColor">
            <path d="M144.54,17.34H0V0H144.54ZM0,0S32.36,17.34,72.27,17.34,144.54,0,144.54,0"></path>
        </svg>
    </div>
</header>
<section class="bg-light py-10">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="post-archive-tag">GuestBook</div>
                <div class="bg-light p-2">
                    <div class="d-flex flex-row align-items-start">
                        <sec:authorize access="isAuthenticated()">
                            <img class="rounded-circle"
                                 src="<sec:authentication property="principal.account.profileImage"/>"
                                 width="40"
                                 alt="image"/>
                        </sec:authorize>

                        <sec:authorize access="isAnonymous()">
                            <img class="rounded-circle"
                                 src="/assets/img/illustrations/profiles/profile-1.png" width="40"
                                 alt="image"/>
                        </sec:authorize>
                        <textarea name="content" class="form-control ml-1 shadow-none textarea"
                                  placeholder="여러분들의 소중한 의견을 남겨주세요"></textarea>
                    </div>
                    <div class="mt-2 text-right">
                        <div class="custom-control custom-switch">
                            <input name="secretSwitch" type="checkbox" class="custom-control-input"
                                   id="secretSwitch">
                            <label class="custom-control-label" for="secretSwitch">블로그 주인만 보기</label>
                            <button class="btn btn-primary btn-sm shadow-none ml-3"
                                    onClick="sendGuestbook()">작성하기
                            </button>
                        </div>
                    </div>
                </div>
                <hr class="my-5"/>

                <c:if test="${!empty guestbookPage.content[0]}">
                    <div class="float-right">

                        <c:if test="${guestbookPage.content[0].mine eq true}">
                            <a class="btn btn-sm btn-warning"
                               onclick="guestbookEdit(${guestbookPage.content[0].id}, ${guestbookPage.content[0].status eq 'SECRET'});"><i
                                    class="far fa-edit"></i></a>
                            <a class="btn btn-sm btn-danger"
                               onclick="deleteCall(${guestbookPage.content[0].id})"><i
                                    class="fas fa-trash-alt"></i></a>
                        </c:if>
                        <a class="btn btn-sm btn-primary"
                           onclick="replyCall(${guestbookPage.content[0].id})"><i
                                class="fas fa-reply"></i></a>
                    </div>

                    <div class="post-archive-item">
                        <c:if test="${guestbookPage.content[0].status eq 'SECRET'}">
                            <p>[비밀 방명록]</p>
                        </c:if>
                        <p><c:out value="${guestbookPage.content[0].content}"/></p>
                    </div>

                    <div class="post-archive-meta">
                        <img class="post-archive-meta-img"
                             src="<c:out value = "${guestbookPage.content[0].writer.profileImage}"/>"/>
                        <div class="post-archive-meta-details">
                            <div class="post-archive-meta-details-name"><c:out
                                    value="${guestbookPage.content[0].writer.userName}"/></div>
                            <div class="post-archive-meta-details-date"><c:out
                                    value="${guestbookPage.content[0].modifiedDate}"/></div>
                        </div>
                    </div>
                    <hr class="my-5"/>
                </c:if>

                <c:if test="${!empty guestbookPage.content[1]}">
                    <c:if test="${guestbookPage.content[1].mine eq true}">
                        <div class="float-right">
                            <a class="btn btn-sm btn-warning"
                               onclick="guestbookEdit(${guestbookPage.content[1].id}, ${guestbookPage.content[1].status eq 'SECRET'});"><i
                                    class="far fa-edit"></i></a>
                            <a class="btn btn-sm btn-danger"
                               onclick="deleteCall(${guestbookPage.content[1].id})"><i
                                    class="fas fa-trash-alt"></i></a>
                        </div>
                    </c:if>
                    <div class="post-archive-item">
                        <c:if test="${guestbookPage.content[1].status eq 'SECRET'}">
                            <p>[비밀 방명록]</p>
                        </c:if>
                        <p><c:out value="${guestbookPage.content[1].content}"/></p>
                    </div>
                    <div class="post-archive-meta">
                        <img class="post-archive-meta-img"
                             src="<c:out value = "${guestbookPage.content[1].writer.profileImage}"/>"/>
                        <div class="post-archive-meta-details">
                            <div class="post-archive-meta-details-name"><c:out
                                    value="${guestbookPage.content[1].writer.userName}"/></div>
                            <div class="post-archive-meta-details-date"><c:out
                                    value="${guestbookPage.content[1].modifiedDate}"/></div>
                        </div>
                    </div>
                    <hr class="my-5"/>
                </c:if>

                <c:if test="${!empty guestbookPage.content[2]}">
                    <c:if test="${guestbookPage.content[2].mine eq true}">
                        <div class="float-right">
                            <a class="btn btn-sm btn-warning"
                               onclick="guestbookEdit(${guestbookPage.content[2].id}, ${guestbookPage.content[2].status eq 'SECRET'});"><i
                                    class="far fa-edit"></i></a>
                            <a class="btn btn-sm btn-danger"
                               onclick="deleteCall(${guestbookPage.content[2].id})"><i
                                    class="fas fa-trash-alt"></i></a>
                        </div>
                    </c:if>
                    <div class="post-archive-item">
                        <c:if test="${guestbookPage.content[2].status eq 'SECRET'}">
                            <p>[비밀 방명록]</p>
                        </c:if>
                        <p><c:out value="${guestbookPage.content[2].content}"/></p>
                    </div>
                    <div class="post-archive-meta">
                        <img class="post-archive-meta-img"
                             src="<c:out value = "${guestbookPage.content[2].writer.profileImage}"/>"/>
                        <div class="post-archive-meta-details">
                            <div class="post-archive-meta-details-name"><c:out
                                    value="${guestbookPage.content[2].writer.userName}"/></div>
                            <div class="post-archive-meta-details-date"><c:out
                                    value="${guestbookPage.content[2].modifiedDate}"/></div>
                        </div>
                    </div>
                    <hr class="my-5"/>
                </c:if>

                <c:if test="${!empty guestbookPage.content[3]}">
                    <c:if test="${guestbookPage.content[3].mine eq true}">
                        <div class="float-right">
                            <a class="btn btn-sm btn-warning"
                               onclick="guestbookEdit(${guestbookPage.content[3].id}, ${guestbookPage.content[3].status eq 'SECRET'});"><i
                                    class="far fa-edit"></i></a>
                            <a class="btn btn-sm btn-danger"
                               onclick="deleteCall(${guestbookPage.content[3].id})"><i
                                    class="fas fa-trash-alt"></i></a>
                        </div>
                    </c:if>
                    <div class="post-archive-item">
                        <c:if test="${guestbookPage.content[3].status eq 'SECRET'}">
                            <p>[비밀 방명록]</p>
                        </c:if>
                        <p><c:out value="${guestbookPage.content[3].content}"/></p>
                    </div>
                    <div class="post-archive-meta">
                        <img class="post-archive-meta-img"
                             src="<c:out value = "${guestbookPage.content[3].writer.profileImage}"/>"/>
                        <div class="post-archive-meta-details">
                            <div class="post-archive-meta-details-name"><c:out
                                    value="${guestbookPage.content[3].writer.userName}"/></div>
                            <div class="post-archive-meta-details-date"><c:out
                                    value="${guestbookPage.content[3].modifiedDate}"/></div>
                        </div>
                    </div>
                    <hr class="my-5"/>
                </c:if>


                <c:if test="${!empty guestbookPage.content[4]}">
                    <c:if test="${guestbookPage.content[4].mine eq true}">
                        <div class="float-right">
                            <a class="btn btn-sm btn-warning"
                               onclick="guestbookEdit(${guestbookPage.content[4].id}, ${guestbookPage.content[4].status eq 'SECRET'});"><i
                                    class="far fa-edit"></i></a>
                            <a class="btn btn-sm btn-danger"
                               onclick="deleteCall(${guestbookPage.content[4].id})"><i
                                    class="fas fa-trash-alt"></i></a>
                        </div>
                    </c:if>
                    <div class="post-archive-item">
                        <c:if test="${guestbookPage.content[4].status eq 'SECRET'}">
                            <p>[비밀 방명록]</p>
                        </c:if>
                        <p><c:out value="${guestbookPage.content[4].content}"/></p>
                    </div>
                    <div class="post-archive-meta">
                        <img class="post-archive-meta-img"
                             src="<c:out value = "${guestbookPage.content[4].writer.profileImage}"/>"/>
                        <div class="post-archive-meta-details">
                            <div class="post-archive-meta-details-name"><c:out
                                    value="${guestbookPage.content[4].writer.userName}"/></div>
                            <div class="post-archive-meta-details-date"><c:out
                                    value="${guestbookPage.content[4].modifiedDate}"/></div>
                        </div>
                    </div>
                    <hr class="my-5"/>
                </c:if>

                <c:if test="${!empty guestbookPage.content[5]}">
                    <c:if test="${guestbookPage.content[5].mine eq true}">
                        <div class="float-right">
                            <a class="btn btn-sm btn-warning"
                               onclick="guestbookEdit(${guestbookPage.content[5].id}, ${guestbookPage.content[5].status eq 'SECRET'});"><i
                                    class="far fa-edit"></i></a>
                            <a class="btn btn-sm btn-danger"
                               onclick="deleteCall(${guestbookPage.content[5].id})"><i
                                    class="fas fa-trash-alt"></i></a>
                        </div>
                    </c:if>
                    <div class="post-archive-item">
                        <c:if test="${guestbookPage.content[5].status eq 'SECRET'}">
                            <p>[비밀 방명록]</p>
                        </c:if>
                        <p><c:out value="${guestbookPage.content[5].content}"/></p>
                    </div>
                    <div class="post-archive-meta">
                        <img class="post-archive-meta-img"
                             src="<c:out value = "${guestbookPage.content[5].writer.profileImage}"/>"/>
                        <div class="post-archive-meta-details">
                            <div class="post-archive-meta-details-name"><c:out
                                    value="${guestbookPage.content[5].writer.userName}"/></div>
                            <div class="post-archive-meta-details-date"><c:out
                                    value="${guestbookPage.content[5].modifiedDate}"/></div>
                        </div>
                    </div>
                    <hr class="my-5"/>
                </c:if>

                <c:if test="${!empty guestbookPage.content[6]}">
                    <c:if test="${guestbookPage.content[6].mine eq true}">
                        <div class="float-right">
                            <a class="btn btn-sm btn-warning"
                               onclick="guestbookEdit(${guestbookPage.content[6].id}, ${guestbookPage.content[6].status eq 'SECRET'});"><i
                                    class="far fa-edit"></i></a>
                            <a class="btn btn-sm btn-danger"
                               onclick="deleteCall(${guestbookPage.content[6].id})"><i
                                    class="fas fa-trash-alt"></i></a>
                        </div>
                    </c:if>
                    <div class="post-archive-item">
                        <c:if test="${guestbookPage.content[6].status eq 'SECRET'}">
                            <p>[비밀 방명록]</p>
                        </c:if>
                        <p><c:out value="${guestbookPage.content[6].content}"/></p>
                    </div>
                    <div class="post-archive-meta">
                        <img class="post-archive-meta-img"
                             src="<c:out value = "${guestbookPage.content[6].writer.profileImage}"/>"/>
                        <div class="post-archive-meta-details">
                            <div class="post-archive-meta-details-name"><c:out
                                    value="${guestbookPage.content[6].writer.userName}"/></div>
                            <div class="post-archive-meta-details-date"><c:out
                                    value="${guestbookPage.content[6].modifiedDate}"/></div>
                        </div>
                    </div>
                    <hr class="my-5"/>
                </c:if>

            </div>
        </div>
        <nav aria-label="Page navigation example">
            <ul class="pagination pagination-blog justify-content-center">
                <c:choose>
                    <c:when test="${guestbookPage.first}">
                        <li class="page-item disabled">
                            <a class="page-link" href="?page=${guestbookPage.number}" aria-label="Previous">
                                <span aria-hidden="true">«</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="?page=$0">처음</a>
                        </li>

                        <li class="page-item">
                            <a class="page-link" href="?page=${guestbookPage.number-1}"
                               aria-label="Previous">
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
                            <a class="page-link" href="?page=0">${guestbookPage.pageable.pageNumber+1}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
                            <c:choose>
                                <c:when test="${guestbookPage.pageable.pageNumber+1  == i}">
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
                    <c:when test="${guestbookPage.last}">
                        <li class="page-item disabled">
                            <a class="page-link" href="?page=${guestbookPage.number}" aria-label="Previous">
                                <span aria-hidden="true">»</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item ">
                            <a class="page-link" href="?page=${guestbookPage.number+1}">»</a>
                        </li>
                        <li class="page-item ">
                            <a class="page-link" href="?page=${guestbookPage.totalPages-1}">마지막</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
    <div class="svg-border-rounded text-dark">
        <!-- Rounded SVG Border-->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 144.54 17.34" preserveAspectRatio="none"
             fill="currentColor">
            <path d="M144.54,17.34H0V0H144.54ZM0,0S32.36,17.34,72.27,17.34,144.54,0,144.54,0"></path>
        </svg>
    </div>
</section>

