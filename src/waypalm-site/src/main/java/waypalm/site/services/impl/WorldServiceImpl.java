package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waypalm.common.util.ObjectUtil;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.*;
import waypalm.site.model.world.MapCreateForm;
import waypalm.site.model.world.WorldCreateForm;
import waypalm.site.services.WorldService;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorldServiceImpl implements WorldService {
    @Autowired
    protected WorldRepository worldRepository;
    @Autowired
    protected UserRepository userRepository;

    @Override
    public World createWorld(UserProfile creator, WorldCreateForm form) {
        World world = new World();
        world.setTitle(form.getTitle());
        world.setAlias("");
        world.setMemo(ObjectUtil.ifNull(form.getMemo(), ""));
        world.setPublisherTitle(ObjectUtil.ifNull(form.getPublisherTitle(), ""));
        world.setPublisherUrl(ObjectUtil.ifNull(form.getPublisherUrl(), ""));
        worldRepository.create(world);

        WorldActor actor = new WorldActor(world, creator);
        actor.setAuthor(true);
        actor.setEditor(true);
        worldRepository.create(actor);

        UserSummary profileSummary = userRepository.getProfileSummary(creator);
        profileSummary.setWorldOwned(profileSummary.getWorldOwned() + 1);
        userRepository.save(profileSummary);

        return world;
    }

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

    @Override
    public List<World> getUserWorldList(UserProfile currentProfile) {
        List<World> result = new ArrayList<>();
        for (WorldActor actor : worldRepository.listWorldActor(currentProfile)) {
            result.add(actor.getWorld());
        }
        return result;
    }
}
