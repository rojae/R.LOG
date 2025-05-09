window.kakaoAsyncInit = function () {
    Kakao.Channel.createAddChannelButton({
        container: '#kakao-talk-channel-add-button',
    });
};

(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s);
    js.id = id;
    js.src = 'https://developers.kakao.com/sdk/js/kakao.channel.min.js';
    fjs.parentNode.insertBefore(js, fjs);
})(document, 'script', 'kakao-js-sdk');