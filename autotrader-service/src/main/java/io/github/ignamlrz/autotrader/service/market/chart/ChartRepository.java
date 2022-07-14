package io.github.ignamlrz.autotrader.service.market.chart;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChartRepository extends CrudRepository<Chart, String> {
    /**
     * Find by exchange type
     *
     * @param type Exchange type
     * @return a list of charts
     */
    List<Chart> findByType(String type);

    /**
     * Find by interval
     *
     * @param interval Interval
     * @return a list of charts
     */
    List<Chart> findByInterval(String interval);

    /**
     * Find by pair asset
     *
     * @param pairAsset Pair of assets
     * @return a list of charts
     */
    List<Chart> findByPairAsset(String pairAsset);
}
