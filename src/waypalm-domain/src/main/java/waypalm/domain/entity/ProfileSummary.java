package waypalm.domain.entity;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.DomainEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProfileSummary")
@DynamicUpdate
public class ProfileSummary implements DomainEntity {
    @SuppressWarnings("UnusedDeclaration")
    @EmbeddedId
    private ID id = new ID();

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile", nullable = false, updatable = false)
    @MapsId("profile")
    private Profile profile;
    @Column(name = "worldFavourite")
    private int worldFavourite;
    @Column(name = "worldOwned")
    private int worldOwned;
    @Column(name = "worldOwnedLimit")
    private int worldOwnedLimit;
    @Column(name = "avatarFavourite")
    private int avatarFavourite;
    @Column(name = "avatarOwned")
    private int avatarOwned;

    protected ProfileSummary() {
    }

    public ProfileSummary(Profile profile) {
        setProfile(profile);
    }

    public Profile getProfile() {
        return profile;
    }

    protected void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getWorldFavourite() {
        return worldFavourite;
    }

    public void setWorldFavourite(int worldFavourite) {
        this.worldFavourite = worldFavourite;
    }

    public int getAvatarFavourite() {
        return avatarFavourite;
    }

    public void setAvatarFavourite(int avatarFavourite) {
        this.avatarFavourite = avatarFavourite;
    }

    public int getWorldOwned() {
        return worldOwned;
    }

    public void setWorldOwned(int worldOwned) {
        this.worldOwned = worldOwned;
    }

    public int getWorldOwnedLimit() {
        return worldOwnedLimit;
    }

    public void setWorldOwnedLimit(int worldOwnedLimit) {
        this.worldOwnedLimit = worldOwnedLimit;
    }

    public int getAvatarOwned() {
        return avatarOwned;
    }

    public void setAvatarOwned(int avatarOwned) {
        this.avatarOwned = avatarOwned;
    }

    @Embeddable
    public static class ID implements Serializable {
        private Integer profile;

        protected ID() {
        }

        public ID(Profile profile) {
            this.profile = profile.getId();
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
            return pk != null && profile != null && profile.equals(pk.profile);
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(profile)
                    .toHashCode();
        }
    }
}
