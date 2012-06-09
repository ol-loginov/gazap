package gazap.site.web.controllers.map;

import gazap.domain.dao.MapDao;
import gazap.domain.entity.Map;
import gazap.site.exceptions.ObjectIllegalStateException;
import gazap.site.exceptions.ObjectNotFoundException;
import gazap.site.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

public abstract class MapGodControllerBase extends BaseController {
    @Autowired
    protected MapDao mapDao;

    protected Map loadMapById(String id, Locale locale) throws ObjectNotFoundException, ObjectIllegalStateException {
        Map mapInstance;
        try {
            mapInstance = mapDao.getMap(Integer.parseInt(id));
        } catch (NumberFormatException nfe) {
            mapInstance = mapDao.findMapByAlias(id);
        }
        if (mapInstance == null) {
            throw new ObjectNotFoundException(Map.class, id);
        }
        if (mapInstance.getGeometry() == null) {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.noGeometrySpecified"));
        }
        return mapInstance;
    }
}
