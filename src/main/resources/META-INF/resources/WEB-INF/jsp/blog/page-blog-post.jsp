<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>



<script>
    $(document).ready(function () {
        let message = '<c:out value = "${message}"/>'
        if (textCheck(message)) {
            alert(message);
            window.location.href = '/';
        }
        goComments();
    });

    function snsShare(snsName, title) {

        if (title === null) return false;

        let link = window.location.href;
        let snsPopUp;
        let _width = '500';
        let _height = '450';
        let _left = Math.ceil((window.screen.width - _width) / 2);
        let _top = Math.ceil((window.screen.height - _height) / 2);

        switch (snsName) {
            case 'facebook':
                snsPopUp = window.open("http://www.facebook.com/sharer/sharer.php?u=" + link, '', 'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top);
                break;

            case 'twitter' :
                snsPopUp = window.open("http://twitter.com/intent/tweet?url=" + link + "&text=" + title, '', 'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top);
                break;

            case 'kakao' :
                snsPopUp = window.open("https://story.kakao.com/share?url=" + link, '', 'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top);
                break;

            case 'addurl' :
                let dummy = document.createElement("textarea");
                document.body.appendChild(dummy);
                dummy.value = link;
                dummy.select();
                document.execCommand("copy");
                document.body.removeChild(dummy);
                alert("URL이 클립보드에 복사되었습니다.");
                break;
        }
    }

    function goComments() {
        $.ajax({
            url: '/comments/<c:out value = "${post.id}"/>',
            dataType: 'json',
            success: function (data) {
                showComments(data, 1);
            }
        });
    }

    function showComments(root, depth) {
        for (let i in root.subComments) {
            let id = root.subComments[i].commentId;
            let content = root.subComments[i].content;
            let isMine = root.subComments[i].mine;
            let email = root.subComments[i].writer.email;
            let userName = root.subComments[i].writer.userName;
            let modifiedDate = root.subComments[i].modifiedDate;
            let profileImage = root.subComments[i].writer.profileImage;
            let status = root.subComments[i].status;
            let likeCount = root.subComments[i].likeCount;
            let myLike = root.subComments[i].myLike;
            drawComments(id, content, isMine, email, userName, modifiedDate, profileImage, status, likeCount, myLike, depth);
            showComments(root.subComments[i], depth + 1);
        }
    }

    function commentEdit(id, status) {
        let footer = `
            <div class="custom-control custom-switch">
                <input name="secretSwitchModal" type="checkbox" class="custom-control-input" id="secretSwitchModal">
                <label class="custom-control-label" for="secretSwitchModal">블로그 주인만 보기</label>
               <button class = "btn btn-sm btn-primary btn-submit ml-3">작성하기</button>
            </div>
        `;

        if (status === 'SECRET')
            footer = `
            <div class="custom-control custom-switch">
                <input name="secretSwitchModal" checked type="checkbox" class="custom-control-input" id="secretSwitchModal">
                <label class="custom-control-label" for="secretSwitchModal">블로그 주인만 보기</label>
               <button class = "btn btn-sm btn-primary btn-submit ml-3">작성하기</button>
            </div>
        `;

        $.ajax({
            url: '/comment/' + id,
            method: "post",
            dataType: 'html',
            success: function (data) {
                showModalWithFooter('댓글 수정', data, footer);
            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    function deleteCommentCall(id) {
        if (confirm("정말로 삭제하시겠습니까?")) {
            guestbookDelete(id);
        }
    }

    function guestbookDelete(id) {
        $.ajax({
            url: '/comment/' + id,
            method: "delete",
            dataType: 'json',
            success: function (data) {
                showModal('알림창', data.response);

                setTimeout(function () {
                    window.location.reload();
                }, 2000);

            }
        });
    }


    <c:choose>
    <c:when test = "${post.postLikes eq true}">

    function postUnLike() {
        $.ajax({
            url: window.location.pathname + '/like',
            method: "put",
            dataType: 'json',
            success: function (data) {
                showModal('알림창', data.response);

                setTimeout(function () {
                    window.location.reload();
                }, 2000);

            }
        });
    }

    </c:when>
    <c:otherwise>

    function postLike() {
        $.ajax({
            url: window.location.pathname + '/like',
            method: "post",
            dataType: 'json',
            success: function (data) {
                showModal('알림창', data.response);

                setTimeout(function () {
                    window.location.reload();
                }, 2000);

            }
        });
    }

    </c:otherwise>
    </c:choose>



    <sec:authorize access="hasRole('ADMIN')">

    function drawComments(id = '', content = '', isMine = false, email = '', userName = '', modifiedDate = '', profileImage = '', status = '', likeCount = '0', myLike = false, depth = 1) {
        let margin = 5 * (depth - 1);
        let isSecret = '';

        if (status === 'SECRET')
            isSecret = '[비밀댓글]  ';

        if (isMine === true) {
            $("#comments").append(`
                                    <div class="bg-light p-2 ml-\${margin}">
                                        <div class="float-right">
                                            <div class="d-flex flex-row fs-7">
                                                <div class="like p-2 cursor">
                                                 ` + commentLikeDOM(myLike, likeCount, id) + `
                                                </div>
                                                <div class="comment p-2 cursor action-collapse" data-toggle="collapse" aria-expanded="true"
                                                     aria-controls="collapse-1"
                                                     onclick="addChildForm(this, '\${id}')"><i
                                                        class="far fa-comment-dots"></i><span class="ml-1">댓글쓰기</span></div>
                                                <div class="p-2 cursor">
                                                    <a onclick="commentEdit('\${id}', '\${status}');"><i class="far fa-edit"></i><span class='ml-1'>수정</span></a>
                                                </div>
                                                <div class="p-2 cursor">
                                                    <a onclick="deleteCommentCall(\${id})"><i class="fas fa-eraser"></i><span class='ml-1'>삭제</span></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-start user-info">
                                            <img class="rounded-circle" src= "\${profileImage}" width="40">
                                            <div class="d-flex flex-column justify-content-start ml-2"><span
                                                    class="d-block font-weight-bold name">\${userName}</span><span
                                                    class="date text-black-50">\${modifiedDate}</span></div>
                                        </div>
                                        <div class="mt-2">
                                            <p class="comment-text">
                                                \${isSecret}
                                                \${content}
                                            </p>
                                        </div>
                                    </div>
                        `);
        } else {
            $("#comments").append(`
                                    <div class="bg-light p-2 ml-\${margin}">
                                        <div class="float-right">
                                            <div class="d-flex flex-row fs-12">
                                                <div class="like p-2 cursor">
                                                 ` + commentLikeDOM(myLike, likeCount, id) + `
                                                </div>
                                                <div class="comment p-2 cursor action-collapse" data-toggle="collapse" aria-expanded="true"
                                                     aria-controls="collapse-1"
                                                     onclick="addChildForm(this, '\${id}')"><i
                                                        class="far fa-comment-dots"></i><span class="ml-1">댓글쓰기</span></div>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-start user-info">
                                            <img class="rounded-circle" src= "\${profileImage}" width="40">
                                            <div class="d-flex flex-column justify-content-start ml-2"><span
                                                    class="d-block font-weight-bold name">\${userName}</span><span
                                                    class="date text-black-50">\${modifiedDate}</span></div>
                                        </div>
                                        <div class="mt-2">
                                            <p class="comment-text">
                                                \${isSecret}
                                                \${content}
                                            </p>
                                        </div>
                                    </div>
                        `);
        }
        $("#comments").append('<hr>');
    }

    </sec:authorize>
    <sec:authorize access="!hasRole('ADMIN')">

    function drawComments(id = '', content = '', isMine = false, email = '', userName = '', modifiedDate = '', profileImage = '', status = '', likeCount = '0', myLike = false, depth = 1) {
        let margin = 5 * (depth - 1);
        let isSecret = '';

        if (status === 'SECRET')
            isSecret = '[비밀댓글]  ';

        if (isMine === true) {
            $("#comments").append(`
                                    <div class="bg-light p-2 ml-\${margin}">
                                            <div class="float-right">
                                                <div class="d-flex flex-row fs-7">
                                                    <div class="like p-2 cursor">
                                                     ` + commentLikeDOM(myLike, likeCount, id) + `
                                                    </div>
                                                    <div class="comment p-2 cursor action-collapse" data-toggle="collapse" aria-expanded="true"
                                                         aria-controls="collapse-1"
                                                         onclick="addChildForm(this, '\${id}')"><i
                                                            class="far fa-comment-dots"></i><span class="ml-1">댓글쓰기</span></div>
                                                    <div class="p-2 cursor">
                                                        <a onclick="commentEdit('\${id}', '\${status}');"><i class="far fa-edit"></i><span class='ml-1'>수정</span></a>
                                                    </div>
                                                    <div class="p-2 cursor">
                                                        <a onclick="deleteCommentCall(\${id})"><i class="fas fa-eraser"></i><span class='ml-1'>삭제</span></a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="d-flex flex-row align-items-start user-info">
                                                <img class="rounded-circle" src= "\${profileImage}" width="40">
                                                <div class="d-flex flex-column justify-content-start ml-2"><span
                                                        class="d-block font-weight-bold name">\${userName}</span><span
                                                        class="date text-black-50">\${modifiedDate}</span></div>
                                            </div>
                                            <div class="mt-2">
                                                <p class="comment-text">
                                                    \${isSecret}
                                                    \${content}
                                                </p>
                                            </div>
                                        </div>
                            `);
        } else if (!isMine && isSecret) {
            $("#comments").append(`
                                    <div class="bg-light p-2 ml-\${margin}">
                                            <div class="d-flex flex-row align-items-start user-info">
                                                <img class="rounded-circle" src= "\${profileImage}" width="40">
                                                <div class="d-flex flex-column justify-content-start ml-2"><span
                                                        class="d-block font-weight-bold name">\${userName}</span><span
                                                        class="date text-black-50">\${modifiedDate}</span></div>
                                            </div>
                                            <div class="mt-2">
                                                <p class="comment-text">\${content}</p>
                                            </div>
                                        </div>
                            `);
        } else {
            $("#comments").append(`
                                    <div class="bg-light p-2 ml-\${margin}">
                                            <div class="float-right">
                                                 <div class="d-flex flex-row fs-7">
                                                    <div class="like p-2 cursor">
                                                     ` + commentLikeDOM(myLike, likeCount, id) + `
                                                    </div>
                                                    <div class="comment p-2 cursor action-collapse" data-toggle="collapse" aria-expanded="true"
                                                         aria-controls="collapse-1"
                                                         onclick="addChildForm(this, '\${id}')"><i
                                                            class="far fa-comment-dots"></i><span class="ml-1">댓글쓰기</span></div>
                                                </div>
                                            </div>
                                            <div class="d-flex flex-row align-items-start user-info">
                                                <img class="rounded-circle" src= "\${profileImage}" width="40">
                                                <div class="d-flex flex-column justify-content-start ml-2"><span
                                                        class="d-block font-weight-bold name">\${userName}</span><span
                                                        class="date text-black-50">\${modifiedDate}</span></div>
                                            </div>
                                            <div class="mt-2">
                                                <p class="comment-text">\${content}</p>
                                            </div>
                                        </div>
                            `);
        }
        $("#comments").append('<hr>');
    }

    </sec:authorize>

    function commentLikeDOM(myLike = false, likeCount, commentId = '') {
        if (myLike) {
            return `<a onclick='commentUnLike(\${commentId})'><i class='fas fa-thumbs-up'></i> \${likeCount}<span class='ml-1'>좋아요</span></a>`;
        } else {
            return `<a onclick='commentLike(\${commentId})'><i class='far fa-thumbs-up'></i> \${likeCount}<span class='ml-1'>좋아요</span></a>`;
        }
    }

    function commentLike(commentId = '') {
        if (commentId === '')
            alert('ERROR');
        else {
            $.ajax({
                url: '/comment/' + commentId + '/like',
                method: "post",
                dataType: 'json',
                success: function (data) {
                    showModal('알림창', data.response);

                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);

                }
            });
        }
    }

    function commentUnLike(commentId = '') {
        if (commentId === '')
            alert('ERROR');
        else {
            $.ajax({
                url: '/comment/' + commentId + '/like',
                method: "put",
                dataType: 'json',
                success: function (data) {
                    showModal('알림창', data.response);

                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);

                }
            });
        }
    }

    function sendComment(parentId = '') {
        let content = $('textArea[name=content]').val();
        let status = "ENABLE";

        if ($("input:checkbox[name='secretSwitch']").is(":checked"))
            status = "SECRET";

        if (!textCheck(content)) {
            showModal('알림창', '빈 내용은 작성할 수 없습니다.');
            return;
        }

        let comment = {
            content: content,
            status: status
        };

        $.ajax({
            url: '/comments/<c:out value = "${post.id}"/>/' + parentId,
            method: "post",
            data: JSON.stringify(comment),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                showModal('알림창', data.response);

                setTimeout(function () {
                    window.location.reload();
                }, 2000);

            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    function rmSubComment(element, parentId = '') {
        $('.subComment').remove();
        $('.comment').attr("onClick", "addChildForm(this, '" + parentId + "')");
    }

    <sec:authorize access="isAuthenticated()">

    function addChildForm(element, parentId = '', isSecret = false) {
        rmSubComment();
        let html = `<div class="bg-light p-2 subComment">
                        <div class="d-flex flex-row align-items-start">
                            <img class="rounded-circle" src="<sec:authentication property="principal.account.profileImage"/>"width="40" alt="image"/>
                            <textarea name="content" class="form-control ml-1 shadow-none textarea"></textarea>
                        </div>
                        <div class="mt-2 text-right custom-control custom-switch subComment">
                                <input name="secretSwitch" type="checkbox" class="custom-control-input" id="secretSwitch">
                                <label class="custom-control-label" for="secretSwitch">블로그 주인만 보기</label>
                                <button class="btn btn-primary btn-sm shadow-none ml-4" onClick="sendComment(\${parentId})">댓글달기</button>
                        </div>
                    </div>`;

        $(element).attr("onClick", "rmSubComment(this, '" + parentId + "')");
        $(element).parent().parent().parent().append(html);
    }

    </sec:authorize>

    <sec:authorize access="isAnonymous()">

    function addChildForm(element, parentId = '') {
        rmSubComment();
        let html = `<div class="bg-light p-2 subComment">
                        <div class="d-flex flex-row align-items-start">
                            <img class="rounded-circle" src="/assets/img/illustrations/profiles/profile-2.png" width="40" alt="image"/>
                            <textarea name="content" class="form-control ml-1 shadow-none textarea"></textarea>
                        </div>
                        <div class="mt-2 text-right custom-control custom-switch">
                                <input name="secretSwitch" type="checkbox" class="custom-control-input" id="secretSwitch">
                                <label class="custom-control-label" for="secretSwitch">블로그 주인만 보기</label>
                                <button class="btn btn-primary btn-sm shadow-none ml-4" onClick="sendComment(\${parentId})">댓글달기</button>
                        </div>
                    </div>`;

        $(element).attr("onClick", "rmSubComment(this, '" + parentId + "')");
        $(element).parent().parent().parent().append(html);
    }

    </sec:authorize>


    function deleteCall(postId) {
        if (confirm("정말로 삭제하시겠습니까?")) {
            postDelete(postId);
        }
    }

    function postDelete(postId) {
        $.ajax({
            url: '/post/' + postId,
            method: "delete",
            dataType: 'json',
            success: function (data) {
                showModal('R.LOG 글 관리', data.response);

                setTimeout(function () {
                    window.location.href = '/';
                }, 3000);

            }
        });
    }

    function postEdit(postId) {
        window.location.href = '/admin/write/' + postId;
    }

</script>

<section class="bg-light py-10">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-12 col-xl-12">
                <div class="single-post">
                    <small>
                        <c:forEach var="category" items="${categories}" varStatus="status">
                            <c:out value="${category.categoryName}"/>
                            <c:if test="${!status.last}">&nbsp;>&nbsp;</c:if>
                        </c:forEach>
                        <c:if test="${isWriter}">
                            <div class="float-right">
                                <button class="btn btn-default" onclick="postEdit(${post.id});">수정</button>
                                &nbsp
                                <button class="btn btn-default" onclick="deleteCall(${post.id})">삭제</button>
                            </div>
                        </c:if>
                    </small>
                    <h1><c:out value="${post.title}"/></h1>
                    <p class="lead">${post.header}</p>
                    <div class="d-flex align-items-center justify-content-between mb-5">
                        <div class="single-post-meta mr-4">
                            <img class="single-post-meta-img"
                                 src="${post.writer.profileImage}"/>
                            <div class="single-post-meta-details">
                                <div class="single-post-meta-details-name">
                                    <c:out value="${post.writer.userName}"/>
                                </div>
                                <div class="single-post-meta-details-date">등록일 : <c:out
                                        value="${post.createdDate}"/></div>
                                <div class="single-post-meta-details-date">수정일 : <c:out
                                        value="${post.modifiedDate}"/></div>
                            </div>
                        </div>
                        <div class="single-post-meta-links">
                            <div class="row">
                                <c:choose>
                                    <c:when test="${post.postLikes eq true}">
                                        <a onclick="postUnLike()"><i class="xi xi-heart xi-1x mr-1"></i></a>
                                        <c:out value="${post.likesCount}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <a onclick="postLike()"><i class="xi xi-heart-o xi-1x mr-1"></i></a>
                                        <c:out value="${post.likesCount}"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="row">
                                <a onclick="snsShare('kakao',  '${post.title}');"><i class="xi xi-kakaostory xi-1x"></i></a>
                                <a onclick="snsShare('twitter',  '${post.title}');"><i class="xi xi-twitter xi-1x"></i></a>
                                <a onclick="snsShare('facebook',  '${post.title}');"><i
                                        class="xi xi-facebook xi-1x"></i></a>
                                <a onclick="snsShare('addurl',  '${post.title}');"><i class="xi xi-documents xi-1x"></i></a>
                            </div>
                        </div>
                    </div>

                    <div class="single-post-text my-5">
                        ${post.content}
                        <hr class="my-5"/>
                    </div>
                </div>
            </div>
        </div>

        <h3>댓글</h3>
        <div id="comments">


        </div>


        <div class="bg-light p-2">
            <div class="d-flex flex-row align-items-start">
                <sec:authorize access="isAuthenticated()">
                    <img class="rounded-circle"
                         src="<sec:authentication property="principal.account.profileImage"/>" width="40"
                         alt="image"/>
                </sec:authorize>

                <sec:authorize access="isAnonymous()">
                    <img class="rounded-circle"
                         src="/assets/img/illustrations/profiles/profile-1.png" width="40"
                         alt="image"/>
                </sec:authorize>
                <textarea name="content" class="form-control ml-1 shadow-none textarea"></textarea>
            </div>
            <div class="mt-2 text-right custom-control custom-switch">
                <input name="secretSwitch" type="checkbox" class="custom-control-input" id="secretSwitch">
                <label class="custom-control-label" for="secretSwitch">블로그 주인만 보기</label>
                <button class="btn btn-primary btn-sm shadow-none ml-4" onClick="sendComment()">댓글달기
                </button>
            </div>
        </div>

        <div class="text-center"><a class="btn btn-transparent-dark"
                                    href="/">Back to Blog Overview</a>
        </div>
    </div>
    <div class="svg-border-rounded text-dark">
        <!-- Rounded SVG Border-->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 144.54 17.34" preserveAspectRatio="none"
             fill="currentColor">
            <path d="M144.54,17.34H0V0H144.54ZM0,0S32.36,17.34,72.27,17.34,144.54,0,144.54,0"></path>
        </svg>
    </div>
</section>

<!-- 광고 추가 -->
<jsp:include page="./common/advertise.jsp"/>