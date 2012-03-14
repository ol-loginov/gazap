package gazap.domain.entity;

import gazap.domain.entity.base.DomainEntity;
import gazap.domain.entity.base.DomainHashCodeBuilder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserGameRole")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserGameRole implements DomainEntity {
    @EmbeddedId
    private PK id;

    protected UserGameRole() {
    }

    public UserGameRole(UserProfile profile, GameProfile game, UserGameRoles role) {
        setId(new PK(profile, game, role));
    }

    public PK getId() {
        return id;
    }

    protected void setId(PK id) {
        this.id = id;
    }

    public UserProfile getUser() {
        return getId().getUser();
    }

    public GameProfile getGame() {
        return getId().getGame();
    }

    public UserGameRoles getRole() {
        return getId().getRole();
    }

    @Embeddable
    public static class PK implements Serializable {
        @ManyToOne(optional = false)
        @JoinColumn(name = "user", nullable = false, updatable = false)
        private UserProfile user;
        @ManyToOne(optional = false)
        @JoinColumn(name = "game", nullable = false, updatable = false)
        private GameProfile game;
        @Column(name = "userGameRole", nullable = false, updatable = false)
        @Enumerated(EnumType.STRING)
        private UserGameRoles role;

        public PK() {
        }

        public PK(UserProfile user, GameProfile game, UserGameRoles role) {
            Assert.notNull(user);
            Assert.notNull(game);
            Assert.notNull(role);
            this.user = user;
            this.game = game;
            this.role = role;
        }

        public UserProfile getUser() {
            return user;
        }

        public void setUser(UserProfile user) {
            this.user = user;
        }

        public GameProfile getGame() {
            return game;
        }

        public void setGame(GameProfile game) {
            this.game = game;
        }

        public UserGameRoles getRole() {
            return role;
        }

        public void setRole(UserGameRoles role) {
            this.role = role;
        }

        @Override
        public boolean equals(Object instance) {
            if (instance == null)
                return false;
            if (!(instance instanceof PK))
                return false;

            final PK other = (PK) instance;
            return user != null && user.isSame(other.user)
                    && game != null && game.isSame(other.game)
                    && role != null && role.equals(other.role);
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(user)
                    .append(game)
                    .append(role)
                    .toHashCode();
        }
    }
}
