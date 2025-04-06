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

    <jsp:include page="./common/top-meta.jsp"/>

    <script>
        $(document).ready(function () {
            getReports();
        })

        function getReports(pageNo = 0){
            if(!($('input[name="pageNo"]').val() === undefined))
                pageNo =  $('input[name="pageNo"]').val();

            $.ajax({
                url: '/error/reports?page=' + pageNo,
                method: "get",
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    let reportList = $('.report-list');

                    if (data.reports.empty) {
                        $('.btn-more').remove();
                        reportList.append(`<button class="list-group-item list-group-item-action py-4 btn-more" href="#!">
                                                <div class="text-center small">해당 영역이 끝입니다.</div>
                                            </button>`);
                    }
                    for (let i in data.reports.content) {

                        let checkHtml = issueCheckBox(`\${data.reports.content[i].checkStatus}`, `\${data.reports.content[i].id}`);

                        let html = `<a class="list-group-item list-group-item-action py-4" href="javascript:void(0);" onclick="getReport(\${data.reports.content[i].id}); return false;">
                        <div class="d-flex justify-content-between">
                            <div class="mr-4 d-flex">
                                 <div class="mr-4 d-flex">
                                    <div>
                                        <h6>순번 : \${data.reports.content[i].id}</h6>
                                        신고시간 : \${data.reports.content[i].createdDate}
                                        <p class="card-text">내용 : \${data.reports.content[i].content.substring(0,50)} ...</p></p>
                                        `+checkHtml+`
                                    </div>
                                </div>
                            </div>
                        </div></a>`;

                        reportList.append(html);
                    }
                    let nextPage = Number(pageNo) + 1;
                    reportList.prepend(`<input type='hidden' name='pageNo' value = '\${nextPage}' />`);
                }
            });
        }

        function issueCheckBox(isCheck, issueId = '?'){
            if(isCheck === 'UNCHECK') {
                return `<div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="issue\${issueId}" >
                        <label class="form-check-label" for="flexCheckDefault">
                            issue-\${issueId}
                        </label>
                </div>`;
            }
            else if(isCheck === 'DONE'){
                return `<div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="issue\${issueId}" checked>
                        <label class="form-check-label" for="flexCheckDefault">
                            <del>issue-\${issueId}</del>
                        </label>
                </div>`;
            }else{
                return `<div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="issue\${issueId}" checked>
                        <label class="form-check-label" for="flexCheckDefault">
                            issue-\${issueId}
                        </label>
                </div>`;
            }
        }

        function issueSelectBox(checkStatus = 'UNCHECK'){
            $('.report-status').val(checkStatus).attr('selected', 'selected');
        }



        function getReport(id = -1){
            if(id === -1){
                return alert('조회시킬 오류 건을 알 수 없습니다');
            }
            $.ajax({
                url: '/error/report/' + id,
                method: "get",
                dataType: 'json',
                contentType: 'application/json',
                beforeSend : function(xhr, opts) {
                    $('.report-detail').html('');
                },
                success: function (data) {
                    let reportDetail = $('.report-detail');
                    let checkbox = issueCheckBox(data.checkStatus, data.id);
                    let createdDate = data.createdDate;
                    let content = data.content;
                    let img = data.writer == null ? "/assets/img/illustrations/profiles/question-mark.png" : data.writer.profileImage;
                    let userName = data.writer == null ? "미로그인 사용자" : data.writer.userName;
                    let email = data.writer == null ? "" : data.writer.email;

                    let html = `<div class="white-box">
                                    <h1 class="box-title">`+checkbox+`</h1>
                                    <div class="d-flex flex-row-reverse align-items-start">
                                        <select class="report-status" data-style="btn-success">
                                            <option value="UNCHECK">미확인</option>
                                            <option value="READ">읽음</option>
                                            <option value="PROCESS">처리중</option>
                                            <option value="DONE">이슈종료</option>
                                        </select>
                                    </div>
                                    <p class="text-justify">`+content+`</p>
                                    <p class="text-muted">`+createdDate+`</p>
                                    <div class="d-flex flex-row-reverse align-items-start">
                                        <div class = "d-flex flex-column justify-content-start ml-2">
                                            <span class="d-block font-weight-bold ">`+userName+`</span>
                                            <span class="d-block font-weight-bold ">`+email+`</span>
                                        </div>
                                        <img src="`+img+`" style="height: 2.25rem; width: 2.25rem; border-radius: 100%; margin-right: 0.5rem; flex-shrink: 0;"">
                                    </div>
                                </div>`;
                    reportDetail.append(html);
                    issueSelectBox(data.checkStatus);

                    $('select').on('change', function() {
                        updateReportStatus(data.id, this.value);
                    });

                }
            });
        }

        function updateReportStatus(id = -1, checkStatus = 'UNCHECK'){
            if(id === -1){
                return alert('변경할 이슈를 알 수 없습니다');
            }

            let data = {
                checkStatus: checkStatus,
            };

            $.ajax({
                url: '/error/report/' + id,
                method: "put",
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    if(data.code !== '200')
                        showModal('알림창', data.response);
                    else {
                        $('.report-list').html('');
                        getReports();       // list
                        getReport(id);      // detail
                    }

                }
            });
        }

        function enrollIssue(){
            return $('.report-detail').html(`<div id="report">
                <label for="report-form">이슈 등록하기</label>
                <textarea class="form-control" id="report-form" name="content" rows="5"></textarea>
                <button class="btn btn-primary btn-sm mt-1" onclick="doReport();">
                    <span class="glyphicon glyphicon-home"></span>report
                </button>
            </div>`);

        }

        function doReport(){
            let data = {
                content: $('textarea[name="content"]').val()
            };
            $.ajax({
                url: '/error/report',
                method: "post",
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    alert(data.response);

                    $('.report-list').html('');
                    getReports();       // list
                    $('.report-detail').html('');      // detail
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
                    <h4 class="page-title">이슈 괸리 페이지</h4>
                    <br/><strong>영역</strong>을 클릭하면, 해당 이슈 상세 조회가 가능합니다.

                    <div class="input-group mt-5">
                        <input class="form-control border-end-0 border rounded-pill" type="text" placeholder="이슈 검색" id="keyword">
                        <span class="input-group-append">
                            <button class="btn btn-outline-primary bg-white border-start-0 border rounded-pill ms-n3" type="button">
                                <i class="fa fa-search"></i>
                            </button>
                        </span>
                    </div>

                </div>

                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <div class="d-md-flex">
                        <button onclick="enrollIssue();" class="btn btn-primary btn-md ml-auto" role="button" aria-pressed="true">이슈등록</button>
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
                <div class="col-md-5">
                        <div class="list-group list-group-flush report-list">
                        </div>

                        <button class="list-group-item list-group-item-action py-4 btn-more" href="#!" onclick="getReports();">
                            <div class="text-center small">View More Reports</div>
                        </button>

                </div>
                <div class="col-md-7 report-detail">


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
<script>

</script>
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
