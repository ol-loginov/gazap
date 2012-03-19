package gazap.domain.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class IntegerIdentityCU extends IntegerIdentity implements DomainEntityCU {
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt = new Date();
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt = new Date();

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
