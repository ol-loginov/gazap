package gazap.site.web.controllers.catalog;

import gazap.common.web.model.Anchor;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.catalog.Listing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Locale;

@Controller
public class CatalogIndexController extends BaseController {
    @RequestMapping("/catalog")
    public ModelAndView welcome(final Locale locale) {
        Listing page = new Listing();
        page.setWelcome(true);
        page.getSuggestions().addAll(new ArrayList<Anchor>() {{
            add(new Anchor("/catalog/games", format.getMessage(locale, "catalog.suggestions.section")));
        }});
        return responseBuilder(locale).view("catalog/index", page);
    }

    @RequestMapping("/catalog/{cat1}")
    public ModelAndView welcome(final Locale locale, @PathVariable("cat1") final String cat1) {
        Listing page = new Listing();
        page.getBreadcrumbs().addAll(new ArrayList<Anchor>() {{
            add(new Anchor("/catalog/games", format.getMessage(locale, "catalog.section." + cat1)));
        }});
        page.getSuggestions().addAll(new ArrayList<Anchor>() {{
            add(new Anchor("/catalog/games", format.getMessage(locale, "catalog.section." + cat1 + ".suggestions")));
        }});
        return responseBuilder(locale).view("catalog/index", page);
    }
}
