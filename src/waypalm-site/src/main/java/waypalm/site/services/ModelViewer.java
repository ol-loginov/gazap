package waypalm.site.services;

import waypalm.domain.entity.Profile;
import waypalm.site.model.viewer.UserTitle;

public interface ModelViewer {
    UserTitle profileTitle(Profile profile);
}
