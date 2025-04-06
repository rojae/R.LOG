/*
 * 관리자만 사용하는 Javascript 파일
 */
function goManage() {
    $.ajax({
        url: '/gate/manage',
        method: "get",
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            if (data.code === '200') {
                showModal('알림창', data.response);
                setTimeout(function () {
                    window.location.href = '/manage';
                }, 2000);
            } else {
                showModal('알림창', data.response);
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
}