package gazap.domain.dao;

import gazap.domain.entity.base.DomainEntityCU;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

import java.util.Date;

public class SessionListener implements PreInsertEventListener, PreUpdateEventListener {
    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        if (event.getEntity() instanceof DomainEntityCU) {
            DomainEntityCU instance = (DomainEntityCU) event.getEntity();
            instance.setUpdatedAt(new Date());
            instance.setCreatedAt(new Date());
        }
        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        if (event.getEntity() instanceof DomainEntityCU) {
            DomainEntityCU instance = (DomainEntityCU) event.getEntity();
            instance.setUpdatedAt(new Date());
        }
        return false;
    }
}
