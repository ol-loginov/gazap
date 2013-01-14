package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.GeometryPlain;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.ProfileSurfaceRel;
import waypalm.domain.entity.Surface;
import waypalm.site.model.constructor.CreatePlaneForm;
import waypalm.site.services.WorldService;

@Service
public class WorldServiceImpl implements WorldService {
    @Autowired
    protected WorldRepository worldRepository;
    @Autowired
    protected UserRepository userRepository;

    @Override
    public Surface createSurface(Profile creator, CreatePlaneForm planeForm) {
        GeometryPlain geometry = new GeometryPlain();
        geometry.setOrientation(planeForm.getOrientation());
        worldRepository.create(geometry);

        Surface surface = new Surface();
        surface.setTitle(planeForm.getTitle());
        surface.setGeometry(geometry);
        worldRepository.create(surface);

        ProfileSurfaceRel rel = new ProfileSurfaceRel(creator, surface);
        rel.setAuthor(true);
        rel.setEditor(true);
        rel.setFavourite(true);
        worldRepository.create(rel);

        return surface;
    }
}
