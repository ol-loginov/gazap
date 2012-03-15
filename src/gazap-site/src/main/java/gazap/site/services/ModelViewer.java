package gazap.site.services;

import gazap.domain.entity.Game;
import gazap.domain.entity.Map;
import gazap.domain.entity.UserProfile;
import gazap.site.model.viewer.GameTitle;
import gazap.site.model.viewer.MapTitle;
import gazap.site.model.viewer.UserTitle;

public interface ModelViewer {
    UserTitle userTitle(UserProfile profile);

    GameTitle gameTitle(Game game);

    MapTitle mapTitle(Map map);
}
