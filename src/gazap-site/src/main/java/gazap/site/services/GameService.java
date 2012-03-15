package gazap.site.services;

import gazap.domain.entity.Game;
import gazap.domain.entity.UserProfile;
import gazap.site.web.controllers.game.GameCreateForm;
import org.springframework.transaction.annotation.Transactional;

public interface GameService {
    @Transactional
    Game createGame(UserProfile creator, GameCreateForm form);

    Game findGameByAliasOrId(String aliasOrId);
}
