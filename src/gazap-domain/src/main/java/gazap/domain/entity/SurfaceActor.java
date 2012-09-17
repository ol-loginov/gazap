package gazap.domain.entity;

import gazap.domain.entity.base.DomainEntity;
import gazap.domain.entity.base.DomainHashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SurfaceActor")
@DynamicUpdate
@IdClass(SurfaceActor.PK.class)
public class SurfaceActor implements DomainEntity {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private UserProfile user;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "surface", nullable = false, updatable = false)
    private Surface surface;
    @Column
    private boolean author;
    @Column
    private boolean editor;

    protected SurfaceActor() {
    }

    public SurfaceActor(Surface surface, UserProfile user) {
        this.surface = surface;
        this.user = user;
    }

    public UserProfile getUser() {
        return user;
    }

    protected void setUser(UserProfile user) {
        this.user = user;
    }

    public Surface getSurface() {
        return surface;
    }

    protected void setSurface(Surface surface) {
        this.surface = surface;
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
        private Surface surface;

        protected PK() {
        }

        public PK(UserProfile user, Surface surface) {
            Assert.notNull(user);
            Assert.notNull(surface);
            this.user = user;
            this.surface = surface;
        }

        public UserProfile getUser() {
            return user;
        }

        protected void setUser(UserProfile user) {
            this.user = user;
        }

        public Surface getSurface() {
            return surface;
        }

        protected void setSurface(Surface surface) {
            this.surface = surface;
        }

        @Override
        public boolean equals(Object instance) {
            if (instance == null)
                return false;
            if (!(instance instanceof PK))
                return false;

            final PK other = (PK) instance;
            return user != null && user.isSame(other.user)
                    && surface != null && surface.isSame(other.surface);
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(user)
                    .append(surface)
                    .toHashCode();
        }
    }
}
