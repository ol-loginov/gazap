package waypalm.site.services;

import waypalm.domain.entity.Surface;
import waypalm.domain.entity.UserProfile;
import waypalm.domain.entity.World;
import waypalm.site.model.world.MapCreateForm;
import org.springframework.transaction.annotation.Transactional;
import waypalm.site.model.world.WorldCreateForm;

public interface SurfaceService {
    @Transactional
    Surface createSurface(World world, UserProfile creator, MapCreateForm form);

    @Transactional
    World createWorld(UserProfile creator, WorldCreateForm form);
}
