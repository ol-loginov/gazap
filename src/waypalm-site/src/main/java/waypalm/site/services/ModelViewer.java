package waypalm.site.services;

import waypalm.domain.entity.Profile;
import waypalm.site.model.view.UserName;

public interface ModelViewer {
    UserName createUserName(Profile profile);
}
