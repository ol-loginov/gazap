package gazap.site.services;

import gazap.domain.entity.UserProfile;
import gazap.site.model.viewer.UserDetails;

public interface ModelViewer {
    UserDetails userDetails(UserProfile profile);
}
