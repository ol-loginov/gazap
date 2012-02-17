package gazap.site.web.controllers.errors;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.errors.HttpError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/errors")
public class HttpErrorsController extends BaseController {
    private HttpError genericHttp(String code, String... suggestions) {
        HttpError page = new HttpError();
        page.setHttp(code);
        page.getSuggestions().addAll(Arrays.asList(suggestions));
        return page;
    }

    @RequestMapping({"/404", "/404.*"})
    @ViewName(response = HttpError.class, name = "errors/http")
    public HttpError for404() {
        return genericHttp("404",
                "search",
                "email");
    }

    @RequestMapping({"/500", "/500.*"})
    @ViewName(response = HttpError.class, name = "errors/http")
    public HttpError for500() {
        return genericHttp("500",
                "come-n-try",
                "email");
    }
}
