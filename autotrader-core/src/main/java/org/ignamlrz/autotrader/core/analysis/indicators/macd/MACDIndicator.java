package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.ignamlrz.autotrader.core.analysis.AnalysisResult;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicator;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOutput;
import org.ignamlrz.autotrader.core.model.market.BasicChart;
import org.ignamlrz.autotrader.core.utilities.FloatUtils;

/**
 * Moving Average Convergence/Divergence indicator
 *
 * @see <a href="https://www.investopedia.com/terms/m/macd.asp">investopedia.com</a>
 * @see <a href="https://tulipindicators.org/macd">tulipindicators.org</a>
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class MACDIndicator extends Indicator {

    // ========================================================
    // = STATIC FIELDS
    // ========================================================

    static final String IDENTIFIER = "macd";
    static final String NAME = "Moving Average Convergence/Divergence";
    static final Indicator.Type TYPE = Type.INDICATOR;

    // Error messages
    static final String INPUT_ERROR_MSG = "input must be an MACD Indicator Input";
    static final String OUTPUT_ERROR_MSG = "output must be an MACD Indicator Output";

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * MACD Indicator options
     */
    MACDIndicatorOptions options;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of the {@link MACDIndicator}
     *
     * @param options to use
     */
    public MACDIndicator(MACDIndicatorOptions options) {
        super(IDENTIFIER, NAME, TYPE);
        this.options = options;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public AnalysisResult analyze(IndicatorOutput output) {
        if (!(output instanceof MACDIndicatorOutput)) {
            throw new IllegalArgumentException(OUTPUT_ERROR_MSG);
        }
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <T extends IndicatorInput> IndicatorOutput run(T input) {
        if (!(input instanceof MACDIndicatorInput)) {
            throw new IllegalArgumentException(INPUT_ERROR_MSG);
        }

        return macd((MACDIndicatorInput) input);
    }

    @Override
    public <T extends BasicChart> IndicatorOutput run(T chart) {
        Float[] reals = FloatUtils.arrayOf(chart.getDataFrom(getOptions().getTarget()));
        MACDIndicatorInput input = new MACDIndicatorInput(reals);
        return run(input);
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

    /**
     * Process an MACD indicator
     *
     * @param input MACD indicator input
     * @return an MACD Indicator output
     */
    private MACDIndicatorOutput macd(MACDIndicatorInput input) {
        // ...calculate short and long EMA
        EMAIndicatorOutput shortOutput = ema(input.getReals(), getOptions().getShortPeriod());
        EMAIndicatorOutput longOutput = ema(input.getReals(), getOptions().getLongPeriod());

        // Calculate macd
        Float[] macd = FloatUtils.subtract(shortOutput.getEma(), longOutput.getEma());

        // ...values before longPeriod are marked as invalid
        FloatUtils.invalidateBefore(macd, getOptions().getLongPeriod() - 1);

        // ...calculate signal
        Float[] signal = ema(macd, getOptions().getSignalPeriod()).getEma();

        // ...calculate histogram
        Float[] histogram = FloatUtils.subtract(macd, signal);

        return new MACDIndicatorOutput(macd, signal, histogram);
    }

    /**
     * Process an EMA indicator
     *
     * @param input  Array of values to process
     * @param period Period to process
     * @return an array of results
     */
    EMAIndicatorOutput ema(Float[] input, int period) {
        // ...build options
        EMAIndicatorOptions options = EMAIndicatorOptions.builder()
                .period(period)
                .smothering(getOptions().getSmothering())
                .build();

        // ...run indicator
        return EMAIndicator.run(input, options);
    }
}
