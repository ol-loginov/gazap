package waypalm.site.services.impl;

import waypalm.domain.dao.WorldRepository;
import waypalm.site.services.UserActionGuard;
import waypalm.domain.entity.*;

public class UserActionGuardImpl implements UserActionGuard {
    private final Profile visitor;
    private final WorldRepository worldRepository;

    public UserActionGuardImpl(Profile visitor, WorldRepository worldRepository) {
        this.visitor = visitor;
        this.worldRepository = worldRepository;
    }

    @Override
    public boolean controlSurface(Surface surface) {
        if (visitor == null) return false;
        ProfileSurfaceRel rel = worldRepository.getSurfaceRelation(surface, visitor);
        return rel != null && rel.isAuthor();
    }

    @Override
    public boolean controlWorld(World world) {
        if (visitor == null) return false;
        ProfileWorldRel rel = worldRepository.getWorldRelation(world, visitor);
        return rel != null && rel.isAuthor();
    }
}
