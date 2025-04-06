<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<script>
    $(window).ready(function () {
        let message = '<c:out value = "${message}"/>'
        if (textCheck(message)) {
            alert(message);
            window.location.replace('/');
        }
    });

    function getComments(pageNo = 0) {
        if (!($('input[name="pageNo"]').val() === undefined))
            pageNo = $('input[name="pageNo"]').val();

        $.ajax({
            url: '/my/info/comments?page=' + pageNo,
            method: "get",
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                let commentsList = $('.comments-list');

                if (data.empty) {
                    $('.btn-more').remove();
                    commentsList.append(`<button class="list-group-item list-group-item-action py-4 btn-more" href="#!">
                                                <div class="text-center small">해당 영역이 끝입니다.</div>
                                            </button>`);
                }
                for (let i in data.content) {
                    let title = data.content[i].postTitle.substr(0, 25);

                    let html = `<a class="list-group-item list-group-item-action py-4" href="/post/\${data.content[i].postId}">
                        <div class="d-flex justify-content-between">
                            <div class="mr-4 d-flex">
                                <img class="rounded-circle mr-3" src="\${data.content[i].writer.profileImage}" width="40" alt="image">
                                <div>
                                    <h6>\${data.content[i].content}</h6>
                                    <p class="card-text">\${title}...에서 댓글을 남겼습니다</p>
                                </div>
                            </div>
                            <div class="small text-gray-400 flex-shrink-0 text-right">\${data.content[i].modifiedDate}<br />
                                <div class="badge badge-green-soft badge-pill text-green">\${data.content[i].status}</div>
                            </div>
                        </div></a>`;

                    commentsList.prepend(html);
                }
                let nextPage = Number(pageNo) + 1;
                commentsList.prepend(`<input type='hidden' name='pageNo' value = \${nextPage} />`);

            }
        });
    }

    function saveProfile(input) {
        if (input.files && input.files[0]) {
            let form_data = new FormData();
            form_data.append('file', input.files[0]);
            $.ajax({
                data: form_data,
                type: "POST",
                url: '/my/image',
                cache: false,
                contentType: false,
                enctype: 'multipart/form-data',
                processData: false,
                success: function (img_name) {
                    document.querySelector("#thumbnail").src = img_name;
                }
            });
        }
    }

    function saveMyInfo() {

        let userName = document.querySelector("#username").value;
        let email = document.querySelector("#email").value;
        let recvMail = (isCheck('mail-agree')) ? 1 : 0;
        let profileImage = document.querySelector("#thumbnail").src;

        let accountInfo = {
            userName: userName,
            email: email,
            profileImage: profileImage,
            recvMail: recvMail
        };

        $.ajax({
            url: '/my/info',
            method: "post",
            data: JSON.stringify(accountInfo),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                showModal('알림창', data.response);

                setTimeout(function () {
                    window.location.reload();
                }, 2000);

            }
        });
    }

    function updatePassword() {
        $.ajax({
            url: '/my/info/password',
            method: "get",
            dataType: 'html',
            success: function (data) {
                showModalWithFooter('계정 패스워드 변경하기', data, `<button type="submit" class="btn-submit btn btn-sm btn-primary btn-block">변경하기</button>`);
            }
        });
    }

    function updateEmail() {
        $.ajax({
            url: '/my/info/email',
            method: "get",
            dataType: 'html',
            success: function (data) {
                showModalWithFooter('이메일 변경하기', data, `<button type="submit" class="btn-submit btn btn-sm btn-primary btn-block">변경하기</button>`);
            }
        });
    }

</script>

<!-- Page Header-->
<header class="page-header page-header-dark bg-img-cover overlay overlay-60"
        style="background-image: url(https://cdn.pixabay.com/photo/2018/02/03/09/51/bulletin-board-3127287_960_720.jpg)">
    <div class="page-header-content">
        <div class="container text-center">
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <h1 class="page-header-title mb-3">내 정보조회</h1>
                    <p class="page-header-text">정보 수정, 내 댓글 조회가 가능합니다</p>
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
        <ul class="nav nav-tabs mb-3">
            <li class="nav-item">
                <button class="nav-link active" data-toggle="tab" href="#basic">기본정보</button>
            </li>
            <li class="nav-item">
                <button class="nav-link" data-toggle="tab" href="#postInfo" onclick="getComments()">작성 댓글
                </button>
            </li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane fade show active" id="basic">
                <div class="col-lg-10">
                    <%--
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">HOME</li>
                            <li class="breadcrumb-item active">내 정보</li>
                        </ol>--%>

                    <div class="bg-light p-2">
                        <div class="d-md-flex d-sm-block flex-row align-items-start">
                            <div class="col-md-4 col-sm-8 col-xs-8 order-md-1">
                                <figure class="figure">
                                    <figcaption class="figure-caption text-center">프로필 사진</figcaption>
                                    <img src="${myInfo.profileImage}"
                                         class="figure-img img-fluid img-thumbnail"
                                         id="thumbnail" alt="썸네일"/>
                                    <!-- 버튼 클릭시 파일찾기로 링크해 준다 -->
                                    <input type="button" class="btn btn-primary btn-sm btn-block"
                                           value="내 PC에서 가져오기" onclick="getImageFile();">
                                    <!-- 화면에서 파일찾기를 안보이게 한다. -->
                                    <input type="file" onchange="saveProfile(this)" name="file" id="file"
                                           style="display: none;"/>
                                </figure>
                            </div>

                            <div class="col-md-8 col-sm-8 col-xs-8 order-md-1">
                                <h4 class="mb-3">My Infomation</h4>
                                <form class="needs-validation" novalidate="">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="username">성함/닉네임</label>
                                            <input type="text" class="form-control" id="username"
                                                   placeholder="성함을 입력하세요" value="${myInfo.userName}"
                                                   required="">
                                            <div class="invalid-feedback">
                                                성함이 비어있습니다.
                                            </div>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="username">비밀번호</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="pwd"
                                                   required="" value="******************" disabled>
                                            <button class="btn btn-primary btn-sm btn-xs" type="button"
                                                    onclick="updatePassword();">비밀번호
                                                변경
                                            </button>
                                        </div>
                                        <small id="emailHelp" class="form-text text-muted">현재 비밀번호를 확인하고,
                                            새로운 비밀번호로 변경합니다.</small>
                                    </div>

                                    <div class="mb-3">
                                        <label for="email">이메일</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="email"
                                                   placeholder="이메일을 입력하세요" required=""
                                                   value="${myInfo.email}">
                                            <div class="invalid-feedback" style="width: 100%;">
                                                이메일이 비어있습니다.
                                            </div>
                                            <button class="btn btn-primary btn-sm btn-xs" type="button"
                                                    onclick="updateEmail();">이메일
                                                변경하기
                                            </button>
                                        </div>
                                        <small id="emailHelp" class="form-text text-muted">신규 메일 인증이
                                            필요합니다.</small>
                                    </div>

                                    <div class="mb-3">
                                        <label for="role">권한</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="role"
                                                   placeholder="권한 입력하세요" required=""
                                                   value="${myInfo.role}"
                                                   disabled>
                                            <div class="invalid-feedback" style="width: 100%;">
                                                권한이 비어있습니다.
                                            </div>
                                        </div>
                                    </div>

                                    <hr class="mb-4">

                                    <h4 class="mb-3">Agree to Receive Mail</h4>
                                    <div class="custom-control custom-checkbox">
                                        <c:choose>
                                            <c:when test="${myInfo.recvMail eq true}">
                                                <input type="checkbox" class="custom-control-input"
                                                       id="mail-agree" checked>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="checkbox" class="custom-control-input"
                                                       id="mail-agree">
                                            </c:otherwise>
                                        </c:choose>

                                        <label class="custom-control-label" for="mail-agree">광고성 메일 수신에
                                            동의합니다.</label>
                                    </div>

                                    <hr class="mb-4">

                                    <h4 class="mb-3">Payment</h4>

                                    <div class="d-block my-3">
                                        <div class="custom-control custom-radio">
                                            <input id="credit" name="paymentMethod" type="radio"
                                                   class="custom-control-input" checked="" required="">
                                            <label class="custom-control-label" for="credit">신용카드</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input id="bank" name="paymentMethod" type="radio"
                                                   class="custom-control-input" required="">
                                            <label class="custom-control-label" for="bank">계좌이체</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input id="vcnt" name="paymentMethod" type="radio"
                                                   class="custom-control-input" required="">
                                            <label class="custom-control-label" for="vcnt">가상계좌</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input id="paypal" name="paymentMethod" type="radio"
                                                   class="custom-control-input" required="">
                                            <label class="custom-control-label"
                                                   for="paypal">페이팔</label>
                                        </div>
                                    </div>

                                    <hr class="mb-4">
                                    <hr class="my-5"/>
                                    <button class="btn btn-primary btn-lg btn-block" type="button"
                                            onclick="saveMyInfo()">
                                        저장하기
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="postInfo">
                <div class="container">
                    <div class="input-group input-group-lg mb-5">
                        <div class="input-group-prepend">
                            <button class="btn btn-primary dropdown-toggle" type="button"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">검색
                            </button>
                            <div class="dropdown-menu animated--fade-in-up"><a class="dropdown-item"
                                                                               href="#!">게시물 제목</a><a
                                    class="dropdown-item" href="#!">댓글 내용</a></div>
                        </div>
                        <input class="form-control" type="text" aria-label="Text input with dropdown button"
                               placeholder="검색어를 입력하세요"/>
                    </div>
                    <div class="row">
                        <div class="col-lg-4 col-xl-3 mb-5">
                            <div class="card">
                                <div class="list-group list-group-flush small">
                                    <a class="list-group-item list-group-item-action" href="#!"><i
                                            class="fas fa-plus fa-fw mr-2 text-gray-400"></i>Make New Ticket</a><a
                                        class="list-group-item list-group-item-action" href="#!"><i
                                        class="fas fa-file-alt fa-fw mr-2 text-gray-400"></i>View Open
                                    Tickets</a><a class="list-group-item list-group-item-action"
                                                  href="#!"><i
                                        class="fas fa-archive fa-fw mr-2 text-gray-400"></i>View Closed
                                    Tickets</a><a class="list-group-item list-group-item-action"
                                                  href="#!"><i
                                        class="fas fa-star fa-fw mr-2 text-gray-400"></i>View Starred
                                    Tickets</a><a class="list-group-item list-group-item-action"
                                                  href="#!"><i
                                        class="far fa-handshake fa-fw mr-2 text-gray-400"></i>Managed
                                    Services</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-8 col-xl-9">
                            <div class="card mb-2">
                                <div class="list-group list-group-flush comments-list">
                                    <!-- Comment List -->

                                    <button class="list-group-item list-group-item-action py-4 btn-more" href="#!"
                                            onclick="getComments();">
                                        <div class="text-center small">View More Comments</div>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="detailInfo">
            </div>


            <div class="svg-border-rounded text-dark">
                <!-- Rounded SVG Border-->
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 144.54 17.34"
                     preserveAspectRatio="none"
                     fill="currentColor">
                    <path d="M144.54,17.34H0V0H144.54ZM0,0S32.36,17.34,72.27,17.34,144.54,0,144.54,0"></path>
                </svg>
            </div>
</section>

