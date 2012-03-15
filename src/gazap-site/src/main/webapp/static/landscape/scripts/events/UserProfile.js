UserProfile = {
    Games:{
        init:function () {
            var button = new ButtonHelper('#createGame');
            button.button.click(function () {
                button.visible(false);
                $.get(button.ajaxHref(), function (ans, status, xhr) {
                    $('#accountGameForm').empty().append(ans).slideDown(200);
                });
            });
            BUS.game.create_dialog.close_modal(function (selector) {
                $('#accountGameForm').slideUp(200, function () {
                    $(this).empty();
                });
                button.visible(true);
            });
        }
    }
};