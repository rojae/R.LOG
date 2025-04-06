<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<script>
    $(document).ready(function () {
        let message = '<c:out value = "${message}"/>';
        if(message !== '')
            setTimeout(500, showModal("알림창", message));


        $('.btn-submit').click(function(){
            let content = $('form textArea[name=content]').val();
            let status = "ENABLE";

            if ($("input:checkbox[name='secretSwitchModal']").is(":checked"))
                status = "SECRET";

            if(!textCheck(content)){
                showModal('알림창', '빈 내용은 작성할 수 없습니다.');
                return;
            }

            let params = {
                content: content,
                status: status
            };

            $.ajax({
                url: "/comment/<c:out value = "${commentId}"/>",
                method: "put",
                data: JSON.stringify(params),
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    showModal('알림창', data.response);

                    if(data.code === '500'){
                        setTimeout(function (){
                            window.location.href = '/login';
                        }, 2000);
                    }
                    else {
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    }

                },
                error: function (error) {
                    console.log(error);
                }
            });
        });

    });
</script>

<form name="form" method="post">
    <input type="hidden" name="_method" value="PUT" />

    <div class="bg-light p-2">
        <div class="d-flex flex-row align-items-start">
            <sec:authorize access="isAuthenticated()">
                <img class="rounded-circle"
                     src="<sec:authentication property="principal.account.profileImage"/>"
                     width="40"
                     alt="image"/>
            </sec:authorize>

            <sec:authorize access="isAnonymous()">
                <img class="rounded-circle"
                     src="/assets/img/illustrations/profiles/profile-1.png" width="40"
                     alt="image"/>
            </sec:authorize>

            <textarea name="content" class="form-control ml-1 shadow-none textarea"
                      placeholder="여러분들의 소중한 의견을 남겨주세요"><c:out value = "${content}"/></textarea>
        </div>

    </div>
</form>
