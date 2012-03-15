package gazap.site.web.controllers.game;

import com.iserv2.commons.mvc.views.Content;
import com.iserv2.commons.mvc.views.ViewName;
import gazap.domain.entity.GameProfile;
import gazap.site.model.ApiAnswer;
import gazap.site.model.viewer.GameTitle;
import gazap.site.services.GameService;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.controllers.ResponseBuilder;
import gazap.site.web.views.game.GameCreatePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class GameCreateController extends BaseController {
    @Autowired
    protected GameService gameService;

    @RequestMapping(value = "/game/create.ajax", method = RequestMethod.GET)
    @ViewName(response = GameCreatePage.class, name = "game/create.ajax")
    public GameCreatePage formAjax() {
        GameCreatePage page = new GameCreatePage();
        page.setForm(new GameCreateForm());
        return page;
    }

    @RequestMapping(value = "/game/create.ajax", method = RequestMethod.POST)
    public Content submitAjax(Locale locale, @Valid GameCreateForm form, BindingResult formBinding) {
        ResponseBuilder responseBuilder = responseBuilder(locale);
        CreateFormApiAnswer answer = new CreateFormApiAnswer();
        if (formBinding.hasErrors()) {
            responseBuilder.validationErrors(answer, formBinding);
        } else {
            GameProfile game = gameService.createGame(securityHelper.getCurrentUser(), form);
            answer.setGame(viewer.gameTitle(game));
            answer.setSuccess(true);
        }
        return responseBuilder.json(answer);
    }

    public static class CreateFormApiAnswer extends ApiAnswer {
        private GameTitle game;

        public GameTitle getGame() {
            return game;
        }

        public void setGame(GameTitle game) {
            this.game = game;
        }
    }
}
