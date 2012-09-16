package gazap.domain.dao.impl;

import gazap.domain.dao.Dao;
import gazap.domain.entity.base.DomainEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class DaoImpl implements Dao {
    private SessionFactory sessionFactory;

    @Autowired(required = false)
    @Qualifier("gazap.dao.sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected final Session getSession() {
        return sessionFactory.getCurrentSession();
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

    public static int toNumberInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        throw new IllegalArgumentException("value is not a number");
    }
}
