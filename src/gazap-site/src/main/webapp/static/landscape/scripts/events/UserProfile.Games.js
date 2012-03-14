UserProfile = {
    Games:{
        init:function () {
            var button = new ButtonHelper('#createGame');
            button.button.click(function () {
                button.visible(false);
                $.get(button.ajaxHref(), function (ans, status, xhr) {
                    $('#accountGameForm').empty().append(ans);
                });
            });
        }
    }
};