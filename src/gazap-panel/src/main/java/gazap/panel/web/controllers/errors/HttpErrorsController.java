package gazap.panel.web.controllers.errors;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.panel.web.controllers.BaseController;
import gazap.panel.web.views.errors.ErrorPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/errors")
public class HttpErrorsController extends BaseController {
    private ErrorPage genericHttp(String code, String... suggestions) {
        ErrorPage page = new ErrorPage();
        page.setHttp(code);
        page.getSuggestions().addAll(Arrays.asList(suggestions));
        return page;
    }

    @RequestMapping({"/404", "/404.*"})
    @ViewName(response = ErrorPage.class, name = "errors/http")
    public ErrorPage for404() {
        return genericHttp("404",
                "search",
                "email");
    }

    @RequestMapping({"/500", "/500.*"})
    @ViewName(response = ErrorPage.class, name = "errors/http")
    public ErrorPage for500() {
        return genericHttp("500",
                "come-n-try",
                "email");
    }
}
