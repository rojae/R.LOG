<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>글쓰기</title>
    <link rel="shortcut icon" href="#">
    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
    </script>
    <link href="/css/summernote/summernote-lite.css" rel="stylesheet"/>
    <script src="/js/summernote/summernote-lite.js"></script>
    <script src="/js/summernote/summernote-ko-KR.js"></script>
    <script>
        function goCategory() {
            $.ajax({
                url: '/categories',
                dataType: 'json',
                success: function (data) {
                    showCategories(data, 1);
                }
            });
        }

        function showCategories(root, depth){
            for(let i in root.subCategories){
                let name = root.subCategories[i].categoryName;
                let id = root.subCategories[i].categoryId;
                drawCategory(id, name, depth);
                showCategories(root.subCategories[i], depth + 1);
            }
        }

        function drawCategory(id, name, depth){
            let stair = '';
            for(let i = 0; i < depth; i++)
                stair += '>';
            $("#category-select").append(`<option value="\${id}">\${stair} \${name}</option>`);
        }

        function goWrite(frm) {
            let title = frm.title.value;
            let content = frm.content.value;

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
            goCategory();

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
        <label for="category-select">카테고리</label>
        <select name="category" id="category-select">
        </select>

        <input type="text" name="title" style="width: 40%;" placeholder="제목"/>
        <textarea id="summernote" name="content"></textarea>
        <input id="subBtn" type="button" value="글 작성" style="float: right;" onclick="goWrite(this.form)"/>
    </form>
</div>

</body>
</html>
