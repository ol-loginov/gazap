package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.DomainEntity;
import waypalm.domain.entity.base.DomainHashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProfileSurfaceRel")
@DynamicUpdate
public class ProfileSurfaceRel implements DomainEntity {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "profile", nullable = false, updatable = false)
    private Profile profile;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "surface", nullable = false, updatable = false)
    private Surface surface;
    @Column(name = "author")
    private boolean author;
    @Column(name = "editor")
    private boolean editor;
    @Column(name = "favourite")
    private boolean favourite;

    protected ProfileSurfaceRel() {
    }

    public ProfileSurfaceRel(Profile profile, Surface surface) {
        setSurface(surface);
        setProfile(profile);
    }

    public Profile getProfile() {
        return profile;
    }

    protected void setProfile(Profile profile) {
        this.profile = profile;
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

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public static class PK implements Serializable {
        private final Surface surface;
        private final Profile profile;

        public PK(Surface surface, Profile profile) {
            this.surface = surface;
            this.profile = profile;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof PK && equals((PK) obj);
        }

        public boolean equals(PK pk) {
            return pk != null
                    && surface != null && surface.isSame(pk.surface)
                    && profile != null && profile.isSame(pk.profile);
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(surface)
                    .append(profile)
                    .toHashCode();
        }
    }
}
