package gazap.site.model.viewer;

import gazap.domain.entity.UserWorldRoles;

public class WorldRole {
    private int user;
    private int world;
    private UserWorldRoles role;

    public WorldRole() {
    }

    public WorldRole(int user, int world, UserWorldRoles role) {
        this.user = user;
        this.world = world;
        this.role = role;
    }

    public int getWorld() {
        return world;
    }

    public UserWorldRoles getRole() {
        return role;
    }

    public int getUser() {
        return user;
    }
}
