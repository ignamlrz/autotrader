package io.github.ignamlrz.autotrader.core.repository.symbol;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepository extends MongoRepository<Symbol, String> {
}
