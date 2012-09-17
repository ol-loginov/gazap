package waypalm.site.web.extensions;

import waypalm.site.model.viewer.UserTitle;

public class VisitorExtension {
    private boolean debug;
    private UserTitle user;

    public UserTitle getUser() {
        return user;
    }

    public void setUser(UserTitle user) {
        this.user = user;
    }

    public boolean isLogged() {
        return user != null;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getUserId() {
        return user != null ? user.getId() : 0;
    }
}
