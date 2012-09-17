package waypalm.domain.dao;

import waypalm.domain.entity.base.IntegerIdentity;
import waypalm.domain.entity.base.StringIdentity;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SessionHashCodeBuilder extends HashCodeBuilder {
    public SessionHashCodeBuilder append(StringIdentity object) {
        append(object == null ? null : object.getId());
        return this;
    }

    public SessionHashCodeBuilder append(IntegerIdentity object) {
        append(object == null ? null : object.getId());
        return this;
    }
}
