package gazap.site.services;

import gazap.domain.entity.GameProfile;
import gazap.domain.entity.UserProfile;
import gazap.site.model.viewer.GameTitle;
import gazap.site.model.viewer.UserTitle;

public interface ModelViewer {
    UserTitle userTitle(UserProfile profile);

    GameTitle gameTitle(GameProfile game);
}
