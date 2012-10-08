package waypalm.site.services.impl;

import waypalm.domain.dao.WorldRepository;
import waypalm.site.services.UserActionGuard;
import waypalm.domain.entity.*;

public class UserActionGuardImpl implements UserActionGuard {
    private final UserProfile visitor;
    private final WorldRepository worldRepository;

    public UserActionGuardImpl(UserProfile visitor, WorldRepository worldRepository) {
        this.visitor = visitor;
        this.worldRepository = worldRepository;
    }

    @Override
    public boolean controlSurface(Surface surface) {
        if (visitor == null) return false;
        SurfaceActor actor = worldRepository.getSurfaceActor(surface, visitor);
        return actor != null && actor.isAuthor();
    }

    @Override
    public boolean controlWorld(World world) {
        if (visitor == null) return false;
        WorldActor actor = worldRepository.getWorldActor(world, visitor);
        return actor != null && actor.isAuthor();
    }
}
