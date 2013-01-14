$(function () {
    $('.dropdown-toggle').dropdown();

    $.validator.setDefaults({onfocusout: false, onkeyup: false});
    $.validator.addMethod("validate-good", function (value, element) {
        return true;
    }, "");
});
