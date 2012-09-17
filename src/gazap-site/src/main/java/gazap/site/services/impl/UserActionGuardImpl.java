package gazap.site.services.impl;

import gazap.domain.dao.WorldRepository;
import gazap.domain.entity.*;
import gazap.site.services.UserActionGuard;

public class UserActionGuardImpl implements UserActionGuard {
    private final UserProfile visitor;
    private final WorldRepository worldRepository;

    public UserActionGuardImpl(UserProfile visitor, WorldRepository worldRepository) {
        this.visitor = visitor;
        this.worldRepository= worldRepository;
    }

    @Override
    public boolean createSurface(int worldId) {
        if (visitor == null) {
            return false;
        }
        World world = worldRepository.getWorld(worldId);
        if (world != null) {
            WorldActor actor = worldRepository.getWorldActor(world, visitor);
            if (actor != null) {
                return actor.isEditor();
            }
        }
        return false;
    }

    @Override
    public boolean editSurface(int surfaceId) {
        if (visitor == null) {
            return false;
        }
        Surface surface = worldRepository.getSurface(surfaceId);
        if (surface != null) {
            SurfaceActor actor = worldRepository.getSurfaceActor(surface, visitor);
            if (actor != null) {
                return actor.isEditor();
            }
        }
        return false;
    }
}
