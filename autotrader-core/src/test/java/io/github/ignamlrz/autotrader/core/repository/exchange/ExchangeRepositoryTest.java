package io.github.ignamlrz.autotrader.core.repository.exchange;

import io.github.ignamlrz.autotrader.core.annotation.MongoSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MongoSpringBootTest
class ExchangeRepositoryTest {

    @Autowired
    ExchangeRepository repository;

    @Test
    void test1() {
        repository.save(new Exchange(ExchangeSupplier.BINANCE));
        Exchange ex = repository.findById(ExchangeSupplier.BINANCE).orElseThrow();

        assertEquals(ex.getName(), ExchangeSupplier.BINANCE);
        assertEquals(ex.getStatus(), ExchangeStatus.MEDIUM);
    }
}