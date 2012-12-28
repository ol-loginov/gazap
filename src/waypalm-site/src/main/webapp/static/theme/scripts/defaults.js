$(function () {
    $('.mini-tabs').miniTabs({showEffect: 'show', showDuration: 0, hideEffect: 'hide', hideDuration: 0});
    $('.dropdown-toggle').dropdown();

    $.validator.setDefaults({onfocusout: false, onkeyup: false});
    $.validator.addMethod("validate-good", function (value, element) {
        return true;
    }, "");
});
