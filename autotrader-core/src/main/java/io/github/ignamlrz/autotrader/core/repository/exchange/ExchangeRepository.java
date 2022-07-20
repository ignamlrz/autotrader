package io.github.ignamlrz.autotrader.core.repository.exchange;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends MongoRepository<Exchange, ExchangeSupplier> {

}
