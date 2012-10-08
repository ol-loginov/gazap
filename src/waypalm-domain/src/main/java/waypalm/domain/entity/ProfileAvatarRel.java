package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.DomainEntity;

import javax.persistence.*;

@Entity
@Table(name = "ProfileAvatarRel")
@DynamicUpdate
public class ProfileAvatarRel implements DomainEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "profile", updatable = false, nullable = false)
    private Profile profile;
    @Id
    @ManyToOne
    @JoinColumn(name = "avatar", updatable = false, nullable = false)
    private Avatar avatar;
    @Column(name = "author")
    private boolean author;
    @Column(name = "favourite")
    private boolean favourite;

    protected ProfileAvatarRel() {
    }

    protected ProfileAvatarRel(Profile profile, Avatar avatar) {
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
}
