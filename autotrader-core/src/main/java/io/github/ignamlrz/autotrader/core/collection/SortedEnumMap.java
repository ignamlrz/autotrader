package io.github.ignamlrz.autotrader.core.collection;

import java.util.Iterator;
import java.util.List;

/**
 * Collection for get a sorted unrepeatable hash map of enums
 *
 * @param <E> type of enum
 */
//TODO Tests
public class SortedEnumMap<E extends Enum<?>> extends UnrepeatableHashMap<E, Integer> {

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of a {@link SortedEnumMap}
     *
     * @param array Array of enums
     */
    public SortedEnumMap(E[] array) {
        this(List.of(array));
    }

    /**
     * Constructor of a {@link List} of {@link SortedEnumMap}
     *
     * @param listEnums List of enums
     */
    public SortedEnumMap(List<E> listEnums) {
        // ...iterate each key and increment in one his value
        Iterator<E> it = listEnums.iterator();
        int i = 0;
        while (it.hasNext()) {
            E key = it.next();
            this.put(key, i++);
        }
    }
}
