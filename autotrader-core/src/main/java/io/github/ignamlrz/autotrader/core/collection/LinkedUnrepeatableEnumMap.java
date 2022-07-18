package io.github.ignamlrz.autotrader.core.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Collection for get a sorted unrepeatable hash map of enums
 *
 * @param <E> type of enum
 */
public final class LinkedUnrepeatableEnumMap<E extends Enum<?>> extends UnrepeatableHashMap<E, Integer> {

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of a {@link LinkedUnrepeatableEnumMap}
     *
     * @param array Array of enums
     */
    public LinkedUnrepeatableEnumMap(E[] array) {
        this(List.of(array));
    }

    /**
     * Constructor of a {@link List} of {@link LinkedUnrepeatableEnumMap}
     *
     * @param listEnums List of enums
     */
    public LinkedUnrepeatableEnumMap(Collection<E> listEnums) {
        // ...iterate each key and increment in one his value
        Iterator<E> it = listEnums.iterator();
        int i = 0;
        while (it.hasNext()) {
            E key = it.next();
            this.put(key, i++);
        }
    }

    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Method for get sorted enums of this map
     *
     * @return sorted enums
     */
    public Collection<E> getSortedEnums() {
        return entrySet().stream()
                .sorted(Comparator.comparingInt(Entry::getValue))
                .map(Entry::getKey)
                .collect(Collectors.toList());
    }
}
