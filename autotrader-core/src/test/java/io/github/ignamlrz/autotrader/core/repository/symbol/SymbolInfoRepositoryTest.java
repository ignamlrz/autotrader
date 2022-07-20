package io.github.ignamlrz.autotrader.core.repository.symbol;

import io.github.ignamlrz.autotrader.core.annotation.MongoSpringBootTest;
import io.github.ignamlrz.autotrader.core.repository.exchange.Exchange;
import io.github.ignamlrz.autotrader.core.repository.exchange.ExchangeSupplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MongoSpringBootTest
class SymbolInfoRepositoryTest {
    @Autowired
    SymbolInfoRepository repository;

    @Test
    void test() {
        Exchange ex = new Exchange(ExchangeSupplier.BINANCE);
        SymbolInfo test = new SymbolInfo(ex, "btc", "usdt", true, false);
        repository.save(test);
        SymbolInfo s = repository.findById(test.getSymbol()).orElseThrow();

        assertEquals(test.getSymbol(), s.getSymbol());
    }
}