package gazap.site.services;

import gazap.domain.entity.Contribution;
import gazap.domain.entity.World;
import gazap.domain.entity.Map;
import gazap.domain.entity.UserProfile;
import gazap.site.model.viewer.ContributionV;
import gazap.site.model.viewer.WorldTitle;
import gazap.site.model.viewer.MapTitle;
import gazap.site.model.viewer.UserTitle;

public interface ModelViewer {
    UserTitle userTitle(UserProfile profile);

    WorldTitle worldTitle(World world);

    MapTitle mapTitle(Map map);

    ContributionV mapContribution(Contribution c);
}
