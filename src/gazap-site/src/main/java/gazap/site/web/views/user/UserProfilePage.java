package gazap.site.web.views.user;

import gazap.site.model.viewer.*;
import gazap.site.web.views.GazapPage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "UserProfilePage")
@XmlAccessorType(XmlAccessType.NONE)
public class UserProfilePage extends GazapPage {
    private UserTitle user;
    private List<GameTitle> games = new ArrayList<GameTitle>();
    private List<GameRole> gameRoles = new ArrayList<GameRole>();
    private List<MapTitle> maps = new ArrayList<MapTitle>();
    private List<MapRole> mapRoles = new ArrayList<MapRole>();


    @XmlElement
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
