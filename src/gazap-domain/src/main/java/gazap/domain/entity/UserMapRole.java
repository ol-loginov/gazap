package gazap.domain.entity;

import gazap.domain.entity.base.DomainHashCodeBuilder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserMapRole")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserMapRole {
    @EmbeddedId
    private PK id;

    protected UserMapRole() {
    }

    public UserMapRole(UserProfile profile, Map map, UserMapRoles role) {
        setId(new PK(profile, map, role));
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

    public Map getMap() {
        return getId().getMap();
    }

    public UserMapRoles getRole() {
        return getId().getRole();
    }

    @Embeddable
    public static class PK implements Serializable {
        @ManyToOne(optional = false)
        @JoinColumn(name = "user", nullable = false, updatable = false)
        private UserProfile user;
        @ManyToOne(optional = false)
        @JoinColumn(name = "map", nullable = false, updatable = false)
        private Map map;
        @Column(name = "userMapRole", nullable = false, updatable = false)
        @Enumerated(EnumType.STRING)
        private UserMapRoles role;

        public PK() {
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

        public void setUser(UserProfile user) {
            this.user = user;
        }

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        public UserMapRoles getRole() {
            return role;
        }

        public void setRole(UserMapRoles role) {
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
