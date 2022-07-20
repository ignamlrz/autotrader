package io.github.ignamlrz.autotrader.core.repository.symbol;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolInfoRepository extends CrudRepository<SymbolInfo, String> {
}
