package gazap.domain.entity.base;

import java.util.Date;

public interface DomainEntityCU {
    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    Date getUpdatedAt();

    void setUpdatedAt(Date updatedAt);
}
