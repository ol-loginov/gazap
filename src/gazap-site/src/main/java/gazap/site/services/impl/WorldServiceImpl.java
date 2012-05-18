package gazap.site.services.impl;

import gazap.domain.dao.WorldDao;
import gazap.domain.entity.World;
import gazap.domain.entity.UserWorldRole;
import gazap.domain.entity.UserWorldRoles;
import gazap.domain.entity.UserProfile;
import gazap.site.services.WorldService;
import gazap.site.web.controllers.world.WorldCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WorldServiceImpl implements WorldService {
    @Autowired
    protected WorldDao worldDao;

    @Override
    public World createWorld(UserProfile creator, WorldCreateForm form) {
        World world = new World();
        world.setTitle(form.getTitle());
        worldDao.create(world);

        UserWorldRole role = new UserWorldRole(creator, world, UserWorldRoles.CREATOR);
        worldDao.create(role);

        return world;
    }

    @Override
    public World findWorldByAliasOrId(String aliasOrId) {
        if (!StringUtils.hasText(aliasOrId)) {
            return null;
        }
        return Character.isDigit(aliasOrId.charAt(0))
                ? worldDao.getWorld(Integer.parseInt(aliasOrId))
                : worldDao.findWorldByAlias(aliasOrId);
    }
}
