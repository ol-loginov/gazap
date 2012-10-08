package waypalm.site.services;

import waypalm.domain.entity.Contribution;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.World;
import waypalm.site.model.viewer.ContributionV;
import waypalm.site.model.viewer.SurfaceTitle;
import waypalm.site.model.viewer.UserTitle;
import waypalm.site.model.viewer.WorldTitle;

public interface ModelViewer {
    UserTitle profileTitle(Profile profile);

    WorldTitle worldTitle(World world);

    SurfaceTitle surfaceTitle(Surface surface);

    ContributionV mapContribution(Contribution c, ModelViewerSet... viewSet);
}
