package gazap.site.services;

import gazap.domain.entity.World;
import gazap.domain.entity.UserProfile;
import gazap.site.web.controllers.world.WorldCreateForm;
import org.springframework.transaction.annotation.Transactional;

public interface WorldService {
    @Transactional
    World createWorld(UserProfile creator, WorldCreateForm form);

    World findWorldByAliasOrId(String aliasOrId);
}
