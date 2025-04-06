<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
<meta name="description" content="기록되지 않은 것은 기억되지 않는다. R.LOG"/>
<meta name="author" content="rojae"/>
<meta name="google-site-verification" content="0CtraLDEh-ceXqaIOvBoL_5abl9lUchLiU6qAgkRe54"/>
<meta name="naver-site-verification" content="f1b90b5ba58e9bfa77b5a918b2f2896a5d5fe7f1"/>

<c:choose>
    <c:when test="${empty post}">
        <meta property="og:url" content="https://blog.rojae.kr/">
        <meta property="og:title" content="기록되지 않은 것은 기억되지 않는다. R.LOG">
        <meta property="og:type" content="website">
        <meta property="og:image" content="https://blog.rojae.kr/assets/img/favicon.png">
    </c:when>
    <c:otherwise>
        <!-- Facebook (Common) Open Graph -->
        <meta property="og:type" content="website"/>
        <meta property="og:url" content="https://blog.rojae.kr/post/${post.id}"/>
        <meta property="og:title" content="${post.title}"/>
        <meta property="og:description" content="${post.header}"/>
        <meta property="og:image" content="${post.thumbNail}"/>
        <!-- Twitter Open Graph -->
        <meta name="twitter:card" content="website"/>
        <meta property="og:url" content="https://blog.rojae.kr/post/${post.id}"/>
        <meta property="og:title" content="${post.title}"/>
        <meta property="og:description" content="${post.header}"/>
        <meta property="og:image" content="${post.thumbNail}"/>
    </c:otherwise>
</c:choose>


<title>R.LOG</title>
<link href="/css/styles.css" rel="stylesheet"/>
<link href="/css/spinner.css" rel="stylesheet"/>
<link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css"/>

<sec:authorize access="!isAuthenticated()">
    <link rel="shortcut icon" href="/assets/img/favicon-blue.ico" type="image/x-ico">
    <link rel="icon" href="/assets/img/favicon-blue.ico" type="image/x-ico">
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <link rel="shortcut icon" href="/assets/img/favicon-red.ico" type="image/x-ico">
    <link rel="icon" href="/assets/img/favicon-red.ico" type="image/x-ico">
</sec:authorize>

<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<script data-search-pseudo-elements defer src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.24.1/feather.min.js"
        crossorigin="anonymous"></script>
<script src="/js/common.js"></script>
<script src="/js/kakao.js"></script>
<style>
    .carousel-inner > .carousel-item > img {
        height: 400px;
        -webkit-filter: invert(0.30);
        filter: invert(0.30);
    }

    .carousel-inner > .carousel-item > .carousel-caption {
        top: 60%;
        transform: translateY(-50%);
        bottom: initial;
        -webkit-transform-style: preserve-3d;
        -moz-transform-style: preserve-3d;
        transform-style: preserve-3d;
    }

    .breadcrumb-item + .breadcrumb-item::before {
        content: ">";
    }

</style>



