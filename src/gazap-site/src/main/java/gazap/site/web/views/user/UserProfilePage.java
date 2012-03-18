package gazap.site.web.views.user;

import gazap.site.model.viewer.*;

import java.util.ArrayList;
import java.util.List;

public class UserProfilePage {
    private UserTitle user;
    private List<GameTitle> games = new ArrayList<GameTitle>();
    private List<GameRole> gameRoles = new ArrayList<GameRole>();
    private List<MapTitle> maps = new ArrayList<MapTitle>();
    private List<MapRole> mapRoles = new ArrayList<MapRole>();

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

    public List<MapRole> getMapRoles() {
        return mapRoles;
    }

    public void setMapRoles(List<MapRole> mapRoles) {
        this.mapRoles = mapRoles;
    }
}
