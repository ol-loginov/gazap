package gazap.site.services;

import gazap.domain.entity.Surface;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.World;
import gazap.site.web.controllers.map.MapCreateForm;
import org.springframework.transaction.annotation.Transactional;

public interface SurfaceService {
    @Transactional
    Surface createSurface(World world, UserProfile creator, MapCreateForm form);
}
