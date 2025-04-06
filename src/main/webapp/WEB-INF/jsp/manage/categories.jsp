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
    <style>
        ul li a {
            display: block;
        }
    </style>
    <jsp:include page="./common/top-meta.jsp"/>

    <script>
        $(document).ready(function () {
            goCategory();
        })

        function deleteCategory(thisId = -1){
            if(thisId === -1)
                return alert('잘못된 시도입니다');
            if(confirm('정말 삭제하시겠습니까?')){
                $.ajax({
                    url: '/categories/' + thisId,
                    method: "delete",
                    dataType: 'json',
                    beforeSend : function() {
                        $(".preloader").fadeIn();
                    },
                    success: function (data) {
                        alert(data.response);
                        window.location.reload();
                    },
                    complete: function () {
                        $(".preloader").fadeOut();
                    }
                });
            }
        }

        function goCategory(parentId = 0, depth = 0) {
            $.ajax({
                url: '/categories/' + parentId,
                dataType: 'json',
                beforeSend : function() {
                    $(".preloader").fadeIn();
                },
                success: function (data) {
                    showCategories(data, depth);
                    if (depth === 1)
                        $(".category-group-mid").after(`<button type="button" class="btn btn-default text-black btn-lg btn-block btn-mid-new" onclick="createForm(\${parentId}, 1);"><i class="fas fa-plus"></i></button>`);
                    else if (depth === 2)
                        $(".category-group-buttom").after(`<button type="button" class="btn btn-default text-black btn-lg btn-block btn-buttom-new" onclick="createForm(\${parentId}, 2);"><i class="fas fa-plus"></i></button>`);
                },
                complete: function () {
                    $(".preloader").fadeOut();
                    $("ul").sortable({
                        items: $(".top")
                    });
                }
            });
        }

        function showCategories(root, depth) {
            clearCategory(depth);
            for (let i in root) {
                if(root.length === 0)  break;
                let name = root[i].categoryName;
                let id = root[i].id;
                drawCategory(id, name, depth);
            }
       }

        function clearCategory(depth) {
            if (depth === 1) {
                $('.btn-mid-new').remove();
                $(".category-group-mid").html('');
            }
            else if (depth === 2) {
                $('.btn-buttom-new').remove();
                $(".category-group-buttom").html('');
            }
        }

        function drawCategory(id, name, depth) {
            if (depth === 0) {
                $(".category-group-top").append(`
                <li class="list-group-item top justify-content-between align-items-center" id="\${id}">
                    <i class="xi xi-list-dot xi-1x"></i> \${name}
                    <div class = "float-right">
                        <a class="btn btn-outline-info btn-sm" href="#" onclick="editForm(this, '\${id}'); return false;"><i class="far fa-edit"></i></a>
                        <a class="btn btn-outline-info btn-sm" href="#" onclick="deleteCategory('\${id}'); return false;"><i class="far fa-trash-alt"></i></a>
                        <a class="btn btn-outline-info btn-sm" href="#" onclick="goCategory('\${id}', 1); return false;"><i class="fas fa-angle-right"></i></a>
                    </div>
                </li>
                `);
            } else if (depth === 1) {
                $(".category-group-mid").append(`
                <li class="list-group-item top justify-content-between align-items-center" id="\${id}">
                    <i class="xi xi-list-dot xi-1x"></i> \${name}
                    <div class = "float-right">
                        <a class="btn btn-outline-info btn-sm" href="#" onclick="editForm(this, '\${id}'); return false;"><i class="far fa-edit"></i></a>
                        <a class="btn btn-outline-info btn-sm" href="#" onclick="deleteCategory('\${id}'); return false;"><i class="far fa-trash-alt"></i></a>
                        <a class="btn btn-outline-info btn-sm" href="#" onclick="goCategory('\${id}', 2); return false;"><i class="fas fa-angle-right"></i></a>
                    </div>
                </li>
                `);
            } else if(depth === 2){
                $(".category-group-buttom").append(`
                <li class="list-group-item top justify-content-between align-items-center" id="\${id}">
                    <i class="xi xi-list-dot xi-1x"></i> \${name}
                    <div class = "float-right">
                        <a class="btn btn-outline-info btn-sm" href="#" onclick="editForm(this, '\${id}'); return false;"><i class="far fa-edit"></i></a>
                        <a class="btn btn-outline-info btn-sm" href="#" onclick="deleteCategory('\${id}'); return false;"><i class="far fa-trash-alt"></i></a>
                    </div>
                </li>
                `);
            }

        }

        function createForm(parentId = 0, type = 0){
            $('.category-add-li').remove();

            if(parentId === 0 || type === 0){
                $(".category-group-top").append(`
                    <li class="list-group-item top justify-content-between align-items-center category-add-li">
                        <div class="form-group input-group">
                            <input type="text" name="categoryName" class="form-control form-control-sm m-1">
                            <button type="button" class="btn btn-outline-info btn-sm" href="#" onclick="createCategory(); return false;"><i class="far fa-save"></i></button>
                        </div>
                    </li>
                `);
            }
            else if(type === 1) {
                $(".category-group-mid").append(`
                    <li class="list-group-item top justify-content-between align-items-center category-add-li">
                        <div class="form-group input-group">
                            <input type="text" name="categoryName" class="form-control form-control-sm m-1">
                            <button type="button" class="btn btn-outline-info btn-sm" href="#" onclick="createCategory(\${parentId}); return false;"><i class="far fa-save"></i></button>
                        </div>
                    </li>
                `);
            }
            else if(type === 2){
                $(".category-group-buttom").append(`
                    <li class="list-group-item top justify-content-between align-items-center category-add-li">
                        <div class="form-group input-group">
                            <input type="text" name="categoryName" class="form-control form-control-sm m-1">
                            <button type="button" class="btn btn-outline-info btn-sm" href="#" onclick="createCategory(\${parentId}); return false;"><i class="far fa-save"></i></button>
                        </div>
                    </li>
                `);
            }

        }

        function createCategory(parentId = 0){
            let categoryName = $('.category-add-li').find('input[name="categoryName"]').val();

            if(categoryName === '')
                return alert('카테고리 이름은 필수입니다.');

            $.ajax({
                url: '/category/',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify ({
                  parentId: parentId,
                  categoryName: categoryName
                }),
                method: 'POST',
                beforeSend : function() {
                    $(".preloader").fadeIn();
                },
                success: function (data) {
                    alert(data.response);
                    window.location.reload();
                },
                complete: function () {
                    $(".preloader").fadeOut();
                }
            });
        }

        function editCategory(id = 0){
            if(id === 0)
                return alert('오류가 발생하였습니다. 새로고침 이후에 시도해주세요');

            let categoryName = $('.category-edit-li').find('input[name="categoryName"]').val();

            if(categoryName === '')
                return alert('카테고리 이름은 필수입니다.');

            $.ajax({
                url: '/category/' + id,
                dataType: 'json',
                contentType: 'application/json',
                data: categoryName,
                method: 'PUT',
                beforeSend : function() {
                    $(".preloader").fadeIn();
                },
                success: function (data) {
                    alert(data.response);
                    window.location.reload();
                },
                complete: function () {
                    $(".preloader").fadeOut();
                }
            });

        }

        function editForm(e, id = 0){

            $.ajax({
                url: '/category/' + id,
                dataType: 'json',
                contentType: 'application/json',
                method: 'GET',
                beforeSend : function() {
                    $(".preloader").fadeIn();
                },
                success: function (data) {
                    let edit = `<div class="form-group input-group">
                                    <input type="text" name="categoryName" class="form-control form-control-sm m-1" value="\${data.categoryName}">
                                    <button type="button" class="btn btn-outline-info btn-sm" href="#" onclick="editCategory(\${data.id}); return false;"><i class="far fa-save"></i></button>
                                </div>`;
                    $(e.closest('li')).addClass('category-edit-li');
                    $(e.closest('li')).html(edit);
                },
                complete: function () {
                    $(".preloader").fadeOut();
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
    <jsp:include page="./common/header.jsp"/>
    <!-- ============================================================== -->
    <!-- End Topbar header -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Left Sidebar - style you can find in sidebar.scss  -->
    <!-- ============================================================== -->
    <jsp:include page="./common/side-bar.jsp"/>
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
                    <h4 class="page-title">카테고리 관리</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <div class="d-md-flex">
                        <ol class="breadcrumb ms-auto">
                            <li><a href="#" class="fw-normal">Categories</a></li>
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
                <p class="text-muted">
                    <strong>드래그 앤 드롭</strong>으로 카테고리 순서를 변경하고 주제 연결을 설정할 수 있습니다.
                </p>

                <div class="col-md-3 d-inline ml-3 p-3 bg-primary text-blue">
                    <blockquote class="blockquote">
                        <p class="mb-0 text-white">대메뉴</p>
                    </blockquote>

                    <!-- 대메뉴 -->
                    <ul class="list-group category-group-top">

                    </ul>
                    <button type="button" class="btn btn-default text-black btn-lg btn-block btn-top-new" onclick="createForm();"><i class="fas fa-plus"></i></button>
                </div>

                <!-- 중메뉴 -->
                <div class="col-md-3 d-inline ml-3 p-3 bg-primary text-blue">
                    <blockquote class="blockquote">
                        <p class="mb-0 text-white">중메뉴</p>
                    </blockquote>

                    <ul class="list-group category-group-mid">

                    </ul>

                </div>

                <!-- 소메뉴 -->
                <div class="col-md-3 d-inline ml-3 p-3 bg-primary text-blue">
                    <blockquote class="blockquote">
                        <p class="mb-0 text-white">소메뉴</p>
                    </blockquote>

                    <ul class="list-group category-group-buttom">

                    </ul>


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
        <jsp:include page="./common/footer.jsp"/>
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
<jsp:include page="./common/buttom-meta.jsp"/>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

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
