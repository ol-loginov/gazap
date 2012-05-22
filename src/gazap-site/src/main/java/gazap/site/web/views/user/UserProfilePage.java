package gazap.site.web.views.user;

import gazap.domain.entity.UserWorldRoles;
import gazap.site.model.viewer.MapTitle;
import gazap.site.model.viewer.UserTitle;
import gazap.site.model.viewer.WorldTitle;

import java.util.*;

public class UserProfilePage {
    private UserTitle user;
    private List<WorldTitle> worlds = new ArrayList<WorldTitle>();
    private Map<Integer, Set<UserWorldRoles>> worldRoles = new TreeMap<Integer, Set<UserWorldRoles>>();
    private List<MapTitle> maps = new ArrayList<MapTitle>();

    public UserTitle getUser() {
        return user;
    }

    public void setUser(UserTitle user) {
        this.user = user;
    }

    public List<WorldTitle> getWorlds() {
        return worlds;
    }

    public void setWorlds(List<WorldTitle> worlds) {
        this.worlds = worlds;
    }

    public Map<Integer, Set<UserWorldRoles>> getWorldRoles() {
        return worldRoles;
    }

    public Set<UserWorldRoles> getWorldRolesList(int world) {
        Set<UserWorldRoles> list = worldRoles.get(world);
        if (list == null) {
            worldRoles.put(world, list = new TreeSet<UserWorldRoles>());
        }
        return list;
    }

    public List<MapTitle> getMaps() {
        return maps;
    }

    public void setMaps(List<MapTitle> maps) {
        this.maps = maps;
    }
}
