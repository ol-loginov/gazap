package waypalm.site.services;

import waypalm.domain.entity.Surface;
import waypalm.domain.entity.World;

public interface UserActionGuard {
    boolean controlSurface(Surface surface);

    boolean controlWorld(World world);
}
