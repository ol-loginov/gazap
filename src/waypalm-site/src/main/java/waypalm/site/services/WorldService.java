package waypalm.site.services;

import org.springframework.transaction.annotation.Transactional;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.Surface;
import waypalm.site.model.constructor.CreateSurfaceForm;

public interface WorldService {
    @Transactional
    Surface createSurface(Profile creator, CreateSurfaceForm surfaceForm);
}
