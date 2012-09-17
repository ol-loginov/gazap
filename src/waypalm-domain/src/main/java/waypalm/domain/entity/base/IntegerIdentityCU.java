package waypalm.domain.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class IntegerIdentityCU extends IntegerIdentityC implements DomainEntityCU {
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt = new Date();

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
