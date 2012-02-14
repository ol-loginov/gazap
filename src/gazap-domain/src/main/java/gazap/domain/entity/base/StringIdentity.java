package gazap.domain.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@MappedSuperclass
@XmlAccessorType(XmlAccessType.NONE)
public class StringIdentity {
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = false)
    @XmlElement
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
