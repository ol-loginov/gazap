package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;
import waypalm.domain.entity.base.DomainEntity;
import waypalm.domain.entity.base.DomainHashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProfileSummary")
@DynamicUpdate
public class ProfileSummary implements DomainEntity {
    @Id
    @OneToOne
    @JoinColumn(name = "profile", updatable = false)
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
}
