package gazap.site.model.viewer;

import gazap.domain.entity.UserMapRoles;

public class MapRole {
    private int user;
    private int map;
    private UserMapRoles role;

    public MapRole() {
    }

    public MapRole(int user, int map, UserMapRoles role) {
        this.user = user;
        this.map = map;
        this.role = role;
    }

    public int getMap() {
        return map;
    }

    public UserMapRoles getRole() {
        return role;
    }

    public int getUser() {
        return user;
    }
}
