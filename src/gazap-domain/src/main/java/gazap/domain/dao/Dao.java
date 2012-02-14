package gazap.domain.dao;

import gazap.domain.entity.base.DomainEntity;

public interface Dao {
    //TransactionStatus getTransaction();

    //void commit(TransactionStatus transaction);

    //void rollback(TransactionStatus transaction);

    //<T extends DomainEntity> T merge(T entity);

    void delete(DomainEntity entity);

    void create(DomainEntity entity);

    void save(DomainEntity entity);

    //void saveOrCreate(DomainEntity entity);
}
