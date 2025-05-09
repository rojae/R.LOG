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
            $.ajax({
                url: '/manage/statistic/today',
                method: "get",
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    if (data.code === '200') {
                        $('.today-count').text(data.response);
                    } else {
                        showModal('알림창', '금일 조회 오류');
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });

            $.ajax({
                url: '/manage/statistic/month',
                method: "get",
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    if (data.code === '200') {
                        $('.month-count').text(data.response);
                    } else {
                        showModal('알림창', '금월 조회 오류');
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });

            $.ajax({
                url: '/manage/statistic/all',
                method: "get",
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    if (data.code === '200') {
                        $('.all-count').text(data.response);
                    } else {
                        showModal('알림창', '누적 조회 오류');
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        });
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
                    <h4 class="page-title">Dashboard</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <div class="d-md-flex">
                        <ol class="breadcrumb ms-auto">
                            <li><a href="#" class="fw-normal">Dashboard</a></li>
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
            <!-- Three charts -->
            <!-- ============================================================== -->
            <div class="row justify-content-center">
                <div class="col-lg-4 col-md-12">
                    <div class="white-box analytics-info">
                        <h3 class="box-title">오늘 방문수</h3>
                        <ul class="list-inline two-part d-flex align-items-center mb-0">
                            <li>
                                <div id="sparklinedash"><canvas width="67" height="30"
                                                                style="display: inline-block; width: 67px; height: 30px; vertical-align: top;"></canvas>
                                </div>
                            </li>
                            <li class="ms-auto"><span class="counter text-success today-count"></span></li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-4 col-md-12">
                    <div class="white-box analytics-info">
                        <h3 class="box-title">이번 달 방문수</h3>
                        <ul class="list-inline two-part d-flex align-items-center mb-0">
                            <li>
                                <div id="sparklinedash2"><canvas width="67" height="30"
                                                                 style="display: inline-block; width: 67px; height: 30px; vertical-align: top;"></canvas>
                                </div>
                            </li>
                            <li class="ms-auto"><span class="counter text-purple month-count"></span></li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-4 col-md-12">
                    <div class="white-box analytics-info">
                        <h3 class="box-title">누적 방문수</h3>
                        <ul class="list-inline two-part d-flex align-items-center mb-0">
                            <li>
                                <div id="sparklinedash3"><canvas width="67" height="30"
                                                                 style="display: inline-block; width: 67px; height: 30px; vertical-align: top;"></canvas>
                                </div>
                            </li>
                            <li class="ms-auto"><span class="counter text-info all-count"></span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- PRODUCTS YEARLY SALES -->
            <!-- ============================================================== -->
            <div class="row">
                <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
                    <div class="white-box">
                        <h3 class="box-title">Products Yearly Sales</h3>
                        <div class="d-md-flex">
                            <ul class="list-inline d-flex ms-auto">
                                <li class="ps-3">
                                    <h5><i class="fa fa-circle me-1 text-info"></i>Mac</h5>
                                </li>
                                <li class="ps-3">
                                    <h5><i class="fa fa-circle me-1 text-inverse"></i>Windows</h5>
                                </li>
                            </ul>
                        </div>
                        <div id="ct-visits" style="height: 405px;">
                            <div class="chartist-tooltip" style="top: -17px; left: -12px;"><span
                                    class="chartist-tooltip-value">6</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- RECENT SALES -->
            <!-- ============================================================== -->
            <div class="row">
                <div class="col-md-12 col-lg-12 col-sm-12">
                    <div class="white-box">
                        <div class="d-md-flex mb-3">
                            <h3 class="box-title mb-0">Recent sales</h3>
                            <div class="col-md-3 col-sm-4 col-xs-6 ms-auto">
                                <select class="form-select shadow-none row border-top">
                                    <option>March 2021</option>
                                    <option>April 2021</option>
                                    <option>May 2021</option>
                                    <option>June 2021</option>
                                    <option>July 2021</option>
                                </select>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table no-wrap">
                                <thead>
                                <tr>
                                    <th class="border-top-0">#</th>
                                    <th class="border-top-0">Name</th>
                                    <th class="border-top-0">Status</th>
                                    <th class="border-top-0">Date</th>
                                    <th class="border-top-0">Price</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>1</td>
                                    <td class="txt-oflo">Elite admin</td>
                                    <td>SALE</td>
                                    <td class="txt-oflo">April 18, 2021</td>
                                    <td><span class="text-success">$24</span></td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td class="txt-oflo">Real Homes WP Theme</td>
                                    <td>EXTENDED</td>
                                    <td class="txt-oflo">April 19, 2021</td>
                                    <td><span class="text-info">$1250</span></td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td class="txt-oflo">Ample Admin</td>
                                    <td>EXTENDED</td>
                                    <td class="txt-oflo">April 19, 2021</td>
                                    <td><span class="text-info">$1250</span></td>
                                </tr>
                                <tr>
                                    <td>4</td>
                                    <td class="txt-oflo">Medical Pro WP Theme</td>
                                    <td>TAX</td>
                                    <td class="txt-oflo">April 20, 2021</td>
                                    <td><span class="text-danger">-$24</span></td>
                                </tr>
                                <tr>
                                    <td>5</td>
                                    <td class="txt-oflo">Hosting press html</td>
                                    <td>SALE</td>
                                    <td class="txt-oflo">April 21, 2021</td>
                                    <td><span class="text-success">$24</span></td>
                                </tr>
                                <tr>
                                    <td>6</td>
                                    <td class="txt-oflo">Digital Agency PSD</td>
                                    <td>SALE</td>
                                    <td class="txt-oflo">April 23, 2021</td>
                                    <td><span class="text-danger">-$14</span></td>
                                </tr>
                                <tr>
                                    <td>7</td>
                                    <td class="txt-oflo">Helping Hands WP Theme</td>
                                    <td>MEMBER</td>
                                    <td class="txt-oflo">April 22, 2021</td>
                                    <td><span class="text-success">$64</span></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- Recent Comments -->
            <!-- ============================================================== -->
            <div class="row">
                <!-- .col -->
                <div class="col-md-12 col-lg-8 col-sm-12">
                    <div class="card white-box p-0">
                        <div class="card-body">
                            <h3 class="box-title mb-0">Recent Comments</h3>
                        </div>
                        <div class="comment-widgets">
                            <!-- Comment Row -->
                            <div class="d-flex flex-row comment-row p-3 mt-0">
                                <div class="p-2"><img src="/plugins/images/users/varun.jpg" alt="user" width="50" class="rounded-circle"></div>
                                <div class="comment-text ps-2 ps-md-3 w-100">
                                    <h5 class="font-medium">James Anderson</h5>
                                    <span class="mb-3 d-block">Lorem Ipsum is simply dummy text of the printing and type setting industry.It has survived not only five centuries. </span>
                                    <div class="comment-footer d-md-flex align-items-center">
                                        <span class="badge bg-primary rounded">Pending</span>

                                        <div class="text-muted fs-2 ms-auto mt-2 mt-md-0">April 14, 2021</div>
                                    </div>
                                </div>
                            </div>
                            <!-- Comment Row -->
                            <div class="d-flex flex-row comment-row p-3">
                                <div class="p-2"><img src="/plugins/images/users/genu.jpg" alt="user" width="50" class="rounded-circle"></div>
                                <div class="comment-text ps-2 ps-md-3 active w-100">
                                    <h5 class="font-medium">Michael Jorden</h5>
                                    <span class="mb-3 d-block">Lorem Ipsum is simply dummy text of the printing and type setting industry.It has survived not only five centuries. </span>
                                    <div class="comment-footer d-md-flex align-items-center">

                                        <span class="badge bg-success rounded">Approved</span>

                                        <div class="text-muted fs-2 ms-auto mt-2 mt-md-0">April 14, 2021</div>
                                    </div>
                                </div>
                            </div>
                            <!-- Comment Row -->
                            <div class="d-flex flex-row comment-row p-3">
                                <div class="p-2"><img src="/plugins/images/users/ritesh.jpg" alt="user" width="50" class="rounded-circle"></div>
                                <div class="comment-text ps-2 ps-md-3 w-100">
                                    <h5 class="font-medium">Johnathan Doeting</h5>
                                    <span class="mb-3 d-block">Lorem Ipsum is simply dummy text of the printing and type setting industry.It has survived not only five centuries. </span>
                                    <div class="comment-footer d-md-flex align-items-center">

                                        <span class="badge rounded bg-danger">Rejected</span>

                                        <div class="text-muted fs-2 ms-auto mt-2 mt-md-0">April 14, 2021</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-md-12 col-sm-12">
                    <div class="card white-box p-0">
                        <div class="card-heading">
                            <h3 class="box-title mb-0">Chat Listing</h3>
                        </div>
                        <div class="card-body">
                            <ul class="chatonline">
                                <li>
                                    <div class="call-chat">
                                        <button class="btn btn-success text-white btn-circle btn" type="button">
                                            <i class="fas fa-phone"></i>
                                        </button>
                                        <button class="btn btn-info btn-circle btn" type="button">
                                            <i class="far fa-comments text-white"></i>
                                        </button>
                                    </div>
                                    <a href="javascript:void(0)" class="d-flex align-items-center"><img
                                            src="/plugins/images/users/varun.jpg" alt="user-img" class="img-circle">
                                        <div class="ms-2">
                                                <span class="text-dark">Varun Dhavan <small
                                                        class="d-block text-success d-block">online</small></span>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <div class="call-chat">
                                        <button class="btn btn-success text-white btn-circle btn" type="button">
                                            <i class="fas fa-phone"></i>
                                        </button>
                                        <button class="btn btn-info btn-circle btn" type="button">
                                            <i class="far fa-comments text-white"></i>
                                        </button>
                                    </div>
                                    <a href="javascript:void(0)" class="d-flex align-items-center"><img
                                            src="/plugins/images/users/genu.jpg" alt="user-img" class="img-circle">
                                        <div class="ms-2">
                                                <span class="text-dark">Genelia
                                                    Deshmukh <small class="d-block text-warning">Away</small></span>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <div class="call-chat">
                                        <button class="btn btn-success text-white btn-circle btn" type="button">
                                            <i class="fas fa-phone"></i>
                                        </button>
                                        <button class="btn btn-info btn-circle btn" type="button">
                                            <i class="far fa-comments text-white"></i>
                                        </button>
                                    </div>
                                    <a href="javascript:void(0)" class="d-flex align-items-center"><img
                                            src="/plugins/images/users/ritesh.jpg" alt="user-img" class="img-circle">
                                        <div class="ms-2">
                                                <span class="text-dark">Ritesh
                                                    Deshmukh <small class="d-block text-danger">Busy</small></span>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <div class="call-chat">
                                        <button class="btn btn-success text-white btn-circle btn" type="button">
                                            <i class="fas fa-phone"></i>
                                        </button>
                                        <button class="btn btn-info btn-circle btn" type="button">
                                            <i class="far fa-comments text-white"></i>
                                        </button>
                                    </div>
                                    <a href="javascript:void(0)" class="d-flex align-items-center"><img
                                            src="/plugins/images/users/arijit.jpg" alt="user-img" class="img-circle">
                                        <div class="ms-2">
                                                <span class="text-dark">Arijit
                                                    Sinh <small class="d-block text-muted">Offline</small></span>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <div class="call-chat">
                                        <button class="btn btn-success text-white btn-circle btn" type="button">
                                            <i class="fas fa-phone"></i>
                                        </button>
                                        <button class="btn btn-info btn-circle btn" type="button">
                                            <i class="far fa-comments text-white"></i>
                                        </button>
                                    </div>

                                    <a href="javascript:void(0)" class="d-flex align-items-center"><img
                                            src="/plugins/images/users/govinda.jpg" alt="user-img"
                                            class="img-circle">
                                        <div class="ms-2">
                                                <span class="text-dark">Govinda
                                                    Star <small class="d-block text-success">online</small></span>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <div class="call-chat">
                                        <button class="btn btn-success text-white btn-circle btn" type="button">
                                            <i class="fas fa-phone"></i>
                                        </button>
                                        <button class="btn btn-info btn-circle btn" type="button">
                                            <i class="far fa-comments text-white"></i>
                                        </button>
                                    </div>
                                    <a href="javascript:void(0)" class="d-flex align-items-center"><img
                                            src="/plugins/images/users/hritik.jpg" alt="user-img" class="img-circle">
                                        <div class="ms-2">
                                                <span class="text-dark">John
                                                    Abraham<small class="d-block text-success">online</small></span>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- /.col -->
            </div>
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
