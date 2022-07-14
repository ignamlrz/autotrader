package io.github.ignamlrz.autotrader.core.analysis.indicators;

import io.github.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorInput;

public class IndicatorTests {

    // ========================================================
    // = PREDEFINED FIELDS
    // ========================================================

    public static final Float[] predefinedInput = {
            81.59f, 81.06f, 82.87f, 83.00f, 83.61f,
            83.15f, 82.84f, 83.99f, 84.55f, 84.36f,
            85.53f, 86.54f, 86.89f, 87.77f, 87.29f
    };

    public static final EMAIndicatorInput emaInputs =  new EMAIndicatorInput(new Float[2]);

    // ========================================================
    // = EXPECTED EMA FIELDS
    // ========================================================

    public static final Float[] expectedEma = {
            81.59f, 81.41f, 81.90f, 82.27f, 82.71f,
            82.86f, 82.85f, 83.23f, 83.67f, 83.90f,
            84.44f, 85.14f, 85.73f, 86.70f, 86.41f
    };

    // ========================================================
    // = EXPECTED MACD FIELDS
    // ========================================================

    public static final Float[] expectedMacd = {
            null, null, null, null, 0.62f,
            0.35f, 0.11f, 0.42f, 0.58f, 0.42f,
            0.68f, 0.93f, 0.89f, 0.98f, 0.62f
    };

    public static final Float[] expectedMacdSignal = {
            null, null, null, null, 0.62f,
            0.56f, 0.47f, 0.46f, 0.49f, 0.47f,
            0.52f, 0.60f, 0.66f, 0.72f, 0.70f
    };


    public static final Float[] expectedMacdHistogram = {
            null, null, null, null, 0.00f,
            -0.21f, -0.36f, -0.05f, 0.09f, -0.05f,
            0.17f, 0.33f, 0.24f, 0.26f, -0.08f
    };
}
