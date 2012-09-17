package gazap.site.services.impl;

import gazap.common.util.GravatarHelper;
import gazap.domain.dao.UserRepository;
import gazap.domain.dao.WorldRepository;
import gazap.domain.entity.*;
import gazap.site.model.viewer.ContributionV;
import gazap.site.model.viewer.SurfaceTitle;
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
    protected UserRepository userRepository;
    @Autowired
    protected WorldRepository worldRepository;
    @Autowired
    protected UserAccess auth;

    @Override
    public UserTitle userTitle(UserProfile profile) {
        UserTitle title = new UserTitle();
        title.setId(profile.getId());
        title.setAlias(profile.getAlias());
        title.setName(profile.getDisplayName());
        title.setGravatar(GravatarHelper.hashOrDefault(profile.getEmail()));
        title.setRoute("/u/" + (profile.getAlias() == null ? Integer.toString(profile.getId()) : profile.getAlias()));
        title.setSummary(userRepository.loadSummary(profile));
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
    public SurfaceTitle surfaceTitle(Surface surface) {
        SurfaceTitle title = new SurfaceTitle();
        title.setId(surface.getId());
        title.setTitle(surface.getTitle());
        title.setAlias(surface.getAlias());
        title.setRoute(String.format("/w/%s/%s", surface.getWorld().getAlias(), surface.getAlias() == null ? Integer.toString(surface.getId()) : surface.getAlias()));
        return title;
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
