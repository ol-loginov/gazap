package gazap.domain.entity;

import gazap.domain.entity.base.DomainEntity;
import gazap.domain.entity.base.DomainHashCodeBuilder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MapApprover")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class MapApprover implements DomainEntity {
    @EmbeddedId
    private PK id;

    protected MapApprover() {
    }

    public MapApprover(Map map, UserProfile profile, int level) {
        setId(new PK(map, profile, level));
    }

    public PK getId() {
        return id;
    }

    protected void setId(PK id) {
        this.id = id;
    }

    public int getLevel() {
        return getId().getLevel();
    }

    public Map getMap() {
        return getId().getMap();
    }

    public UserProfile getUser() {
        return getId().getUser();
    }

    @Embeddable
    public static class PK implements Serializable {
        @ManyToOne(optional = false)
        @JoinColumn(name = "map", nullable = false, updatable = false)
        private Map map;
        @ManyToOne(optional = false)
        @JoinColumn(name = "user", nullable = false, updatable = false)
        private UserProfile user;
        @Column(name = "level")
        private int level;

        public PK() {
        }

        public PK(Map map, UserProfile user, int level) {
            Assert.notNull(user);
            Assert.notNull(map);
            this.user = user;
            this.map = map;
            this.level = level;
        }

        public UserProfile getUser() {
            return user;
        }

        public void setUser(UserProfile user) {
            this.user = user;
        }

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        @Override
        public boolean equals(Object instance) {
            if (instance == null)
                return false;
            if (!(instance instanceof PK))
                return false;

            final PK other = (PK) instance;
            return getUser() != null && getUser().isSame(other.getUser())
                    && getMap() != null && getMap().isSame(other.getMap())
                    && getLevel() == other.getLevel();
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(getUser())
                    .append(getMap())
                    .append(getLevel())
                    .toHashCode();
        }
    }
}
