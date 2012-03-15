package gazap.site.model.viewer;

import gazap.domain.entity.UserGameRoles;

public class GameRole {
    private int user;
    private int game;
    private UserGameRoles role;

    public GameRole() {
    }

    public GameRole(int user, int game, UserGameRoles role) {
        this.user = user;
        this.game = game;
        this.role = role;
    }

    public int getGame() {
        return game;
    }

    public UserGameRoles getRole() {
        return role;
    }

    public int getUser() {
        return user;
    }
}
