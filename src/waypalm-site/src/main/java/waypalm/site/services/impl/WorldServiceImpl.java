package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import waypalm.common.util.ObjectUtil;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.*;
import waypalm.site.model.world.SurfaceCreateForm;
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
    public World createWorld(Profile creator, WorldCreateForm form) {
        World world = new World();
        world.setTitle(form.getTitle());
        world.setHidden(true);
        worldRepository.create(world);

        WorldPublishing publishing = new WorldPublishing(world);
        publishing.setMemo(ObjectUtil.ifNull(form.getMemo(), ""));
        publishing.setPublisherTitle(ObjectUtil.ifNull(form.getPublisherTitle(), ""));
        publishing.setPublisherUrl(ObjectUtil.ifNull(form.getPublisherUrl(), ""));
        worldRepository.create(publishing);

        ProfileWorldRel rel = new ProfileWorldRel(creator, world);
        rel.setAuthor(true);
        rel.setEditor(true);
        rel.setFavourite(true);
        worldRepository.create(rel);

        ProfileSummary profileSummary = userRepository.getProfileSummary(creator);
        profileSummary.setWorldOwned(profileSummary.getWorldOwned() + 1);
        userRepository.save(profileSummary);

        return world;
    }

    @Override
    public Surface createSurface(World world, Profile creator, SurfaceCreateForm form) {
        Geometry geometry;
        switch (form.getGeometry()) {
            case PLANE:
                GeometryPlain plain = new GeometryPlain();
                plain.setOrientation(form.getPlainOrientation());
                geometry = plain;
                break;
            default:
                throw new IllegalArgumentException("Surface kind is not supported");
        }
        worldRepository.create(geometry);

        Surface surface = new Surface();
        surface.setWorld(world);
        surface.setAlias(form.getAlias());
        surface.setTitle(form.getTitle());
        surface.setMain(false);
        surface.setHidden(true);
        surface.setGeometry(geometry);
        worldRepository.create(surface);

        ProfileSurfaceRel rel = new ProfileSurfaceRel(creator, surface);
        rel.setAuthor(true);
        rel.setEditor(true);
        rel.setFavourite(true);
        worldRepository.create(rel);

        world.setSurfaceCount(world.getSurfaceCount() + 1);
        worldRepository.save(world);

        return surface;
    }

    @Override
    public List<World> getUserWorldList(Profile currentProfile) {
        List<World> result = new ArrayList<>();
        for (ProfileWorldRel rel : worldRepository.listWorldRelation(currentProfile)) {
            result.add(rel.getWorld());
        }
        return result;
    }

    @Override
    public World findWorldBySlug(String slugText) {
        if (!StringUtils.hasText(slugText)) {
            return null;
        }
        if (ObjectUtil.isInteger(slugText)) {
            return worldRepository.getWorld(Integer.parseInt(slugText));
        } else {
            return worldRepository.findWorldByAlias(slugText);
        }
    }
}
