package waypalm.site.services;

import org.springframework.transaction.annotation.Transactional;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.World;
import waypalm.site.model.world.MapCreateForm;
import waypalm.site.model.world.SurfaceCreateForm;
import waypalm.site.model.world.WorldCreateForm;

import java.util.List;

public interface WorldService {
    @Transactional
    Surface createSurface(World world, Profile creator, SurfaceCreateForm form);

    @Transactional
    World createWorld(Profile creator, WorldCreateForm form);

    List<World> getUserWorldList(Profile currentProfile);

    World findWorldBySlug(String slugText);
}
