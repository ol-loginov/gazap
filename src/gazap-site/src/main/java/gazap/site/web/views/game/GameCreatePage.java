package gazap.site.web.views.game;

import gazap.site.web.controllers.game.GameCreateForm;
import gazap.site.web.views.GazapPage;

public class GameCreatePage extends GazapPage {
    private GameCreateForm form;

    public GameCreateForm getForm() {
        return form;
    }

    public void setForm(GameCreateForm form) {
        this.form = form;
    }
}
