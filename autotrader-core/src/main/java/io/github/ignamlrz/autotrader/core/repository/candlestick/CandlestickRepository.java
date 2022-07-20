package io.github.ignamlrz.autotrader.core.repository.candlestick;

import io.github.ignamlrz.autotrader.core.repository.symbol.Symbol;
import io.github.ignamlrz.autotrader.core.utilities.time.Interval;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickRepository extends MongoRepository<Candlestick, ObjectId> {
    Candlestick findCandlestickBySymbolAndIntervalAndTimestamp(Symbol s, Interval i, long timestamp);
}
