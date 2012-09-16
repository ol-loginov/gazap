package gazap.domain.entity;

import gazap.domain.entity.base.DomainEntity;
import gazap.domain.entity.base.DomainHashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MapApprover")
@DynamicUpdate
@IdClass(MapApprover.PK.class)
public class MapApprover implements DomainEntity {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "map", nullable = false, updatable = false)
    private Map map;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private UserProfile user;
    @Id
    @Column(name = "level")
    private int level;

    protected MapApprover() {
    }

    public MapApprover(Map map, UserProfile profile, int level) {
        setMap(map);
        setUser(profile);
        setLevel(level);
    }

    public Map getMap() {
        return map;
    }

    protected void setMap(Map map) {
        this.map = map;
    }

    public UserProfile getUser() {
        return user;
    }

    protected void setUser(UserProfile user) {
        this.user = user;
    }

    public int getLevel() {
        return level;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    public static class PK implements Serializable {
        private Map map;
        private UserProfile user;
        private int level;

        protected PK() {
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

        protected void setUser(UserProfile user) {
            this.user = user;
        }

        public Map getMap() {
            return map;
        }

        protected void setMap(Map map) {
            this.map = map;
        }

        public int getLevel() {
            return level;
        }

        protected void setLevel(int level) {
            this.level = level;
        }

        @Override
        public boolean equals(Object instance) {
            if (instance == null)
                return false;
            if (!(instance instanceof PK))
                return false;

            final PK other = (PK) instance;
            return user != null && user.isSame(other.user)
                    && map != null && map.isSame(other.map)
                    && level == other.level;
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(user)
                    .append(map)
                    .append(level)
                    .toHashCode();
        }
    }
}
