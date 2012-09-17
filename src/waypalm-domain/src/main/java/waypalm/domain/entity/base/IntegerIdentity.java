package waypalm.domain.entity.base;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@MappedSuperclass
@XmlAccessorType(XmlAccessType.NONE)
public abstract class IntegerIdentity implements DomainEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement
    private Integer id;

    public Integer getId() {
        return id;
    }

    @SuppressWarnings("UnusedDeclaration")
    protected void setId(Integer id) {
        this.id = id;
    }

    @SuppressWarnings("UnusedDeclaration")
    public boolean isSame(IntegerIdentity obj) {
        return obj != null
                && obj.getClass().equals(getClass())
                && getId() != null
                && getId().equals(obj.getId());
    }
}
