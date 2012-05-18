package gazap.site.services.impl;

import gazap.common.util.GravatarHelper;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.World;
import gazap.domain.entity.Map;
import gazap.domain.entity.UserProfile;
import gazap.site.model.viewer.WorldTitle;
import gazap.site.model.viewer.MapTitle;
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
    public WorldTitle worldTitle(World world) {
        WorldTitle title = new WorldTitle();
        title.setId(world.getId());
        title.setTitle(world.getTitle());
        title.setAlias(world.getAlias());
        title.setRoute("/world/" + (world.getAlias() == null ? Integer.toString(world.getId()) : world.getAlias()));
        return title;
    }

    @Override
    public MapTitle mapTitle(Map map) {
        MapTitle title = new MapTitle();
        title.setId(map.getId());
        title.setTitle(map.getTitle());
        title.setAlias(map.getAlias());
        title.setRoute("/map/" + (map.getAlias() == null ? Integer.toString(map.getId()) : map.getAlias()));
        return title;
    }
}
