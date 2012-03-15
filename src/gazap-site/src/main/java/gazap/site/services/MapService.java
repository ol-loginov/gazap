package gazap.site.services;

import gazap.domain.entity.Map;
import gazap.domain.entity.UserProfile;
import gazap.site.web.controllers.map.MapCreateForm;
import org.springframework.transaction.annotation.Transactional;

public interface MapService {
    @Transactional
    Map createMap(UserProfile creator, MapCreateForm form);
}
