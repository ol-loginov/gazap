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
@IdClass(UserSummary.PK.class)
public class UserSummary implements DomainEntity, Serializable {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private UserProfile user;
    @Column(name = "worldsCreated")
    private int worldsCreated;
    @Column(name = "playersCreated")
    private int playersCreated;
    @Column(name = "mapsCreated")
    private int mapsCreated;

    protected UserSummary() {
    }

    public UserSummary(UserProfile profile) {
        setUser(profile);
    }

    public UserProfile getUser() {
        return user;
    }

    protected void setUser(UserProfile user) {
        this.user = user;
    }

    public int getWorldsCreated() {
        return worldsCreated;
    }

    public void setWorldsCreated(int worldsCreated) {
        this.worldsCreated = worldsCreated;
    }

    public int getPlayersCreated() {
        return playersCreated;
    }

    public void setPlayersCreated(int playersCreated) {
        this.playersCreated = playersCreated;
    }

    public int getMapsCreated() {
        return mapsCreated;
    }

    public void setMapsCreated(int mapsCreated) {
        this.mapsCreated = mapsCreated;
    }

    public static class PK implements Serializable {
        private UserProfile user;

        public PK() {
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
