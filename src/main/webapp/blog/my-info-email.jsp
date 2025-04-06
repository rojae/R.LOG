<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<script>
    $(document).ready(function () {
        let message = '<c:out value = "${message}"/>';

        if(message !== '')
            setTimeout(500, showModal("알림창", message));

        $('.btn-submit').click(function(){
            let currentPwd = $('#input-current-password').val();
            let newMail = $('#input-new-mail').val();

            if(!textCheck(currentPwd)){
                alert("빈 칸은 입력할 수 없습니다.")
                return;
            }

            let data = {
                currentPwd: currentPwd,
                newMail: newMail
            };

            $.ajax({
                url: "/my/info/email",
                method: "post",
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    showModal('알림창', data.response);

                    if(data.code === '200'){
                        setTimeout(function () {
                            window.location.replace('/logout');
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


<form class="form-inline">
    <div class="form-group mb-2">
        <label for="current-password" class="sr-only">현재 비밀번호</label>
        <input type="text" readonly class="form-control-plaintext" id="current-password" value="현재 비밀번호">
    </div>
    <div class="form-group form-group-sm  mb-2">
        <label for="input-current-password" class="sr-only">Password</label>
        <input type="password" class="form-control form-control-sm" id="input-current-password" placeholder="Current Password" autocomplete="off">
    </div>
</form>

<form class="form-inline">
    <div class="form-group mb-2">
        <label for="new-password" class="sr-only">새로운 이메일</label>
        <input type="text" readonly class="form-control-plaintext" id="new-password" value="새로운 이메일">
    </div>
    <div class="form-group form-group-sm  mb-2">
        <label for="input-new-mail" class="sr-only">new email</label>
        <input type="text" class="form-control form-control-sm" id="input-new-mail" placeholder="New Email" autocomplete="off">
    </div>
</form>

