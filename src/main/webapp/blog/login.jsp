<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:10 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>R.LOG 로그인</title>
    <link href="https://fonts.googleapis.com/css?family=Karla:400,700&display=swap" rel="stylesheet">
    <link rel="shortcut icon" href="/assets/img/favicon-blue.ico?1" type="image/x-ico">
    <link rel="icon" href="/assets/img/favicon-blue.ico?1" type="image/x-ico">
    <link rel="stylesheet" href="https://cdn.materialdesignicons.com/4.8.95/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/login.scss">
    <link rel="stylesheet" href="/css/login.css/">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
</head>
<!DOCTYPE html>
<head>
    <title>R.LOG</title>
</head>
<body>

<main class="d-flex align-items-center min-vh-100 py-3 py-md-0">
    <div class="container">
        <div class="card login-card">
            <div class="row no-gutters">
                <div class="col-md-5">
                    <img src="/assets/img/login/bg-login.jpg" alt="login" class="login-card-img">
                </div>
                <div class="col-md-7">
                    <div class="card-body">
                        <div class="brand-wrapper">
                            <img src="/assets/img/illustrations/logo2.svg" alt="logo" class="logo">
                        </div>
                        <div class="badge badge-pill badge-primary-soft text-primary badge-marketing mb-3"
                        ">
                        <c:if test="${!empty loginResult}">
                            <c:out value="${loginResult}"/>
                        </c:if>
                    </div>
                    <p class="login-card-description">계정에 로그인하세요.</p>
                    <form name="login" method="post" action="/login">
                        <div class="form-group">
                            <label for="email" class="sr-only">Email</label>
                            <input type="email" name="email" id="email" class="form-control" placeholder="이메일을 입력하세요">
                        </div>
                        <div class="form-group mb-4">
                            <label for="password" class="sr-only">Password</label>
                            <input type="password" name="password" id="password" class="form-control"
                                   placeholder="패스워드를 입력하세요">
                        </div>
                        <input name="login" id="login" class="btn btn-block login-btn mb-4" type="submit" value="Login">
                        <button type="button" class="btn mb-4 btn-default btn_social" data-social="kakao">
                            <img src="/assets/img/login/kakao_login.png"/>
                        </button>

                    </form>
                    <a href="#!" class="forgot-password-link">Forgot password?</a>
                    <p class="login-card-footer-text">Don't have an account? -> <a href="/signup" class="text-reset">Register
                        here</a></p>
                    <nav class="login-card-footer-nav">
                        <a href="#!">Terms of use.</a>
                        <a href="#!">Privacy policy</a>
                    </nav>
                </div>
            </div>
        </div>
    </div>
    <!-- <div class="card login-card">
      <img src="assets/images/bg-login.jpg" alt="login" class="login-card-img">
      <div class="card-body">
        <h2 class="login-card-title">Login</h2>
        <p class="login-card-description">Sign in to your account to continue.</p>
        <form action="#!">
          <div class="form-group">
            <label for="email" class="sr-only">Email</label>
            <input type="email" name="email" id="email" class="form-control" placeholder="Email">
          </div>
          <div class="form-group">
            <label for="password" class="sr-only">Password</label>
            <input type="password" name="password" id="password" class="form-control" placeholder="Password">
          </div>
          <div class="form-prompt-wrapper">
            <div class="custom-control custom-checkbox login-card-check-box">
              <input type="checkbox" class="custom-control-input" id="customCheck1">
              <label class="custom-control-label" for="customCheck1">Remember me</label>
            </div>
            <a href="#!" class="text-reset">Forgot password?</a>
          </div>
          <input name="login" id="login" class="btn btn-block login-btn mb-4" type="button" value="Login">
        </form>
        <p class="login-card-footer-text">Don't have an account? <a href="#!" class="text-reset">Register here</a></p>
      </div>
    </div> -->
    </div>
</main>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<jsp:include page="common/modal.jsp"/>
<script>
        let socials = document.getElementsByClassName("btn_social");
        for (let social of socials) {
        social.addEventListener('click', function () {
            let socialType = this.getAttribute('data-social');
            location.href = "/oauth2/authorization/" + socialType;
            document.cookie = "socialType=" + this.getAttribute('data-social').toUpperCase();
        })
    }
</script>
</body>
</html>
