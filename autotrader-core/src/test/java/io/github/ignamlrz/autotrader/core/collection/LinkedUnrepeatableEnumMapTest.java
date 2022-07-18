package io.github.ignamlrz.autotrader.core.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of {@link LinkedUnrepeatableEnumMap} class
 */
class LinkedUnrepeatableEnumMapTest {
    System.Logger.Level[] levels = new System.Logger.Level[]{
            System.Logger.Level.DEBUG, System.Logger.Level.WARNING, System.Logger.Level.ERROR
    };

    @Test
    void test_construction() {
        LinkedUnrepeatableEnumMap<System.Logger.Level> map = new LinkedUnrepeatableEnumMap<>(levels);

        // ...check size
        assertFalse(map.isEmpty());
        assertEquals(levels.length, map.size());
        // ...check construction
        for(int i=0; i<levels.length; i++) {
            assertTrue(map.containsKey(levels[i]));
            assertEquals(i, map.get(levels[i]));
        }
    }

    @Test
    void test_unrepeatable() {
        LinkedUnrepeatableEnumMap<System.Logger.Level> map = new LinkedUnrepeatableEnumMap<>(levels);

        // ...check can not repeat key
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> map.put(System.Logger.Level.DEBUG, 3));
        assertEquals(String.format(
                "key '%s' was already inserted", System.Logger.Level.DEBUG
        ), ex1.getMessage());
        // ...check can not repeat value
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> map.put(System.Logger.Level.INFO, 2));
        assertEquals(String.format(
                "value '%s' was already inserted", 2
        ), ex2.getMessage());
    }

    @Test
    void test_removalAndInsertions() {
        LinkedUnrepeatableEnumMap<System.Logger.Level> map1 = new LinkedUnrepeatableEnumMap<>(levels);
        LinkedUnrepeatableEnumMap<System.Logger.Level> repeatedKeys = new LinkedUnrepeatableEnumMap<>(levels);
        LinkedUnrepeatableEnumMap<System.Logger.Level> repeatedValues = new LinkedUnrepeatableEnumMap<>(
                new System.Logger.Level[]{
                        System.Logger.Level.TRACE, System.Logger.Level.INFO
                }
        );

        // ...check is not inserted a repeated map keys
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> map1.putAll(repeatedKeys));
        assertEquals(String.format(
                "keys '%s' already existed previously", "[DEBUG, WARNING, ERROR]"
        ), ex1.getMessage());
        assertEquals(levels.length, map1.size());

        // ...check is not inserted a repeated map values
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> map1.putAll(repeatedValues));
        assertEquals(String.format(
                "values '%s' already existed previously", "[0, 1]"
        ), ex2.getMessage());
        assertEquals(levels.length, map1.size());

        // ...clear map
        map1.clear();

        // ...check now can insert map2 on map1
        map1.putAll(repeatedKeys);

        // ...check is inserted all
        assertEquals(repeatedKeys.size(), map1.size());

        // ...check remove a nonexistent value return null
        assertNull(map1.remove(System.Logger.Level.ALL));

        // ...check remove debug return debug
        assertEquals(0, map1.remove(System.Logger.Level.DEBUG));

        // ...check size is now levels.length - 1
        assertEquals(levels.length - 1, map1.size());
    }
}