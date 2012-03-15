package gazap.site.web.controllers.access;

import com.iserv2.commons.mvc.views.Content;
import gazap.site.model.ApiAnswer;
import gazap.site.web.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
public class StatusController extends BaseController {
    public final static String ROUTE = "/auth/status";

    @RequestMapping(ROUTE + ".json")
    public Content statusJson(Locale locale) {
        ApiAnswer answer = new ApiAnswer();
        answer.setSuccess(true);
        return responseBuilder(locale).json(answer);
    }
}
