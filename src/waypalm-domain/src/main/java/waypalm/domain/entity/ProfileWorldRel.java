package waypalm.domain.entity;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.DomainEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProfileWorldRel")
@DynamicUpdate
public class ProfileWorldRel implements DomainEntity {
    @SuppressWarnings("UnusedDeclaration")
    @EmbeddedId
    private ID id = new ID();

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile", nullable = false, updatable = false)
    @MapsId("profile")
    private Profile profile;
    @ManyToOne(optional = false)
    @JoinColumn(name = "world", nullable = false, updatable = false)
    @MapsId("world")
    private World world;
    @Column(name = "author")
    private boolean author;
    @Column(name = "editor")
    private boolean editor;
    @Column(name = "avatar")
    private boolean avatar;
    @Column(name = "favourite")
    private boolean favourite;

    protected ProfileWorldRel() {
    }

    public ProfileWorldRel(Profile profile, World world) {
        setWorld(world);
        setProfile(profile);
    }

    public Profile getProfile() {
        return profile;
    }

    protected void setProfile(Profile profile) {
        this.profile = profile;
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

    public boolean isAvatar() {
        return avatar;
    }

    public void setAvatar(boolean avatar) {
        this.avatar = avatar;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Embeddable
    public static class ID implements Serializable {
        private Integer world;
        private Integer profile;

        protected ID() {
        }

        public ID(Profile profile, World world) {
            this.world = world.getId();
            this.profile = profile.getId();
        }

        public Integer getWorld() {
            return world;
        }

        public void setWorld(Integer world) {
            this.world = world;
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
                    && world != null && world.equals(pk.world)
                    && profile != null && profile.equals(pk.profile);
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(world)
                    .append(profile)
                    .toHashCode();
        }
    }
}
