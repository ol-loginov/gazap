package gazap.domain.entity;

import gazap.domain.entity.base.DomainEntity;
import gazap.domain.entity.base.DomainHashCodeBuilder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserSummary")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserSummary implements DomainEntity {
    @EmbeddedId
    private PK id;
    @Column(name = "worldsCreated")
    private int worldsCreated;
    @Column(name = "playersCreated")
    private int playersCreated;
    @Column(name = "mapsCreated")
    private int mapsCreated;

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

    @Embeddable
    public static class PK implements Serializable {
        public static final String F_USER = "user";

        @ManyToOne(optional = false)
        @JoinColumn(name = F_USER, nullable = false)
        private UserProfile user;

        public PK() {
        }

        public PK(UserProfile user) {
            Assert.notNull(user);
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
