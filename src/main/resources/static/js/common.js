function showModal(title = 'Please Insert Title', body = 'Please Insert Body') {
    $('#rlog-modal .modal-header').html(`<h5 class="modal-title">${title}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>`);

    $('#rlog-modal .modal-body').html(`<p>${body}</p>`);
    $('#rlog-modal .modal-footer').html(`<button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">Close</button>`);
    $('#rlog-modal').modal('show');
}

function showModalWithSubmit(title = 'Please Insert Title', body = 'Please Insert Body', footer = 'Please Insert Footer') {
    $('#rlog-modal .modal-header').html(`<h5 class="modal-title">${title}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>`);

    $('#rlog-modal .modal-body').html(`<p>${body}</p>`);
    $('#rlog-modal .modal-footer').html(`<button class = "btn btn-sm btn-primary btn-submit">작성하기</button>`);
    $('#rlog-modal').modal('show');
}


function showModalWithFooter(title = 'Please Insert Title', body = 'Please Insert Body', footer = 'Please Insert Footer') {
    $('#rlog-modal .modal-header').html(`<h5 class="modal-title">${title}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>`);

    $('#rlog-modal .modal-body').html(`<p>${body}</p>`);
    $('#rlog-modal .modal-footer').html(footer);
    $('#rlog-modal').modal('show');
}

function ready(userName = '사용자') {
    if (userName === "")
        userName = '사용자';
    showModal(`${userName}님 죄송합니다...`, `해당 기능은 수정 중 입니다. <br/>최대한 빠르게 해결하겠습니다`);
}

function myInfo() {
    window.location.href = '/my/info';
}

function goLogin() {
    window.location.href = '/login';
}

function goLogout() {
    window.location.href = '/logout';
}

function textCheck(txt) {
    return txt.length !== 0;
}

function getImageFile(){
    $('input[name="file"]').click();
}

function isCheck(id){
    return !!$("input:checkbox[id='" + id + "']").is(":checked");
}



