package gazap.site.services.impl;

import gazap.common.util.GravatarHelper;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.GameProfile;
import gazap.domain.entity.UserProfile;
import gazap.site.model.viewer.GameTitle;
import gazap.site.model.viewer.UserTitle;
import gazap.site.services.FormatService;
import gazap.site.services.ModelViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelViewerImpl implements ModelViewer {
    @Autowired
    protected FormatService formatService;
    @Autowired
    protected UserProfileDao userProfileDao;

    @Override
    public UserTitle userTitle(UserProfile profile) {
        UserTitle title = new UserTitle();
        title.setId(profile.getId());
        title.setAlias(profile.getAlias());
        title.setName(profile.getDisplayName());
        title.setGravatar(GravatarHelper.hashOrDefault(profile.getEmail()));
        title.setRoute("/user/" + (profile.getAlias() == null ? Integer.toString(profile.getId()) : profile.getAlias()));
        title.setSummary(userProfileDao.loadSummary(profile));
        return title;
    }

    @Override
    public GameTitle gameTitle(GameProfile game) {
        GameTitle title = new GameTitle();
        title.setTitle(game.getTitle());
        title.setAlias(game.getAlias());
        return title;
    }
}
