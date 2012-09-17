package waypalm.site.services;

import waypalm.domain.entity.World;
import waypalm.domain.entity.UserProfile;
import waypalm.site.web.controllers.world.WorldCreateForm;
import org.springframework.transaction.annotation.Transactional;

public interface WorldService {
    @Transactional
    World createWorld(UserProfile creator, WorldCreateForm form);

    World findWorldByAliasOrId(String aliasOrId);
}
