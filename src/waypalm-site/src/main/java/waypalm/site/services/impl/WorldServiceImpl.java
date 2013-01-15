package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.*;
import waypalm.site.model.constructor.CreateSurfaceForm;
import waypalm.site.services.WorldService;

@Service
public class WorldServiceImpl implements WorldService {
    @Autowired
    protected WorldRepository worldRepository;
    @Autowired
    protected UserRepository userRepository;

    @Override
    public Surface createSurface(Profile creator, CreateSurfaceForm surfaceForm) {
        Geometry geometry;
        switch (surfaceForm.getType()) {
            case PLANE:
                geometry = createPlaneGeometry(surfaceForm);
                break;
            default:
                throw new IllegalArgumentException("surface type " + surfaceForm.getType() + " is not supported");
        }
        worldRepository.create(geometry);

        Surface surface = new Surface();
        surface.setTitle("Untitled");
        surface.setGeometry(geometry);
        worldRepository.create(surface);

        ProfileSurfaceRel rel = new ProfileSurfaceRel(creator, surface);
        rel.setAuthor(true);
        rel.setEditor(true);
        rel.setFavourite(true);
        worldRepository.create(rel);

        return surface;
    }

    private GeometryPlain createPlaneGeometry(CreateSurfaceForm surfaceForm) {
        GeometryPlain geometry = new GeometryPlain();
        geometry.setOrientation(surfaceForm.getPlainOrientation());
        return geometry;
    }
}
