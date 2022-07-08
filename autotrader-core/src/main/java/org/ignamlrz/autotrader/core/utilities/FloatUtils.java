package org.ignamlrz.autotrader.core.utilities;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FloatUtils {

    // ========================================================
    // = STATIC METHODS
    // ========================================================

    /**
     * Static method for generate an array from a {@link Collection} of {@link Float}
     *
     * @param collection Collection to parse
     * @return an array of {@link Float}
     */
    public static Float[] arrayOf(Collection<Float> collection) {
        // ...initialize variables
        Float[] result = new Float[collection.size()];
        // ...iterate each element of this collection
        Iterator<Float> it = collection.iterator();
        for (int i = 0; it.hasNext(); i++) {
            result[i] = it.next();
        }
        // ...return created array
        return result;
    }

    /**
     * Static method for add two arrays of {@link Float}
     *
     * @param a First addend array of {@link Float} - Can contain nullable elements
     * @param b Second addend array of {@link Float} - Can contain nullable elements
     * @return result array of {@link Float}
     * @throws IllegalArgumentException if size of both arrays not is the same
     */
    public static Float[] add(Float[] a, Float[] b) {
        return operate(a, b, FloatUtils::add);
    }

    /**
     * Static method for add two collections of {@link Float}
     *
     * @param a First addend collection of {@link Float} - Can contain nullable elements
     * @param b Second addend collection of {@link Float} - Can contain nullable elements
     * @return result array of {@link Float}
     * @throws IllegalArgumentException if size of both arrays not is the same
     */
    public static Float[] add(Collection<Float> a, Collection<Float> b) {
        // ...check size of both arrays
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("size of both collections must be the same");
        }
        return add(arrayOf(a), arrayOf(b));
    }

    /**
     * Static method for invalidate external elements of an array of {@link Float} given a range
     *
     * @param a      Target array of {@link Float} - Can contain nullable elements
     * @param before Invalidate before this index (exclusive)
     * @param after  Invalidate after this index (exclusive)
     * @return result array of {@link Float}
     * @throws IndexOutOfBoundsException if range (being and end) is out of bounds
     */
    public static void invalidate(Float[] a, int before, int after) {
        int size = a.length;
        // ...check indexes is out of range
        if (before < 0 || size <= before) {
            throw new IndexOutOfBoundsException("Begin index is out of range");
        }
        if (after <= before || size <= after) {
            throw new IndexOutOfBoundsException("End index is out of range");
        }
        // ...invalidate before range
        for (int i = 0; i < before; i++) {
            a[i] = null;
        }
        // ...invalidate after range
        for (int i = after + 1; i < size; i++) {
            a[i] = null;
        }
    }

    /**
     * Static method for invalidate elements of an array of {@link Float} before specified index
     *
     * @param a      Target array of {@link Float} - Can contain nullable elements
     * @param before Invalidate before this index (exclusive)
     * @return result array of {@link Float}
     */
    public static void invalidateBefore(Float[] a, int before) {
        invalidate(a, before, a.length - 1);
    }

    /**
     * Static method for invalidate elements of an array of {@link Float} after specified index
     *
     * @param a     Target array of {@link Float} - Can contain nullable elements
     * @param after Invalidate after this index (exclusive)
     * @return result array of {@link Float}
     */
    public static void invalidateAfters(Float[] a, int after) {
        invalidate(a, 0, after);
    }

    /**
     * Static method for multiply an array of {@link Float} with a scalar
     *
     * @param a Multiplicand array of {@link Float} - Can contain nullable elements
     * @param b Multiplier
     * @return result array of {@link Float}
     */
    public static Float[] multiply(Float[] a, Float b) {
        return operate(a, b, FloatUtils::multiply);
    }

    /**
     * Static method for multiply two arrays of {@link Float}
     *
     * @param a Multiplicand array of {@link Float} - Can contain nullable elements
     * @param b Multiplier array of {@link Float} - Can contain nullable elements
     * @return result array of {@link Float}
     * @throws IllegalArgumentException if size of both arrays not is the same
     */
    public static Float[] multiply(Float[] a, Float[] b) {
        return operate(a, b, FloatUtils::multiply);
    }

    /**
     * Static method for add two collections of {@link Float}
     *
     * @param a Multiplicand collection of {@link Float} - Can contain nullable elements
     * @param b Multiplier collection of {@link Float} - Can contain nullable elements
     * @return result array of {@link Float}
     * @throws IllegalArgumentException if size of both arrays not is the same
     */
    public static Float[] multiply(Collection<Float> a, Collection<Float> b) {
        // ...check size of both arrays
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("size of both collections must be the same");
        }
        return multiply(arrayOf(a), arrayOf(b));
    }

    /**
     * Static method for multiply negate an array of {@link Float}
     *
     * @param a Target array of {@link Float} - Can contain nullable elements
     * @return result array of {@link Float}
     */
    public static Float[] negate(Float[] a) {
        return operate(a, -1.f, FloatUtils::multiply);
    }

    /**
     * Static method for subtract two arrays of {@link Float}
     *
     * @param a Minuend array of {@link Float} - Can contain nullable elements
     * @param b Subtrahend array of {@link Float} - Can contain nullable elements
     * @return result array of {@link Float}
     * @throws IllegalArgumentException if size of both arrays not is the same
     */
    public static Float[] subtract(Float[] a, Float[] b) {
        return operate(a, b, FloatUtils::subtract);
    }

    /**
     * Static method for subtract two collections of {@link Float}
     *
     * @param a Minuend collection of {@link Float} - Can contain nullable elements
     * @param b Subtrahend collection of {@link Float} - Can contain nullable elements
     * @return result array of {@link Float}
     * @throws IllegalArgumentException if size of both arrays not is the same
     */
    public static Float[] subtract(Collection<Float> a, Collection<Float> b) {
        // ...check size of both arrays
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("size of both collections must be the same");
        }
        return subtract(arrayOf(a), arrayOf(b));
    }

    // ========================================================
    // = PRIVATE STATIC METHODS
    // ========================================================

    /**
     * Static method for do an addition of two floats
     *
     * @param a First addend
     * @param b Second addend
     * @return addition
     */
    private static Float add(@Nullable Float a, @Nullable Float b) {
        return (a != null && b != null) ? a + b : null;
    }

    /**
     * Static method for do a multiplication of two floats
     *
     * @param a Multiplicand
     * @param b Multiplier
     * @return result
     */
    private static Float multiply(@Nullable Float a, @Nullable Float b) {
        return (a != null && b != null) ? a * b : null;
    }

    /**
     * Private method for run a function between two arrays of {@link Float}
     *
     * @param a        First array of {@link Float}
     * @param b        Seconds array of {@link Float}
     * @param function Function to apply
     * @return a result array of {@link Float}
     */
    private static Float[] operate(Float[] a, Float[] b, BiFunction<Float, Float, Float> function) {
        // ...check size of both arrays
        if (a.length != b.length) {
            throw new IllegalArgumentException("size of both arrays must be the same");
        }
        // ...initialize variables
        int size = a.length;
        Float[] result = new Float[size];
        // ...process function
        for (int i = 0; i < size; i++) {
            result[i] = function.apply(a[i], b[i]);
        }
        return result;
    }

    /**
     * Private method for run a function between an array of {@link Float}
     *
     * @param a        First array of {@link Float}
     * @param number   Number to use on function
     * @param function Function to apply
     * @return a result array of {@link Float}
     */
    private static Float[] operate(Float[] a, float number, BiFunction<Float, Float, Float> function) {
        // ...initialize variables
        int size = a.length;
        Float[] result = new Float[size];
        // ...process function
        for (int i = 0; i < size; i++) {
            result[i] = function.apply(a[i], number);
        }
        return result;
    }

    /**
     * Private method for run a function between an array of {@link Float}
     *
     * @param a        First array of {@link Float}
     * @param function Function to apply
     * @return a result array of {@link Float}
     */
    private static Float[] operate(Float[] a, Function<Float, Float> function) {
        // ...initialize variables
        int size = a.length;
        Float[] result = new Float[size];
        // ...process function
        for (int i = 0; i < size; i++) {
            result[i] = function.apply(a[i]);
        }
        return result;
    }

    /**
     * Static method for do a subtraction of two floats
     *
     * @param a Minuend
     * @param b Subtrahend
     * @return subtraction
     */
    public static Float subtract(@Nullable Float a, @Nullable Float b) {
        return (a != null && b != null) ? a - b : null;
    }
}
