package org.ignamlrz.autotrader.core.utilities;

import java.util.Iterator;
import java.util.List;

public class FloatUtils {

    public static float[] arrayOf(List<Float> collection) {
        float[] result = new float[collection.size()];
        Iterator<Float> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            result[i++] = it.next();
        }
        return result;
    }

    public static Float[] boxedArrayOf(List<Float> collection) {
        Float[] result = new Float[collection.size()];
        Iterator<Float> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            result[i++] = it.next();
        }
        return result;
    }
}
