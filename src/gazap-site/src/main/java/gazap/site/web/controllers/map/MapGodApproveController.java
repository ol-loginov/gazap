package gazap.site.web.controllers.map;

import gazap.domain.entity.*;
import gazap.site.exceptions.ObjectIllegalStateException;
import gazap.site.exceptions.ObjectNotFoundException;
import gazap.site.model.viewer.ContributionV;
import gazap.site.services.ModelViewerSet;
import gazap.site.web.views.map.MapEditPlainPage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class MapGodApproveController extends MapGodControllerBase {
    private static final String ACTION_URL = "/map/{map}/approve";

    @RequestMapping(value = ACTION_URL, method = RequestMethod.GET)
    public ModelAndView getEditor(Locale locale, @PathVariable("map") String mapId) throws ObjectNotFoundException, ObjectIllegalStateException {
        if (!auth.isAuthorized()) {
            throw new AccessDeniedException("you should be authorized to enter");
        }

        Map map = loadMapById(mapId, locale);

        if (Geometry.Plain.CLASS.equals(map.getGeometry().getGeometryClass())) {
            MapEditPlainPage page = new MapEditPlainPage();
            page.setGeometry((GeometryPlain) map.getGeometry());
            page.setMap(viewer.mapTitle(map));
            return responseBuilder(locale).view("map/plain-geometry-approve", page);
        } else {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.geometryUnknown"));
        }
    }

    @RequestMapping(value = ACTION_URL + "/contribution/changes.ajax", method = RequestMethod.GET)
    public ModelAndView getLocalChanges(Locale locale, @PathVariable("map") String mapId, @RequestParam(value = "after", defaultValue = "0") long after) throws ObjectIllegalStateException, ObjectNotFoundException, IOException {
        Map map = loadMapById(mapId, locale);
        UserProfile visitor = auth.getCurrentProfile();
        MapGodControllerLocalChangesApiAnswer answer = new MapGodControllerLocalChangesApiAnswer();

        List<ContributionV> list = new ArrayList<ContributionV>();
        for (Contribution c : mapDao.listContributionsToApprove(map, visitor, new Date(after))) {
            list.add(viewer.mapContribution(c, ModelViewerSet.ADD_AUTHOR_DETAILS));
        }
        answer.setList(list);
        return responseBuilder(locale).json(answer);
    }
}
