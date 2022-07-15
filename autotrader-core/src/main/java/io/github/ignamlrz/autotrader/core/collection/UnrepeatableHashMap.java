package io.github.ignamlrz.autotrader.core.collection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection for get an unrepeatable hash map
 *
 * @param <K> Key value to search
 */
// TODO Tests
public abstract class UnrepeatableHashMap<K, V> implements Map<K, V> {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private final Map<K, V> unrepeatable;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    protected UnrepeatableHashMap() {
        this.unrepeatable = new HashMap<>();
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public int size() {
        return this.unrepeatable.size();
    }

    @Override
    public boolean isEmpty() {
        return this.unrepeatable.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.unrepeatable.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.unrepeatable.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return this.unrepeatable.get(key);
    }

    @Override
    public V put(K key, V value) {
        // ...check key is not repeated
        if (this.containsKey(key))
            throw new IllegalArgumentException(String.format(
                    "key '%s' was already inserted", key
            ));
        // ...check value is not repeated
        if (this.containsValue(value))
            throw new IllegalArgumentException(String.format(
                    "value '%s' was already inserted", value
            ));

        return this.unrepeatable.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return this.unrepeatable.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        // ...check any value is repeated
        List<? extends V> repeatedValues = m.values().stream()
                .filter(this::containsValue)
                .collect(Collectors.toUnmodifiableList());
        if (!repeatedValues.isEmpty())
            throw new IllegalArgumentException(String.format(
                    "values '%s' already existed previously", repeatedValues
            ));

        this.unrepeatable.putAll(m);
    }

    @Override
    public void clear() {
        this.unrepeatable.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.unrepeatable.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.unrepeatable.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.unrepeatable.entrySet();
    }
}
