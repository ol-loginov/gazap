package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;
import waypalm.domain.entity.base.DomainEntity;
import waypalm.domain.entity.base.DomainHashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserSummary")
@DynamicUpdate
public class UserSummary implements DomainEntity {
    @Id
    private PK id;
    @Column(name = "worldFavourite")
    private int worldFavourite;
    @Column(name = "worldOwned")
    private int worldOwned;
    @Column(name = "worldOwnedLimit")
    private int worldOwnedLimit;
    @Column(name = "avatarFavourite")
    private int avatarFavourite;
    @Column(name = "avatarOwned")
    private int avatarOwned;

    protected UserSummary() {
    }

    public UserSummary(UserProfile profile) {
        setId(new PK(profile));
    }

    public PK getId() {
        return id;
    }

    protected void setId(PK id) {
        this.id = id;
    }

    public int getWorldFavourite() {
        return worldFavourite;
    }

    public void setWorldFavourite(int worldFavourite) {
        this.worldFavourite = worldFavourite;
    }

    public int getAvatarFavourite() {
        return avatarFavourite;
    }

    public void setAvatarFavourite(int avatarFavourite) {
        this.avatarFavourite = avatarFavourite;
    }

    public int getWorldOwned() {
        return worldOwned;
    }

    public void setWorldOwned(int worldOwned) {
        this.worldOwned = worldOwned;
    }

    public int getWorldOwnedLimit() {
        return worldOwnedLimit;
    }

    public void setWorldOwnedLimit(int worldOwnedLimit) {
        this.worldOwnedLimit = worldOwnedLimit;
    }

    public int getAvatarOwned() {
        return avatarOwned;
    }

    public void setAvatarOwned(int avatarOwned) {
        this.avatarOwned = avatarOwned;
    }

    @Embeddable
    public static class PK implements Serializable {
        @OneToOne(optional = false)
        @JoinColumn(name = "user", nullable = false, updatable = false)
        private UserProfile user;

        protected PK() {
        }

        public PK(UserProfile user) {
            Assert.notNull(user);
            this.user = user;
        }

        public UserProfile getUser() {
            return user;
        }

        protected void setUser(UserProfile user) {
            this.user = user;
        }

        @Override
        public boolean equals(Object instance) {
            if (instance == null)
                return false;
            if (!(instance instanceof PK))
                return false;

            final PK other = (PK) instance;
            return user != null && user.isSame(other.user);
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(user)
                    .toHashCode();
        }
    }
}
