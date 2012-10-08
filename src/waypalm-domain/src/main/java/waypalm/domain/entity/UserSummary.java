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
    @Column(name = "worldCount")
    private int worldCount;
    @Column(name = "avatarCount")
    private int avatarCount;

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

    public int getWorldCount() {
        return worldCount;
    }

    public void setWorldCount(int worldCount) {
        this.worldCount = worldCount;
    }

    public int getAvatarCount() {
        return avatarCount;
    }

    public void setAvatarCount(int avatarCount) {
        this.avatarCount = avatarCount;
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
