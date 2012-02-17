package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.*;

@Entity
@Table(name = "CatalogGroup")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class CatalogGroup extends IntegerIdentityCUD {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentGroup")
    private CatalogGroup parentGroup;

    public CatalogGroup getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(CatalogGroup parentGroup) {
        this.parentGroup = parentGroup;
    }
}
