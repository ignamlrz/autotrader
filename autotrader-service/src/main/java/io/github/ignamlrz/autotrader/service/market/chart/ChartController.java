package io.github.ignamlrz.autotrader.service.market.chart;

import io.github.ignamlrz.autotrader.binance.models.market.BinanceCandlestick;
import io.github.ignamlrz.autotrader.binance.models.market.BinanceChartModel;
import io.github.ignamlrz.autotrader.core.model.PairAsset;
import io.github.ignamlrz.autotrader.core.utilities.time.Interval;
import io.github.ignamlrz.autotrader.core.utilities.time.Timeframe;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "chart", description = "Manage charts")
@RestController
@RequestMapping("status")
public class ChartController {

    @Autowired
    ChartService chartService;

    @GetMapping("insert")
    public Mono<Chart> serviceBinance(@RequestParam Interval interval, @RequestParam  String baseAsset, @RequestParam  String quoteAsset) {
        PairAsset pairAsset = new PairAsset(baseAsset, quoteAsset);
        List<BinanceCandlestick> data = new ArrayList<>();
        float previousPrice = 1.f;
        for (int i = 0; i < 10; i++) {
            float percentage = 1 - ((randomG(4) - 0.5f) * 10.f) * 0.01f;
            float nextPrice = previousPrice * percentage;
            float maxValue = Math.max(previousPrice, nextPrice) * (1f + (float) Math.random() * 0.05f);
            float minvalue = Math.min(previousPrice, nextPrice) * (1f - (float) Math.random() * 0.05f);
            BinanceCandlestick bc = new BinanceCandlestick(
                    previousPrice,
                    maxValue,
                    minvalue,
                    nextPrice,
                    (float) Math.random() * 1_000_000f,
                    new Timeframe(i * interval.toMillis(), (i + 1) * interval.toMillis() - 1),
                    (int) (Math.random() * 1000),
                    (float) Math.random() * 1_000_000f,
                    (float) Math.random() * 1_000_000f,
                    (float) Math.random() * 1_000_000f
            );
            previousPrice = nextPrice;

            data.add(bc);
        }

        Chart c = chartService.insert(new BinanceChartModel(
                pairAsset,
                interval,
                data
        ));
        return Mono.just(c);
    }

    // v is the number of times random is summed and should be over >= 1
    private float randomG(int v){
        float r = 0;
        for(int i = 0; i < v; i++){
            r += Math.random();
        }
        return r / v;
    }
}
