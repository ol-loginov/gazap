package gazap.domain.entity;

import gazap.domain.entity.base.DomainEntity;
import gazap.domain.entity.base.DomainHashCodeBuilder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserWorldRole")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserWorldRole implements DomainEntity {
    @EmbeddedId
    private PK id;

    protected UserWorldRole() {
    }

    public UserWorldRole(UserProfile profile, World world, UserWorldRoles role) {
        setId(new PK(profile, world, role));
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

    public World getWorld() {
        return getId().getWorld();
    }

    public UserWorldRoles getRole() {
        return getId().getRole();
    }

    @Embeddable
    public static class PK implements Serializable {
        @ManyToOne(optional = false)
        @JoinColumn(name = "user", nullable = false, updatable = false)
        private UserProfile user;
        @ManyToOne(optional = false)
        @JoinColumn(name = "world", nullable = false, updatable = false)
        private World world;
        @Column(name = "userWorldRole", nullable = false, updatable = false)
        @Enumerated(EnumType.STRING)
        private UserWorldRoles role;

        public PK() {
        }

        public PK(UserProfile user, World world, UserWorldRoles role) {
            Assert.notNull(user);
            Assert.notNull(world);
            Assert.notNull(role);
            this.user = user;
            this.world = world;
            this.role = role;
        }

        public UserProfile getUser() {
            return user;
        }

        public void setUser(UserProfile user) {
            this.user = user;
        }

        public World getWorld() {
            return world;
        }

        public void setWorld(World world) {
            this.world = world;
        }

        public UserWorldRoles getRole() {
            return role;
        }

        public void setRole(UserWorldRoles role) {
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
                    && world != null && world.isSame(other.world)
                    && role != null && role.equals(other.role);
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(user)
                    .append(world)
                    .append(role)
                    .toHashCode();
        }
    }
}
