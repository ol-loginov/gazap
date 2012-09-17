package gazap.site.services.impl;

import gazap.domain.dao.WorldRepository;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.World;
import gazap.domain.entity.WorldActor;
import gazap.site.services.WorldService;
import gazap.site.web.controllers.world.WorldCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WorldServiceImpl implements WorldService {
    @Autowired
    protected WorldRepository worldRepository;

    @Override
    public World createWorld(UserProfile creator, WorldCreateForm form) {
        World world = new World();
        world.setTitle(form.getTitle());
        worldRepository.create(world);

        WorldActor actor = new WorldActor(world, creator);
        actor.setAuthor(true);
        actor.setEditor(true);
        worldRepository.create(actor);

        return world;
    }

    @Override
    public World findWorldByAliasOrId(String aliasOrId) {
        if (!StringUtils.hasText(aliasOrId)) {
            return null;
        }
        return Character.isDigit(aliasOrId.charAt(0))
                ? worldRepository.getWorld(Integer.parseInt(aliasOrId))
                : worldRepository.findWorldByAlias(aliasOrId);
    }
}
