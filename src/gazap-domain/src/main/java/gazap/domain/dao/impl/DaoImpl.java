package gazap.domain.dao.impl;

import gazap.domain.dao.Dao;
import gazap.domain.entity.base.DomainEntity;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class DaoImpl implements Dao {
    private TransactionTemplate transactionTemplate;
    private SessionFactory sessionFactory;

    @Autowired(required = false)
    @Qualifier("gazap.dao.sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired(required = false)
    @Qualifier("gazap.dao.transactionManager")
    public void setPlatformTransactionManager(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    protected final Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public TransactionStatus getTransaction() {
        return transactionTemplate.getTransactionManager().getTransaction(new DefaultTransactionDefinition());
    }

    public void commit(TransactionStatus transaction) {
        transactionTemplate.getTransactionManager().commit(transaction);
    }

    public void rollback(TransactionStatus transaction) {
        transactionTemplate.getTransactionManager().rollback(transaction);
    }

    @SuppressWarnings("unchecked")
    public <T extends DomainEntity> T merge(T entity) {
        return (T) getSession().merge(entity);
    }

    @Override
    public void delete(DomainEntity entity) {
        getSession().delete(entity);
    }

    @Override
    public void create(DomainEntity entity) {
        getSession().save(entity);
    }

    @Override
    public void save(DomainEntity entity) {
        getSession().update(entity);
    }

    public void saveOrCreate(DomainEntity entity) {
        getSession().saveOrUpdate(entity);
    }

    public static int toNumberInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        throw new IllegalArgumentException("value is not a number");
    }
}
