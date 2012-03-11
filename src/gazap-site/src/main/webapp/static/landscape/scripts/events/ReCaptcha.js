(function () {
    var captchaKey = "";

    function recaptchaShowDefault() {
        var container = $('#recaptcha-container');
        container = container.empty();
        Recaptcha.create(captchaKey, container[0], {
            theme:"white",
            callback:Recaptcha.focus_response_field});
    }

    function recaptchaEnable(callback) {
        if (!callback) {
            callback = recaptchaShowDefault;
        }
        if (typeof Recaptcha == 'undefined') {
            var url = 'http://www.google.com/recaptcha/api/js/recaptcha_ajax.js';
            $.getScript(url, callback);
        } else {
            callback();
        }
    }

    UI.bindInitCaptcha(function (key) {
        captchaKey = key;
    });

    UI.bindReloadCaptcha(function (tabindex) {
        recaptchaEnable(function () {
            recaptchaShowDefault();
            Recaptcha.reload();
            $('#recaptcha_response_field').attr('tabindex', tabindex);
        });
    });
})();
