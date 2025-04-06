<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>글쓰기</title>
    <link rel="shortcut icon" href="#">
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

        function showCategories(root, depth) {
            for (let i in root.subCategories) {
                let name = root.subCategories[i].categoryName;
                let id = root.subCategories[i].categoryId;
                drawCategory(id, name, depth);
                showCategories(root.subCategories[i], depth + 1);
            }
        }

        function drawCategory(id, name, depth) {
            let stair = '';
            for (let i = 0; i < depth; i++)
                stair += '>';
            $("#category-select").append(`<option value="\${id}">\${stair} \${name}</option>`);
        }

        function goWrite(frm) {
            let title = frm.title.value;
            let content = frm.content.value;

            if (title.trim() == '') {
                alert("제목을 입력해주세요");
                return false;
            }
            if (content.trim() == '') {
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
                height: 600,
                fontNames: ['맑은고딕', 'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New',],
                fontNamesIgnoreCheck: ['맑은고딕'],
                focus: true,

                callbacks: {
                    onImageUpload: function (files, editor, welEditable) {
                        for (let i = files.length - 1; i >= 0; i--) {
                            sendFile(files[i], this);
                        }
                    }
                }
            });
        });

        function sendFile(file, el) {
            let form_data = new FormData();
            form_data.append('file', file);
            $.ajax({
                data: form_data,
                type: "POST",
                url: '/admin/write/image',
                cache: false,
                contentType: false,
                enctype: 'multipart/form-data',
                processData: false,
                success: function (img_name) {
                    $(el).summernote('editor.insertImage', img_name);
                }
            });
        }

        $('.summernote').summernote({maximumImageFileSize: 1048576});

        function saveThumbNail(input) {
            if (input.files && input.files[0]) {
                let form_data = new FormData();
                form_data.append('file', input.files[0]);
                $.ajax({
                    data: form_data,
                    type: "POST",
                    url: '/admin/write/image',
                    cache: false,
                    contentType: false,
                    enctype: 'multipart/form-data',
                    processData: false,
                    success: function (img_name) {
                        document.querySelector("#thumbnail").src = img_name;
                        document.post.thumbNail.value = img_name;
                    }
                });
            }
        }
    </script>
</head>
<body>
<div style="width: 100%; margin: auto;">
    <form name="post" method="post" action="/admin/write">
        <img src="${post.thumbNail}" style="width: 100px; height: 100px;" id="thumbnail" alt="썸네일"/>
        <!-- 버튼 클릭시 파일찾기로 링크해 준다 -->
        <input type="button" class="btn btn-primary btn-sm" value="썸네일 변경" onclick="getImageFile();">
        <!-- 화면에서 파일찾기를 안보이게 한다. -->
        <input type="file" onchange="saveThumbNail(this)" name="file" id="file" style="display: none;"/>
        <br/>
        <label for="category-select">카테고리</label>
        <select name="category" id="category-select">
        </select>
        <label for="post-status">공개/비공개</label>
        <select name="status" id="post-status">
            <option value="ENABLE" <c:if test="${post.status eq 'ENABLE'}">selected</c:if>>공개</option>
            <option value="PROTECTED" <c:if test="${post.status eq 'PROTECTED'}">selected</c:if>>비공개</option>
        </select>
        <input type="hidden" name="thumbNail" value="${post.thumbNail}"/>
        <br/>
        <input type="text" name="title" style="width: 40%;" placeholder="제목" value="${post.title}"/>
        <br/>
        <textarea name="header" style="width: 100%;" placeholder="머릿말">${post.header}</textarea>
        <textarea id="summernote" name="content">
            ${post.content}
        </textarea>
        <input id="subBtn" class="btn btn-primary btn-sm" type="button" value="작성완료" style="float: right;"
               onclick="goWrite(this.form)"/>
    </form>
</div>

</body>
</html>
