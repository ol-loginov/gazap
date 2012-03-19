package gazap.site.web.views.user;

import gazap.domain.entity.UserMapRoles;
import gazap.site.model.viewer.GameRole;
import gazap.site.model.viewer.GameTitle;
import gazap.site.model.viewer.MapTitle;
import gazap.site.model.viewer.UserTitle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserProfilePage {
    private UserTitle user;
    private List<GameTitle> games = new ArrayList<GameTitle>();
    private List<GameRole> gameRoles = new ArrayList<GameRole>();
    private List<MapTitle> maps = new ArrayList<MapTitle>();
    private Map<Integer, List<UserMapRoles>> visitorMapRoles = new TreeMap<Integer, List<UserMapRoles>>();

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

    public List<GameRole> getGameRoles() {
        return gameRoles;
    }

    public void setGameRoles(List<GameRole> gameRoles) {
        this.gameRoles = gameRoles;
    }

    public List<MapTitle> getMaps() {
        return maps;
    }

    public void setMaps(List<MapTitle> maps) {
        this.maps = maps;
    }

    public Map<Integer, List<UserMapRoles>> getVisitorMapRoles() {
        return visitorMapRoles;
    }

}
