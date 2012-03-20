package gazap.site.web.views.user;

import gazap.domain.entity.UserGameRoles;
import gazap.domain.entity.UserMapRoles;
import gazap.site.model.viewer.GameTitle;
import gazap.site.model.viewer.MapTitle;
import gazap.site.model.viewer.UserTitle;

import java.util.*;

public class UserProfilePage {
    private UserTitle user;
    private List<GameTitle> games = new ArrayList<GameTitle>();
    private Map<Integer, Set<UserGameRoles>> gameRoles = new TreeMap<Integer, Set<UserGameRoles>>();
    private List<MapTitle> maps = new ArrayList<MapTitle>();
    private Map<Integer, Set<UserMapRoles>> mapRoles = new TreeMap<Integer, Set<UserMapRoles>>();

    public UserTitle getUser() {
        return user;
    }

    public void setUser(UserTitle user) {
        this.user = user;
    }

    public List<GameTitle> getGames() {
        return games;
    }

    public void setGames(List<GameTitle> games) {
        this.games = games;
    }

    public Map<Integer, Set<UserGameRoles>> getGameRoles() {
        return gameRoles;
    }

    public Set<UserGameRoles> getGameRolesList(int game) {
        Set<UserGameRoles> list = gameRoles.get(game);
        if (list == null) {
            gameRoles.put(game, list = new TreeSet<UserGameRoles>());
        }
        return list;
    }

    public List<MapTitle> getMaps() {
        return maps;
    }

    public void setMaps(List<MapTitle> maps) {
        this.maps = maps;
    }

    public Map<Integer, Set<UserMapRoles>> getMapRoles() {
        return mapRoles;
    }

    public Set<UserMapRoles> getMapRolesList(int map) {
        Set<UserMapRoles> list = mapRoles.get(map);
        if (list == null) {
            mapRoles.put(map, list = new TreeSet<UserMapRoles>());
        }
        return list;
    }

}
