<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
<meta name="description" content="기록되지 않은 것은 기억되지 않는다. R.LOG"/>
<meta name="author" content="rojae"/>

<meta property="og:url" content="http://blog.rojae.kr/">
<meta property="og:title" content="기록되지 않은 것은 기억되지 않는다. R.LOG">
<meta property="og:type" content="website">
<meta property="og:image" content="http://blog.rojae.kr/assets/img/favicon.png">

<title>블로그관리 || R.LOG</title>
<meta name="robots" content="noindex,nofollow">
<link rel="canonical" href="https://www.wrappixel.com/templates/ample-admin-lite/"/>
<!-- Favicon icon -->
<link rel="icon" type="image/png" href="/assets/img/favicon-black.png"/>
<!-- Custom CSS -->
<link href="/plugins/bower_components/chartist/dist/chartist.min.css" rel="stylesheet">
<link rel="stylesheet" href="/plugins/bower_components/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css">
<!-- Custom CSS -->
<link href="/css/styles.css" rel="stylesheet"/>
<link href="/css/style.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script data-search-pseudo-elements defer src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.24.1/feather.min.js" crossorigin="anonymous"></script>

<script>
    function showModal(title = 'Please Insert Title', body = 'Please Insert Body') {
        $('#rlog-modal .modal-header').html(`<h5 class="modal-title">\${title}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>`);

        $('#rlog-modal .modal-body').html(`<p>\${body}</p>`);
        $('#rlog-modal .modal-footer').html(`<button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">Close</button>`);
        $('#rlog-modal').modal('show');
    }

    function showModalWithSubmit(title = 'Please Insert Title', body = 'Please Insert Body', footer = 'Please Insert Footer') {
        $('#rlog-modal .modal-header').html(`<h5 class="modal-title">\${title}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>`);

        $('#rlog-modal .modal-body').html(`<p>${body}</p>`);
        $('#rlog-modal .modal-footer').html(`<button class = "btn btn-sm btn-primary btn-submit">작성하기</button>`);
        $('#rlog-modal').modal('show');
    }


    function showModalWithFooter(title = 'Please Insert Title', body = 'Please Insert Body', footer = 'Please Insert Footer') {
        $('#rlog-modal .modal-header').html(`<h5 class="modal-title">\${title}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>`);

        $('#rlog-modal .modal-body').html(`<p>\${body}</p>`);
        $('#rlog-modal .modal-footer').html(footer);
        $('#rlog-modal').modal('show');
    }

    function ready(userName = '사용자') {
        if (userName === "")
            userName = '사용자';
        showModal(`\${userName}님 죄송합니다...`, `해당 기능은 수정 중 입니다. <br/>최대한 빠르게 해결하겠습니다`);
    }


</script>