package waypalm.domain.dao;

import waypalm.domain.entity.base.DomainEntity;

public interface Dao {
    void delete(DomainEntity entity);

    void create(DomainEntity entity);

    void save(DomainEntity entity);
}
