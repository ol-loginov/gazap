package gazap.domain.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

@MappedSuperclass
public abstract class IntegerIdentityCUD extends IntegerIdentityCU {
    @Column(name = "deletedAt")
    private Date deletedAt;

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Transient
    public boolean isDeleted() {
        return deletedAt != null;
    }
}
