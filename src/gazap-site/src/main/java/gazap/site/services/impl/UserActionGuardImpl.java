package gazap.site.services.impl;

import gazap.domain.dao.MapDao;
import gazap.domain.entity.UserMapRole;
import gazap.domain.entity.UserMapRoles;
import gazap.domain.entity.UserProfile;
import gazap.site.services.UserActionGuard;

import java.util.List;

public class UserActionGuardImpl implements UserActionGuard {
    private final UserProfile visitor;
    private final MapDao mapDao;

    public UserActionGuardImpl(UserProfile visitor, MapDao mapDao) {
        this.visitor = visitor;
        this.mapDao = mapDao;
    }

    @Override
    public boolean createMap() {
        return visitor != null;
    }

    @Override
    public boolean editMap(int mapId) {
        if (visitor == null) {
            return false;
        }

        List<UserMapRole> roles = mapDao.listMapRoleByUser(visitor, mapId);
        for (UserMapRole role : roles) {
            if (role.getRole() == UserMapRoles.CREATOR) {
                return true;
            }
        }
        return false;
    }
}
