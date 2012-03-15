package gazap.site.model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class SimpleRegistry<K, V> {
    private final Map<K, V> map = new TreeMap<K, V>();

    public boolean add(K key, V value) {
        if (map.containsKey(key)) {
            return false;
        }
        map.put(key, value);
        return true;
    }

    public Collection<V> values() {
        return map.values();
    }
}
