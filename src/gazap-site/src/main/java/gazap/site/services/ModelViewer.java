package gazap.site.services;

import gazap.domain.entity.Contribution;
import gazap.domain.entity.Surface;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.World;
import gazap.site.model.viewer.ContributionV;
import gazap.site.model.viewer.SurfaceTitle;
import gazap.site.model.viewer.UserTitle;
import gazap.site.model.viewer.WorldTitle;

public interface ModelViewer {
    UserTitle userTitle(UserProfile profile);

    WorldTitle worldTitle(World world);

    SurfaceTitle surfaceTitle(Surface surface);

    ContributionV mapContribution(Contribution c, ModelViewerSet... viewSet);
}
