package waypalm.site.services;

import org.springframework.transaction.annotation.Transactional;
import waypalm.domain.entity.Geometry;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.Surface;

public interface WorldService {
    @Transactional
    Surface createSurface(Profile creator, Geometry geometry);
}
