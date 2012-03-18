package gazap.site.web.controllers.game;

import gazap.domain.entity.Game;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class GameCreateController extends BaseController {
    @Autowired
    protected GameService gameService;

    @RequestMapping(value = "/game/create.ajax", method = RequestMethod.GET)
    public ModelAndView formAjax(Locale locale) {
        GameCreatePage page = new GameCreatePage();
        page.setForm(new GameCreateForm());
        return responseBuilder(locale).view("game/create.ajax", page);
    }

    @RequestMapping(value = "/game/create.ajax", method = RequestMethod.POST)
    public ModelAndView submitAjax(Locale locale, @Valid GameCreateForm form, BindingResult formBinding) {
        ResponseBuilder responseBuilder = responseBuilder(locale);
        CreateFormApiAnswer answer = new CreateFormApiAnswer();
        if (formBinding.hasErrors()) {
            responseBuilder.validationErrors(answer, formBinding);
        } else {
            Game game = gameService.createGame(securityHelper.getCurrentUser(), form);
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
