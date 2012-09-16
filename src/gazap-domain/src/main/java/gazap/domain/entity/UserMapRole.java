package gazap.domain.entity;

import gazap.domain.entity.base.DomainEntity;
import gazap.domain.entity.base.DomainHashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserMapRole")
@DynamicUpdate
@IdClass(UserMapRole.PK.class)
public class UserMapRole implements DomainEntity {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private UserProfile user;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "map", nullable = false, updatable = false)
    private Map map;
    @Id
    @Column(name = "userMapRole", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private UserMapRoles role;

    protected UserMapRole() {
    }

    public UserMapRole(UserProfile profile, Map map, UserMapRoles role) {
        setUser(profile);
        setMap(map);
        setRole(role);
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

    public UserMapRoles getRole() {
        return role;
    }

    protected void setRole(UserMapRoles role) {
        this.role = role;
    }

    public static class PK implements Serializable {
        private UserProfile user;
        private Map map;
        private UserMapRoles role;

        protected PK() {
        }

        public PK(UserProfile user, Map map, UserMapRoles role) {
            Assert.notNull(user);
            Assert.notNull(map);
            Assert.notNull(role);
            this.user = user;
            this.map = map;
            this.role = role;
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

        public UserMapRoles getRole() {
            return role;
        }

        protected void setRole(UserMapRoles role) {
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
                    && map != null && map.isSame(other.map)
                    && role != null && role.equals(other.role);
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(user)
                    .append(map)
                    .append(role)
                    .toHashCode();
        }
    }
}
