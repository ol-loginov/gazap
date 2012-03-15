package gazap.site.services;

import gazap.domain.entity.GameProfile;
import gazap.domain.entity.UserProfile;
import gazap.site.web.controllers.game.GameCreateForm;
import org.springframework.transaction.annotation.Transactional;

public interface GameService {
    @Transactional
    GameProfile createGame(UserProfile creator, GameCreateForm form);
}
