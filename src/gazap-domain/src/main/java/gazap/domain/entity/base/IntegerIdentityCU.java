package gazap.domain.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@MappedSuperclass
@XmlAccessorType(XmlAccessType.NONE)
public abstract class IntegerIdentityCU extends IntegerIdentity implements DomainEntityCU {
    @Column(name = "createdAt", nullable = false, updatable = false)
    @XmlElement
    private Date createdAt = new Date();
    @Column(name = "updatedAt", nullable = false)
    @XmlElement
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
