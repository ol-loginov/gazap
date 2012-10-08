package waypalm.domain.entity;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.DomainEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProfileAvatarRel")
@DynamicUpdate
public class ProfileAvatarRel implements DomainEntity {
    @SuppressWarnings("UnusedDeclaration")
    @EmbeddedId
    private ID id = new ID();

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile", nullable = false, updatable = false)
    @MapsId("profile")
    private Profile profile;
    @ManyToOne(optional = false)
    @JoinColumn(name = "avatar", nullable = false, updatable = false)
    @MapsId("avatar")
    private Avatar avatar;
    @Column(name = "author")
    private boolean author;
    @Column(name = "favourite")
    private boolean favourite;

    protected ProfileAvatarRel() {
    }

    public ProfileAvatarRel(Profile profile, Avatar avatar) {
        setProfile(profile);
        setAvatar(avatar);
    }

    public Profile getProfile() {
        return profile;
    }

    protected void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    protected void setAvatar(Avatar avatar) {
        this.avatar = avatar;
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
        private Integer avatar;
        private Integer profile;

        protected ID() {
        }

        public ID(Integer avatar, Integer profile) {
            this.avatar = avatar;
            this.profile = profile;
        }

        public Integer getAvatar() {
            return avatar;
        }

        public void setAvatar(Integer avatar) {
            this.avatar = avatar;
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
                    && avatar != null && avatar.equals(pk.avatar)
                    && profile != null && profile.equals(pk.profile);
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(avatar)
                    .append(profile)
                    .toHashCode();
        }
    }
}
