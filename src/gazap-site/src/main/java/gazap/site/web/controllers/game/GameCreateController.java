package gazap.site.web.controllers.game;

import com.iserv2.commons.mvc.views.Content;
import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.model.ApiAnswer;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.game.GameCreatePage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class GameCreateController extends BaseController {
    @RequestMapping(value = "/game/create.ajax", method = RequestMethod.GET)
    @ViewName(response = GameCreatePage.class, name = "game/create.ajax")
    public GameCreatePage formAjax() {
        GameCreatePage page = new GameCreatePage();
        page.setForm(new GameCreateForm());
        return page;
    }

    @RequestMapping(value = "/game/create.ajax", method = RequestMethod.POST)
    public Content submitAjax(Locale locale, @Valid GameCreateForm form, BindingResult formBinding) {
        CreateFormApiAnswer answer = new CreateFormApiAnswer();
        if (formBinding.hasErrors()) {
            contentFactory(locale).validationErrors(answer, formBinding);
        } else {
            answer.setSuccess(true);
        }
        return contentFactory(locale).json(answer);
    }

    public static class CreateFormApiAnswer extends ApiAnswer {
    }
}
