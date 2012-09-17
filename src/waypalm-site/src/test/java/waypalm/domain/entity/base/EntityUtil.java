package waypalm.domain.entity.base;

import com.iserv2.test.Values;

public class EntityUtil {
    public static <T extends IntegerIdentity> T withId(T entity) {
        return withId(entity, Values.integerAbs());
    }

    public static <T extends IntegerIdentity> T withId(T entity, Integer id) {
        entity.setId(id);
        return entity;
    }
}
