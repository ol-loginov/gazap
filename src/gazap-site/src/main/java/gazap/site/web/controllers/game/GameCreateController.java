package gazap.site.web.controllers.game;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.web.views.game.GameCreatePage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GameCreateController {
    @RequestMapping(value = "/game/create.ajax", method = RequestMethod.GET)
    @ViewName(response = GameCreatePage.class, name = "game/create.ajax")
    public GameCreatePage viewAjax() {
        GameCreatePage page = new GameCreatePage();
        page.setForm(new GameCreateForm());
        return page;
    }
}
