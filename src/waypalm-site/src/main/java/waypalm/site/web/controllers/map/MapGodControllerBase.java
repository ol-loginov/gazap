package waypalm.site.web.controllers.map;

import org.springframework.beans.factory.annotation.Autowired;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.World;
import waypalm.site.exceptions.ObjectIllegalStateException;
import waypalm.site.exceptions.ObjectNotFoundException;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

public abstract class MapGodControllerBase extends BaseController {
    @Autowired
    protected WorldRepository worldRepository;

    protected Surface loadSurfaceByAlias(World world, String id, Locale locale) throws ObjectNotFoundException, ObjectIllegalStateException {
        Surface instance;
        try {
            instance = worldRepository.getSurface(Integer.parseInt(id));
        } catch (NumberFormatException nfe) {
            instance = worldRepository.findSurfaceByAlias(world, id);
        }
        if (instance == null) {
            throw new ObjectNotFoundException(Surface.class, id);
        }
        if (instance.getGeometry() == null) {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.noGeometrySpecified"));
        }
        return instance;
    }

    protected Surface loadSurfaceById(int surfaceId, Locale locale) throws ObjectNotFoundException, ObjectIllegalStateException {
        Surface instance = worldRepository.getSurface(surfaceId);
        if (instance == null) {
            throw new ObjectNotFoundException(Surface.class, surfaceId);
        }
        if (instance.getGeometry() == null) {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.noGeometrySpecified"));
        }
        return instance;
    }
}
