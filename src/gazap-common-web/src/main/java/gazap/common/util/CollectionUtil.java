package gazap.common.util;

import java.util.Collection;

public class CollectionUtil {
    public static <T> T find(Collection<T> collection, Predicate<? super T> predicate) {
        if (collection != null && predicate != null) {
            for (T item : collection) {
                if (predicate.evaluate(item)) {
                    return item;
                }
            }
        }
        return null;
    }
}
