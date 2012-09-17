package waypalm.site.web.views.user;

import waypalm.site.model.viewer.SurfaceTitle;
import waypalm.site.model.viewer.UserTitle;
import waypalm.site.model.viewer.WorldTitle;

import java.util.ArrayList;
import java.util.List;

public class UserProfilePage {
    private UserTitle user;
    private List<WorldTitle> worlds = new ArrayList<>();
    private List<SurfaceTitle> surfaces = new ArrayList<>();

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

    public List<SurfaceTitle> getSurfaces() {
        return surfaces;
    }

    public void setSurfaces(List<SurfaceTitle> surfaces) {
        this.surfaces = surfaces;
    }
}
