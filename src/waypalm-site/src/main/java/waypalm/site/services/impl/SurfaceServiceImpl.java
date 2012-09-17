package waypalm.site.services.impl;

import waypalm.domain.dao.WorldRepository;
import waypalm.site.services.SurfaceService;
import waypalm.site.web.controllers.map.MapCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waypalm.domain.entity.*;

@Service
public class SurfaceServiceImpl implements SurfaceService {
    @Autowired
    protected WorldRepository worldRepository;

    @Override
    public Surface createSurface(World world, UserProfile creator, MapCreateForm form) {
        Geometry geometry;
        if (Geometry.Geoid.CLASS.equals(form.getGeometryClass())) {
            GeometryGeoid geoid = new GeometryGeoid();
            geoid.setRadiusX(form.getGeoidRadiusX());
            geoid.setRadiusY(form.getGeoidRadiusY());
            geoid.setRadiusZ(form.getGeoidRadiusZ());
            geometry = geoid;
        } else if (Geometry.Plain.CLASS.equals(form.getGeometryClass())) {
            GeometryPlain plain = new GeometryPlain();
            plain.setScaleMax(1);
            plain.setScaleMin(1);
            geometry = plain;
        } else {
            throw new IllegalArgumentException("geometry " + form.getGeometryClass() + " is not supported");
        }
        worldRepository.create(geometry);

        Surface surface = new Surface();
        surface.setWorld(world);
        surface.setTitle(form.getTitle());
        surface.setGeometry(geometry);
        worldRepository.create(surface);

        SurfaceActor actor = new SurfaceActor(surface, creator);
        actor.setAuthor(true);
        actor.setEditor(true);
        worldRepository.create(actor);

        return surface;
    }
}
