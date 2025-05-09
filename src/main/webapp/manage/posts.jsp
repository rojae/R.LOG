<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:29 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html dir="ltr" lang="en">

<head>

    <jsp:include page="common/top-meta.jsp"/>

    <script>
        $(document).ready(function () {
            getPosts();
        })

        function getPosts(pageNo = 0) {
            if(!($('input[name="pageNo"]').val() === undefined))
                pageNo =  $('input[name="pageNo"]').val();

            $.ajax({
                url: '/posts?page=' + pageNo,
                method: "get",
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                        let commentsList = $('.posts-list');

                        if (data.posts.empty) {
                            $('.btn-more').remove();
                            commentsList.append(`<button class="list-group-item list-group-item-action py-4 btn-more" href="#!">
                                                <div class="text-center small">해당 영역이 끝입니다.</div>
                                            </button>`);
                        }
                        for (let i in data.posts.content) {
                            let title = data.posts.content[i].title;

                            let html = `<a class="list-group-item list-group-item-action py-4" href="\${data.posts.content[i].url}">
                        <div class="d-flex justify-content-between">
                            <div class="mr-4 d-flex">
                                <img class="rounded-circle mr-3" src="<sec:authentication property="principal.account.profileImage"/>" width="40" alt="image">
                                <div>
                                    <h6>\${title}</h6>
                                    <p class="card-text">\${data.posts.content[i].header}</p>
                                </div>
                            </div>
                            <div class="small text-gray-400 flex-shrink-0 text-right">\${data.posts.content[i].modifiedDate}<br />
                                <div class="badge badge-green-soft badge-pill text-green">\${data.posts.content[i].status}</div>
                            </div>
                        </div></a>`;

                            commentsList.append(html);
                        }
                        let nextPage = Number(pageNo) + 1;
                        commentsList.prepend(`<input type='hidden' name='pageNo' value = '\${nextPage}' />`);
                }
            });
        }
    </script>
</head>

<body>
<!-- ============================================================== -->
<!-- Preloader - style you can find in spinners.css -->
<!-- ============================================================== -->
<div class="preloader">
    <div class="lds-ripple">
        <div class="lds-pos"></div>
        <div class="lds-pos"></div>
    </div>
</div>
<!-- ============================================================== -->
<!-- Main wrapper - style you can find in pages.scss -->
<!-- ============================================================== -->
<div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5" data-sidebartype="full"
     data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">
    <!-- ============================================================== -->
    <!-- Topbar header - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <jsp:include page="common/header.jsp"/>
    <!-- ============================================================== -->
    <!-- End Topbar header -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Left Sidebar - style you can find in sidebar.scss  -->
    <!-- ============================================================== -->
    <jsp:include page="common/side-bar.jsp"/>
    <!-- ============================================================== -->
    <!-- End Left Sidebar - style you can find in sidebar.scss  -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Page wrapper  -->
    <!-- ============================================================== -->
    <div class="page-wrapper">
        <!-- ============================================================== -->
        <!-- Bread crumb and right sidebar toggle -->
        <!-- ============================================================== -->
        <div class="page-breadcrumb bg-white">
            <div class="row align-items-center">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">글 정보</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <div class="d-md-flex">
                        <ol class="breadcrumb ms-auto">
                            <li><a href="#" class="fw-normal">My Post</a></li>
                        </ol>
                        <%-- <a href="https://www.wrappixel.com/templates/ampleadmin/" target="_blank"
                            class="btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white">Upgrade
                             to Pro</a>--%>
                    </div>
                </div>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- ============================================================== -->
        <!-- End Bread crumb and right sidebar toggle -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Container fluid  -->
        <!-- ============================================================== -->
        <div class="container-fluid">
            <!-- ============================================================== -->
            <!-- Start Page Content -->
            <!-- ============================================================== -->
            <div class="row">
                <div class="col-md-12">
                    <div class="white-box">
                        <h3 class="box-title">작성 글 목록</h3>
                        <p class="text-muted">
                            <strong>글 영역</strong>을 클릭하여, 해당 글 수정이 가능합니다.
                        </p>
                    </div>


                    <div class="list-group list-group-flush posts-list">
                        <!-- Comment List -->
                    </div>

                    <button class="list-group-item list-group-item-action py-4 btn-more" href="#!" onclick="getPosts();">
                        <div class="text-center small">View More Posts</div>
                    </button>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- End PAge Content -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- Right sidebar -->
            <!-- ============================================================== -->
            <!-- .right-sidebar -->
            <!-- ============================================================== -->
            <!-- End Right sidebar -->
            <!-- ============================================================== -->
        </div>
        <!-- ============================================================== -->
        <!-- End Container fluid  -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- footer -->
        <!-- ============================================================== -->
        <jsp:include page="common/footer.jsp"/>
        <!-- ============================================================== -->
        <!-- End footer -->
        <!-- ============================================================== -->
    </div>
    <!-- ============================================================== -->
    <!-- End Page wrapper  -->
    <!-- ============================================================== -->
</div>
<!-- ============================================================== -->
<!-- End Wrapper -->
<!-- ============================================================== -->
<!-- ============================================================== -->
<!-- All Jquery -->
<!-- ============================================================== -->
<jsp:include page="common/buttom-meta.jsp"/>

</body>

</html>
<%--
<!DOCTYPE html>
<head>
    <title>R.LOG</title>
</head>
<body>
    <c:if test = "${!empty message}">
        <c:out value = "${message}"/>
        <br/>
    </c:if>

    <c:if test = "${!empty email}">
        <c:out value = "${email}"/>
        <br/>
    </c:if>

    <c:if test = "${!empty role}">
        <c:out value = "${role}"/>
        <br/>
    </c:if>

    <sec:authorize access="hasRole('ROLE_ADMIN')" >
        <p><a href = '/admin'>관리자 페이지로 이동</a></p>
    </sec:authorize>
    <p><a href = '/posts'>글 리스트 조회</a></p>
    <p><a href = '/categories'>카테고리 테스트 조회</a></p>
    <p><a href = '/logout'>로그아웃</a></p>

</body>--%>
