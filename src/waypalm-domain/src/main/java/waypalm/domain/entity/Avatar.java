package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.*;

@Entity
@Table(name = "Avatar")
@DynamicUpdate
public class Avatar extends IntegerIdentityCUD {
    public static final int GAME_TITLE_LENGTH = 64;
    public static final int GAME_NAME_LENGTH = 64;
    @ManyToOne
    @JoinColumn(name = "world", nullable = true, updatable = false)
    private World world;
    @Column(name = "gameTitle", nullable = false, length = GAME_TITLE_LENGTH)
    private String gameTitle;
    @Column(name = "gameName", nullable = false, length = GAME_NAME_LENGTH)
    private String gameName;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
