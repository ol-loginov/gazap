package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import waypalm.common.util.ObjectUtil;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.*;
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
    public Surface createSurface(Profile creator, Geometry geometry) {
        worldRepository.create(geometry);

        Surface surface = new Surface();
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
