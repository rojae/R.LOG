<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<section class="bg-light py-10">
    <div class="row justify-content-center">
        <div class="col-lg-10 col-xl-8">
            <jsp:include page="./write.jsp"/>
        </div>
    </div>
    <div class="svg-border-rounded text-dark">
        <!-- Rounded SVG Border-->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 144.54 17.34" preserveAspectRatio="none"
             fill="currentColor">
            <path d="M144.54,17.34H0V0H144.54ZM0,0S32.36,17.34,72.27,17.34,144.54,0,144.54,0"></path>
        </svg>
    </div>
</section>

<!-- JQuery 2번 import 시 summernote error -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script>

    <c:if test = "${!empty category}">
    /*
        게시글 수정일 경우에, 작성된 게시글의 카테고리를 selected 해놔야 한다.
        카테고리와 제출 URI 수정하고, 다른 부분들은 summerNote에서 JSTL로 불러옴
     */
    function getCategory(categoryId) {
        let sel = document.getElementById("category-select");
        for (let i = 0; i < sel.length; i++) {
            if (sel[i].value == categoryId) {
                sel[i].selected = true;
            }
        }
    }

    function editFormAction(postId) {
        document.post.action = '/admin/write/' + postId;
    }

    $(document).ready(function () {
        setTimeout(function () {
            getCategory(`<c:out value = "${category.id}"/>`);
            editFormAction(`<c:out value = "${post.id}"/>`);
        }, 500);
    });
    </c:if>
</script>
