package io.github.ignamlrz.autotrader.core.repository.candlestick;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickRepository extends CrudRepository<Candlestick, ObjectId> {

}
