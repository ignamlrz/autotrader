package io.github.ignamlrz.autotrader.service.market.chart;

import io.github.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import io.github.ignamlrz.autotrader.core.model.PairAsset;
import io.github.ignamlrz.autotrader.core.model.market.ChartModel;
import io.github.ignamlrz.autotrader.core.utilities.time.Interval;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Chart model
 */
@Document
public class Chart {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    @MongoId
    final String id;

    @Indexed
    final Interval interval;

    @Indexed
    final PairAsset pairAsset;

    @Indexed
    final String type;

    final Map<Long, ChartData> data;

    @DBRef
    final Map<Indicator, IndicatorOutput> indicators;

    // ========================================================
    // = CONSTRUCTOR
    // ========================================================

    /**
     * Constructor of a chart
     *
     * @param chart chart from which is created
     */
    public Chart(
            ChartModel chart
    ) {
        this.type = chart.getType();
        this.interval = chart.getInterval();
        this.pairAsset = chart.getPairAsset();
        this.id = chart.getId();
        this.data = chart.getCandlesticks().stream()
                .collect(Collectors.toMap(
                        candlestick -> candlestick.getTimeframe().getOpen(),
                        candlestick -> new ChartData(ChartUtils.toArray(candlestick)),
                        (k, v) -> v,
                        TreeMap::new
                ));
        indicators = new HashMap<>();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter of an identifier
     *
     * @return identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Getter of interval
     *
     * @return interval
     */
    public Interval getInterval() {
        return interval;
    }

    /**
     * Getter of pair asset
     *
     * @return pair asset
     */
    public PairAsset getPairAsset() {
        return pairAsset;
    }

    /**
     * Getter of type exchange
     *
     * @return type exchange
     */
    public String getType() {
        return type;
    }

    /**
     * Getter of data
     *
     * @return data
     */
    public Map<Long, ChartData> getData() {
        return data;
    }
//
//    /**
//     * Getter of all indicators
//     *
//     * @return indicators
//     */
//    public List<IndicatorOutput> getIndicators() {
//        return indicators;
//    }

    // ========================================================
    // = METHODS
    // ========================================================
//
//    /**
//     * Method for add an indicator
//     *
//     * @param indicator Appends the specified element to the map and is calculated
//     * @return output calculated
//     */
//    public boolean add(Indicator indicator, int minCandlesticks) {
//        IndicatorOutput output = this.indicators.get(indicator);
//        if (!output) this.indicators.add(indicator);
//        return !exists;
//    }
//
//    /**
//     * Method for add an indicator
//     *
//     * @param indicator Appends the specified element to the end of this list (optional operation)
//     * @return true if not exists and is added, false otherwise
//     */
//    public IndicatorOutput remove(Class<IndicatorOutput> indicator) {
//        boolean exists = this.indicators.removeIf(item -> item.getClass().equals(indicator));
//        if (!exists) this.indicators.add(indicator);
//        return !exists;
//    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public String toString() {
        return "Chart{" +
                "id='" + id + '\'' +
                '}';
    }
}
