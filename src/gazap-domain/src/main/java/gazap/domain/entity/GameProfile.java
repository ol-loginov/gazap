package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GameProfile")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class GameProfile extends IntegerIdentityCUD {
}
