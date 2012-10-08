package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.DomainEntity;
import waypalm.domain.entity.base.DomainHashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProfileWorldRel")
@DynamicUpdate
public class ProfileWorldRel implements DomainEntity {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "profile", nullable = false, updatable = false)
    private Profile profile;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "world", nullable = false, updatable = false)
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

    public ProfileWorldRel(Profile user, World world) {
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

    public static class PK implements Serializable {
        private final World world;
        private final Profile profile;

        public PK(World world, Profile profile) {
            this.world = world;
            this.profile = profile;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof PK && equals((PK) obj);
        }

        public boolean equals(PK pk) {
            return pk != null
                    && world != null && world.isSame(pk.world)
                    && profile != null && profile.isSame(pk.profile);
        }

        @Override
        public int hashCode() {
            return new DomainHashCodeBuilder()
                    .append(world)
                    .append(profile)
                    .toHashCode();
        }
    }
}
