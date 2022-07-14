package io.github.ignamlrz.autotrader.service.market.chart;

import io.github.ignamlrz.autotrader.core.model.market.ChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartService {

    @Autowired
    ChartRepository chartRepository;

    public Chart insert(ChartModel c) {
        Chart chart = new Chart(c);
        return chartRepository.save(chart);
    }
}
