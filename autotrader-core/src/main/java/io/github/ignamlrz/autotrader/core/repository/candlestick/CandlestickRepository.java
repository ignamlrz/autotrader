package io.github.ignamlrz.autotrader.core.repository.candlestick;

import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface CandlestickRepository extends Repository<Candlestick, ObjectId> {

}
