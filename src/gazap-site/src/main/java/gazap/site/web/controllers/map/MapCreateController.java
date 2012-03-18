package gazap.site.web.controllers.map;

import gazap.domain.entity.Map;
import gazap.site.model.ApiAnswer;
import gazap.site.model.viewer.MapTitle;
import gazap.site.services.MapService;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.controllers.ResponseBuilder;
import gazap.site.web.views.map.MapCreatePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class MapCreateController extends BaseController {
    @Autowired
    protected MapService mapService;

    @RequestMapping(value = "/map/create", method = RequestMethod.GET)
    public ModelAndView getForm(Locale locale) {
        MapCreatePage page = new MapCreatePage();
        page.setForm(new MapCreateForm());
        return responseBuilder(locale).view("map/create", page);
    }

    @RequestMapping(value = "/map/create.ajax", method = RequestMethod.POST)
    public ModelAndView create(Locale locale, @Valid MapCreateForm form, BindingResult binding) {
        ResponseBuilder response = responseBuilder(locale);
        MapCreateFormResult answer = new MapCreateFormResult();
        if (binding.hasErrors()) {
            response.validationErrors(answer, binding);
        } else {
            Map map = mapService.createMap(securityHelper.getCurrentUser(), form);
            answer.setMap(viewer.mapTitle(map));
            answer.setSuccess(true);
        }
        return response.json(answer);
    }

    public static class MapCreateFormResult extends ApiAnswer {
        private MapTitle map;

        public MapTitle getMap() {
            return map;
        }

        public void setMap(MapTitle map) {
            this.map = map;
        }
    }
}
