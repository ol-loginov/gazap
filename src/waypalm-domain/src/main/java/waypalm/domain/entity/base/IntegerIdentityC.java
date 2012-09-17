package waypalm.domain.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class IntegerIdentityC extends IntegerIdentity {
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt = new Date();

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
