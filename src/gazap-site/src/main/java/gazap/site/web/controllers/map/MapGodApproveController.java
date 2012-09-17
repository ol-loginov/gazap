package gazap.site.web.controllers.map;

import gazap.domain.entity.Geometry;
import gazap.domain.entity.Surface;
import gazap.site.exceptions.ObjectIllegalStateException;
import gazap.site.exceptions.ObjectNotFoundException;
import gazap.site.model.viewer.ContributionV;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class MapGodApproveController extends MapGodControllerBase {
    private static final String ACTION_URL = "/map/{surface}/approve";

    @RequestMapping(value = ACTION_URL, method = RequestMethod.GET)
    public ModelAndView getEditor(Locale locale, @PathVariable("surface") int surfaceId) throws ObjectNotFoundException, ObjectIllegalStateException {
        if (!auth.isAuthorized()) {
            throw new AccessDeniedException("you should be authorized to enter");
        }

        Surface surface = loadSurfaceById(surfaceId, locale);
        if (Geometry.Plain.CLASS.equals(surface.getGeometry().getGeometryClass())) {
            return responseBuilder(locale).view("map/plain-geometry-approve")
                    .addObject("surface", viewer.surfaceTitle(surface))
                    .addObject("geometry", surface.getGeometry());
        } else {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.geometryUnknown"));
        }
    }

    @RequestMapping(value = ACTION_URL + "/contribution/changes.ajax", method = RequestMethod.GET)
    public ModelAndView getLocalChanges(Locale locale, @PathVariable("surface") int surfaceId, @RequestParam(value = "after", defaultValue = "0") long after) throws ObjectIllegalStateException, ObjectNotFoundException, IOException {
        List<ContributionV> list = new ArrayList<>();
        return responseBuilder(locale).json(list);
    }
}
