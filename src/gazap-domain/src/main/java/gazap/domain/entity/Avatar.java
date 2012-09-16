package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCUD;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Avatar")
@DynamicUpdate
public class Avatar extends IntegerIdentityCUD {
    @ManyToOne
    @JoinColumn(name = "world", nullable = true, updatable = false)
    private World world;
    @ManyToOne
    @JoinColumn(name = "owner", nullable = true, updatable = false)
    private UserProfile owner;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public UserProfile getOwner() {
        return owner;
    }

    public void setOwner(UserProfile owner) {
        this.owner = owner;
    }
}
