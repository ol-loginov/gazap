package gazap.site.services.impl;

import gazap.common.util.GravatarHelper;
import gazap.domain.dao.MapDao;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.*;
import gazap.site.model.viewer.ContributionV;
import gazap.site.model.viewer.MapTitle;
import gazap.site.model.viewer.UserTitle;
import gazap.site.model.viewer.WorldTitle;
import gazap.site.services.FormatService;
import gazap.site.services.ModelViewer;
import gazap.site.services.ModelViewerSet;
import gazap.site.services.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelViewerImpl implements ModelViewer {
    @Autowired
    protected FormatService formatService;
    @Autowired
    protected UserProfileDao userProfileDao;
    @Autowired
    protected MapDao mapDao;
    @Autowired
    protected UserAccess auth;

    @Override
    public UserTitle userTitle(UserProfile profile) {
        UserTitle title = new UserTitle();
        title.setId(profile.getId());
        title.setAlias(profile.getAlias());
        title.setName(profile.getDisplayName());
        title.setGravatar(GravatarHelper.hashOrDefault(profile.getEmail()));
        title.setRoute("/user/" + (profile.getAlias() == null ? Integer.toString(profile.getId()) : profile.getAlias()));
        title.setSummary(userProfileDao.loadSummary(profile));
        title.setMe(profile.isSame(auth.getCurrentProfile()));
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
    public MapTitle mapTitle(Map map, ModelViewerSet... viewSet) {
        MapTitle title = new MapTitle();
        title.setId(map.getId());
        title.setTitle(map.getTitle());
        title.setAlias(map.getAlias());
        title.setRoute("/map/" + (map.getAlias() == null ? Integer.toString(map.getId()) : map.getAlias()));

        if (viewSet != null) {
            for (ModelViewerSet set : viewSet) {
                mapTitleSet(title, map, set);
            }
        }

        return title;
    }

    private void mapTitleSet(MapTitle view, Map map, ModelViewerSet set) {
        switch (set) {
            case ADD_MAP_APPROVE_COUNT:
                view.setApproveCount(mapDao.countMapPendingApproves(auth.getCurrentProfile(), map));
                break;
        }
    }

    @Override
    public ContributionV mapContribution(Contribution c, ModelViewerSet... viewSet) {
        if (ContributionTile.CLASS.equals(c.getContributionClass())) {
            return mapContribution((ContributionTile) c, viewSet);
        }
        return null;
    }

    public ContributionV mapContribution(ContributionTile c, ModelViewerSet[] viewSet) {
        ContributionV result = new ContributionV();
        mapContributionAttributes(result, c, viewSet);
        result.setAction(c.getAction());
        result.setScale(c.getScale());
        result.setSize(c.getSize());
        result.setX(c.getX());
        result.setY(c.getY());
        result.setFile(c.getFile());
        return result;
    }

    private void mapContributionAttributes(ContributionV destination, Contribution source, ModelViewerSet[] viewSet) {
        destination.setId(source.getId());
        destination.setCreatedAt(source.getCreatedAt());
        destination.setType(source.getContributionClass());
        destination.setDecision(source.getDecision());
        if (viewSet != null) {
            for (ModelViewerSet set : viewSet) {
                mapContributionAttributesSet(destination, source, set);
            }
        }
    }

    private void mapContributionAttributesSet(ContributionV destination, Contribution source, ModelViewerSet set) {
        switch (set) {
            case ADD_AUTHOR_DETAILS:
                destination.setAuthor(source.getAuthor().getId());
                destination.setAuthorName(source.getAuthor().getDisplayName());
                destination.setAuthorGravatar(GravatarHelper.hashOrDefault(source.getAuthor().getEmail()));
                break;
        }
    }
}
