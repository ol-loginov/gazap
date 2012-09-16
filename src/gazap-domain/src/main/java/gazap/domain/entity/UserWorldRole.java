package gazap.domain.entity;

import gazap.domain.entity.base.DomainEntity;
import gazap.domain.entity.base.DomainHashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserWorldRole")
@DynamicUpdate
@IdClass(UserWorldRole.PK.class)
public class UserWorldRole implements DomainEntity {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private UserProfile user;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "world", nullable = false, updatable = false)
    private World world;
    @Id
    @Column(name = "userWorldRole", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private UserWorldRoles role;

    protected UserWorldRole() {
    }

    public UserWorldRole(UserProfile profile, World world, UserWorldRoles role) {
        setUser(profile);
        setWorld(world);
        setRole(role);
    }

    public UserProfile getUser() {
        return user;
    }

    protected void setUser(UserProfile user) {
        this.user = user;
    }

    public World getWorld() {
        return world;
    }

    protected void setWorld(World world) {
        this.world = world;
    }

    public UserWorldRoles getRole() {
        return role;
    }

    protected void setRole(UserWorldRoles role) {
        this.role = role;
    }

    public static class PK implements Serializable {
        private UserProfile user;
        private World world;
        private UserWorldRoles role;

        protected PK() {
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

        protected void setUser(UserProfile user) {
            this.user = user;
        }

        public World getWorld() {
            return world;
        }

        protected void setWorld(World world) {
            this.world = world;
        }

        public UserWorldRoles getRole() {
            return role;
        }

        protected void setRole(UserWorldRoles role) {
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
