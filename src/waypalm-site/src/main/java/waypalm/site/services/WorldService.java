package waypalm.site.services;

import org.springframework.transaction.annotation.Transactional;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.Surface;
import waypalm.site.model.constructor.CreatePlaneForm;

public interface WorldService {
    @Transactional
    Surface createSurface(Profile creator, CreatePlaneForm planeForm);
}
