package gazap.domain.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@MappedSuperclass
@XmlAccessorType(XmlAccessType.NONE)
public abstract class IntegerIdentityCUD extends IntegerIdentityCU {
    @Column(name = "deletedAt")
    @XmlElement
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
