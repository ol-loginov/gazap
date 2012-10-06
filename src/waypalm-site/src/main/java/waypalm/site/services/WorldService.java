package waypalm.site.services;

import org.springframework.transaction.annotation.Transactional;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.UserProfile;
import waypalm.domain.entity.World;
import waypalm.site.model.world.MapCreateForm;
import waypalm.site.model.world.WorldCreateForm;

import java.util.List;

public interface WorldService {
    @Transactional
    Surface createSurface(World world, UserProfile creator, MapCreateForm form);

    @Transactional
    World createWorld(UserProfile creator, WorldCreateForm form);

    List<World> getUserWorldList(UserProfile currentProfile);
}
