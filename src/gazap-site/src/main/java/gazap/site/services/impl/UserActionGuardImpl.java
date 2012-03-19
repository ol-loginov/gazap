package gazap.site.services.impl;

import gazap.domain.entity.UserProfile;
import gazap.site.services.UserActionGuard;

public class UserActionGuardImpl implements UserActionGuard {
    UserProfile actor;

    public UserActionGuardImpl(UserProfile actor) {
        this.actor = actor;
    }

    @Override
    public boolean createMap() {
        return actor != null;
    }

    @Override
    public boolean editMap(int mapId) {
        return false;
    }
}
