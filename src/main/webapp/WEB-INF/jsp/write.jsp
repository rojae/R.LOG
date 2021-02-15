<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>글쓰기</title>
    <link rel="shortcut icon" href="#">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous">
    </script>
    <link href="/css/summernote/summernote-lite.css" rel="stylesheet"/>
    <script src="/js/summernote/summernote-lite.js"></script>
    <script src="/js/summernote/summernote-ko-KR.js"></script>
    <script>
        function goWrite(frm) {
            var title = frm.title.value;
            var content = frm.content.value;

            if (title.trim() == ''){
                alert("제목을 입력해주세요");
                return false;
            }
            if (content.trim() == ''){
                alert("내용을 입력해주세요");
                return false;
            }
            frm.submit();
        }
    </script>

    <script>
        $(document).ready(function () {
            $('#summernote').summernote({
                placeholder: 'content',
                minHeight: 370,
                maxHeight: null,
                focus: true,
                lang: 'ko-KR'
            });
        });
        $('.summernote').summernote({ maximumImageFileSize: 1048576 });

    </script>



</head>
<body>
<h2 style="text-align: center;">글 작성</h2><br><br><br>

<div style="width: 60%; margin: auto;">
    <form method="post" action="/write">
        <input type="text" name="title" style="width: 40%;" placeholder="제목"/>
        <br><br>
        <textarea id="summernote" name="content"></textarea>
        <input id="subBtn" type="button" value="글 작성" style="float: right;" onclick="goWrite(this.form)"/>
    </form>
</div>

</body>
</html>
