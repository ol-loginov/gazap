(function () {
    var captchaKey = "";

    function recaptchaShowDefault() {
        var container = $('#recaptcha-container').empty();
        Recaptcha.create(captchaKey, container[0], {theme:"white"});
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

    BUS.captcha.init(function (key) {
        captchaKey = key;
    });

    BUS.captcha.reload(function (tabindex) {
        recaptchaEnable(function () {
            recaptchaShowDefault();
            Recaptcha.reload();
        });
    });
})();
