package gazap.site.services;

import gazap.domain.entity.GameProfile;
import gazap.domain.entity.UserProfile;
import gazap.site.web.controllers.game.GameCreateForm;

public interface GameService {
    GameProfile createGame(UserProfile creator, GameCreateForm form);
}
