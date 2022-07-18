package io.github.ignamlrz.autotrader.core.repository.general;

import com.mongodb.lang.Nullable;
import io.github.ignamlrz.autotrader.core.utilities.time.Timeframe;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public final class TimeSeries<T extends Series> {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private final Map<Long, T> series;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    public TimeSeries() {
        this.series = new TreeMap<>();
    }

    // ========================================================
    // = GETTER/SETTER
    // ========================================================

    /**
     * Getter of full series
     *
     * @return full series
     */
    public @NotNull Map<Long, T> getSeries() {
        return this.series;
    }

    /**
     * Getter of a single series
     *
     * @param timestamp to get
     * @return a single series
     */
    public @Nullable Map.Entry<Long, T> getSeries(Long timestamp) {
        return this.series.entrySet().stream()
                .filter(series -> series.getKey() == timestamp)
                .findFirst().orElse(null);
    }

    /**
     * Getter of timeframe series
     *
     * @param timeframe to get
     * @return a timeframe series
     */
    public @NotNull Map<Long, T> getSeries(Timeframe timeframe) {
        return this.series.entrySet().stream()
                .filter(series -> series.getKey() >= timeframe.getOpen() && series.getKey() <= timeframe.getClose())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        TreeMap::new
                ));
    }


    // ========================================================
    // = METHODS
    // ========================================================


    /**
     * Method for clear series
     */
    public void clear() {
        this.series.clear();
    }

    /**
     * Insert a new series
     *
     * @param timestamp to insert
     * @param series    to insert
     */
    public void insert(@NotNull Long timestamp, @NotNull T series) {
        // ...check timestamp exists
        if (this.series.containsKey(timestamp)) {
            throw new IllegalArgumentException("timestamp " + timestamp + " already exists");
        }

        // ...search previous/next series
        Map.Entry<Long, T> previousEntry = findPrevious(timestamp);
        T previous = (previousEntry != null) ? previousEntry.getValue() : null;
        Map.Entry<Long, T> nextEntry = findNext(timestamp);
        T next = (nextEntry != null) ? nextEntry.getValue() : null;

        // ...attach previous/next series
        if (previous != null) previous.setNext(series);
        if (next != null) next.setPrevious(series);
        series.setPrevious(previous);
        series.setNext(next);
        this.series.put(timestamp, series);
    }

    /**
     * Method for find previous series
     *
     * @param timestamp to search
     * @return a map if exists a previous series, false otherwise
     */
    public @Nullable Map.Entry<Long, T> findPrevious(@NotNull Long timestamp) {
        return this.series.entrySet().stream()
                .filter(series -> series.getKey() < timestamp)
                .max(Comparator.comparingLong(Map.Entry::getKey))
                .orElse(null);
    }

    /**
     * Method for find next series
     *
     * @param timestamp to search
     * @return a map if exists a next series, false otherwise
     */
    public @Nullable Map.Entry<Long, T> findNext(@NotNull Long timestamp) {
        return this.series.entrySet().stream()
                .filter(series -> series.getKey() > timestamp)
                .min(Comparator.comparingLong(Map.Entry::getKey)).orElse(null);
    }

    /**
     * Method for remove a series
     *
     * @param timestamp to remove
     * @return the removed series, null otherwise
     */
    @SuppressWarnings("unchecked")
    public T remove(Long timestamp) {
        T series = this.series.remove(timestamp);
        if(series == null) return null;
        T previous = (T) series.previous();
        T next = (T) series.next();
        if (previous != null) previous.setNext(next);
        if (next != null) next.setPrevious(previous);
        return series;
    }

    /**
     * Method for remove series
     *
     * @param timeframe to remove
     * @return a list of removed series
     */
    public List<T> remove(Timeframe timeframe) {
        return this.series.keySet().stream()
                .filter(k -> timeframe.getOpen() >= k && k <= timeframe.getClose())
                .map(this::remove)
                .collect(Collectors.toList());
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

}
