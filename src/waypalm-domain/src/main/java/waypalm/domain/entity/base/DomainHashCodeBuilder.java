package waypalm.domain.entity.base;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class DomainHashCodeBuilder extends HashCodeBuilder {
    public DomainHashCodeBuilder append(StringIdentity object) {
        append(object == null ? null : object.getId());
        return this;
    }

    public DomainHashCodeBuilder append(IntegerIdentity object) {
        append(object == null ? null : object.getId());
        return this;
    }
}
