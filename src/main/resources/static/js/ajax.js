jQuery(function($){
    $(document).ajaxSend(function() {
        $("#overlay").fadeIn(300);
    });

    $(document).ajaxComplete(function() {
        setTimeout(function(){
            $("#overlay").fadeOut(300);
        },500);
    });

});