package waypalm.domain.entity;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.DomainEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProfileSurfaceRel")
@DynamicUpdate
public class ProfileSurfaceRel implements DomainEntity {
    @SuppressWarnings("UnusedDeclaration")
    @EmbeddedId
    private ID id = new ID();

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile", nullable = false, updatable = false)
    @MapsId("profile")
    private Profile profile;
    @ManyToOne(optional = false)
    @JoinColumn(name = "surface", nullable = false, updatable = false)
    @MapsId("surface")
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

    @Embeddable
    public static class ID implements Serializable {
        private Integer surface;
        private Integer profile;

        protected ID() {
        }

        public ID(Profile profile, Surface surface) {
            this.surface = surface.getId();
            this.profile = profile.getId();
        }

        public Integer getSurface() {
            return surface;
        }

        public void setSurface(Integer surface) {
            this.surface = surface;
        }

        public Integer getProfile() {
            return profile;
        }

        public void setProfile(Integer profile) {
            this.profile = profile;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ID && equals((ID) obj);
        }

        public boolean equals(ID pk) {
            return pk != null
                    && surface != null && surface.equals(pk.surface)
                    && profile != null && profile.equals(pk.profile);
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(surface)
                    .append(profile)
                    .toHashCode();
        }
    }
}
