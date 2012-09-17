package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;
import waypalm.domain.entity.base.DomainEntity;
import waypalm.domain.entity.base.DomainHashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "WorldActor")
@DynamicUpdate
@IdClass(WorldActor.PK.class)
public class WorldActor implements DomainEntity {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private UserProfile user;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "world", nullable = false, updatable = false)
    private World world;
    @Column
    private boolean author;
    @Column
    private boolean editor;

    protected WorldActor() {
    }

    public WorldActor(World world, UserProfile user) {
        this.world = world;
        this.user = user;
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

    public boolean isEditor() {
        return editor;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    public boolean isAuthor() {
        return author;
    }

    public void setAuthor(boolean author) {
        this.author = author;
    }

    public static class PK implements Serializable {
        private UserProfile user;
        private World world;

        protected PK() {
        }

        public PK(UserProfile user, World world) {
            Assert.notNull(user);
            Assert.notNull(world);
            this.user = user;
            this.world = world;
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

        @Override
        public boolean equals(Object instance) {
            if (instance == null)
                return false;
            if (!(instance instanceof PK))
                return false;

            final PK other = (PK) instance;
            return user != null && user.isSame(other.user)
                    && world != null && world.isSame(other.world);
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(user)
                    .append(world)
                    .toHashCode();
        }
    }
}
