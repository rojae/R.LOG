<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="common/top-meta.jsp"/>
    <link href="https://fonts.googleapis.com/css?family=Karla:400,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.materialdesignicons.com/4.8.95/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

    <style>
        body {
            margin-top: 20px;
            background: #f6f9fc;
        }

        .account-block {
            padding: 0;
            background-image: url('/assets/img/signup/bg-signup.jpeg');
            background-repeat: no-repeat;
            background-size: cover;
            height: 100%;
            position: relative;
        }

        .account-block .overlay {
            -webkit-box-flex: 1;
            -ms-flex: 1;
            flex: 1;
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .account-block .account-testimonial {
            text-align: center;
            color: #fff;
            position: absolute;
            margin: 0 auto;
            padding: 0 1.75rem;
            bottom: 3rem;
            left: 0;
            right: 0;
        }

        .text-theme {
            color: #5369f8 !important;
        }

        .btn-theme {
            background-color: #5369f8;
            border-color: #5369f8;
            color: #fff;
        }
    </style>
</head>

<body>
<div id="main-wrapper" class="container">
    <div class="row justify-content-center">
        <div class="col-xl-10">
            <div class="card border-0">
                <div class="card-body p-0">
                    <div class="row no-gutters">
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="mb-5">
                                    <h3 class="h4 font-weight-bold text-theme">회원가입</h3>
                                </div>
                                <h6 class="h5 mb-0">R.LOG</h6>
                                <p class="text-muted mt-2 mb-5">R.LOG는 개인용 블로그 시스템입니다.</p>
                                <form name="signup" method="post" action="/signup">
                                    <div class="form-group">
                                        <label for="userName">이름</label>
                                        <input type="text" class="form-control" name="userName" id="userName"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="email">이메일</label>
                                        <input type="email" class="form-control" name="email" id="email"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">비밀번호</label>
                                        <input type="password" class="form-control" name="password" id="password"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="role">권한을 선택하세요</label>
                                        <select class="form-control" id="role" name="role">
                                            <option value="ADMIN">관리자</option>
                                            <option value="USER">사용자</option>
                                        </select>
                                    </div>
                                    <%--    <div class="form-group mb-5">
                                            <label for="passwordConfirm">비밀번호 확인</label>
                                            <input type="password" class="form-control" name="passwordConfirm"
                                                   id="passwordConfirm"/>
                                        </div>--%>
                                    <button type="submit" class="btn btn-theme">회원가입</button>
                                </form>
                            </div>
                        </div>
                        <div class="col-lg-6 d-none d-lg-inline-block">
                            <div class="account-block rounded-right">
                                <div class="rounded-right"></div>
                                <div class="account-testimonial">
                                    <h4 class="text-white mb-4"></h4>
                                    <p class="lead text-white"></p>
                                    <p></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- end card-body -->
            </div>
            <!-- end card -->
            <p class="text-muted text-center mt-3 mb-0">이미 계정이 있으신가요?
                <a href="/login" class="text-primary ml-1">로그인하기</a>
            </p>
            <!-- end row -->
        </div>
        <!-- end col -->
    </div>
    <!-- Row -->
</div>
<jsp:include page="common/modal.jsp"/>
<jsp:include page="common/buttom-meta.jsp"/>
</body>
</html>