package gazap.site.web.controllers.map;

import gazap.domain.dao.MapDao;
import gazap.domain.entity.Geometry;
import gazap.domain.entity.Map;
import gazap.site.exceptions.ObjectIllegalStateException;
import gazap.site.exceptions.ObjectNotFoundException;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.map.MapEditPlainPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class MapEditController extends BaseController {
    @Autowired
    private MapDao mapDao;

    @RequestMapping(value = "/map/{map}/god", method = RequestMethod.GET)
    public ModelAndView getEditor(Locale locale, @PathVariable("map") String map) throws ObjectNotFoundException, ObjectIllegalStateException {
        Map mapInstance;
        try {
            mapInstance = mapDao.getMap(Integer.parseInt(map));
        } catch (NumberFormatException nfe) {
            mapInstance = mapDao.findMapByAlias(map);
        }

        if (mapInstance == null) {
            throw new ObjectNotFoundException(Map.class, map);
        }

        if (mapInstance.getGeometry() == null) {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.noGeometrySpecified"));
        }

        if (Geometry.Plain.CLASS.equals(mapInstance.getGeometry().getGeometryClass())) {
            MapEditPlainPage page = new MapEditPlainPage();
            return responseBuilder(locale).view("map/edit-plain-geometry", page);
        } else {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.geometryUnknown"));
        }
    }

}
