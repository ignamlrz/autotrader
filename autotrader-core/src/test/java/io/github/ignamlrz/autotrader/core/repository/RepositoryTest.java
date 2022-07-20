package io.github.ignamlrz.autotrader.core.repository;

import io.github.ignamlrz.autotrader.core.annotation.MongoSpringBootTest;
import io.github.ignamlrz.autotrader.core.repository.candlestick.Candlestick;
import io.github.ignamlrz.autotrader.core.repository.candlestick.CandlestickRepository;
import io.github.ignamlrz.autotrader.core.repository.exchange.Exchange;
import io.github.ignamlrz.autotrader.core.repository.exchange.ExchangeRepository;
import io.github.ignamlrz.autotrader.core.repository.exchange.ExchangeStatus;
import io.github.ignamlrz.autotrader.core.repository.symbol.Symbol;
import io.github.ignamlrz.autotrader.core.repository.symbol.SymbolRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.ignamlrz.autotrader.core.repository.RepositoryConstantsTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MongoSpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RepositoryTest {

    // ========================================================
    // = REPOSITORIES
    // ========================================================

    @Autowired
    ExchangeRepository exchangeRepository;
    @Autowired
    SymbolRepository symbolRepository;
    @Autowired
    CandlestickRepository candlestickRepository;

    // ========================================================
    // = TEST METHODS
    // ========================================================

    @Test
    @Order(1)
    public void test_exchangeRepository() {
        exchangeRepository.save(new Exchange(EXCHANGE_SUPPLIER));
        Exchange ex = exchangeRepository.findById(EXCHANGE_SUPPLIER).orElseThrow();

        assertEquals(EXCHANGE_SUPPLIER, ex.getSupplier());
        assertEquals(ExchangeStatus.MEDIUM, ex.getStatus());
    }

    @Test
    @Order(2)
    void test_symbolRepository() {
        Exchange exchange = exchangeRepository.findById(EXCHANGE_SUPPLIER).orElseThrow();
        Symbol test = new Symbol(exchange, SYMBOL_ASSET, SYMBOL_QUOTE);
        symbolRepository.save(test);
        Symbol symbol = symbolRepository.findById(test.getSymbol()).orElseThrow();

        assertEquals(test.getClass(), symbol.getClass());
        assertEquals(test.getSymbol(), symbol.getSymbol());
        assertEquals(test.getExchange().toString(), symbol.getExchange().toString());
        assertEquals(test.getBase(), symbol.getBase());
        assertEquals(test.getQuote(), symbol.getQuote());
    }

    @Test
    @Order(3)
    void test_candlestickRepository() {
        Exchange exchange = exchangeRepository.findById(EXCHANGE_SUPPLIER).orElseThrow();
        Symbol symbol = new Symbol(exchange, SYMBOL_ASSET, SYMBOL_QUOTE);
        Candlestick test = new Candlestick(symbol, KLINE_INTERVAL, TIMESTAMP, OPEN, HIGH, LOW, CLOSE, VOLUME, TRADES, TAKER_BUY_VOLUME);
        candlestickRepository.save(test);
        Candlestick candlestick = candlestickRepository.findCandlestickBySymbolAndIntervalAndTimestamp(symbol, KLINE_INTERVAL, TIMESTAMP);

        assertEquals(test.getClass(), candlestick.getClass());
    }
}