package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Avatar")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Avatar extends IntegerIdentityCUD {
    @ManyToOne
    @JoinColumn(name = "game", nullable = true, updatable = false)
    private Game game;
    @ManyToOne
    @JoinColumn(name = "owner", nullable = true, updatable = false)
    private UserProfile owner;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public UserProfile getOwner() {
        return owner;
    }

    public void setOwner(UserProfile owner) {
        this.owner = owner;
    }
}
