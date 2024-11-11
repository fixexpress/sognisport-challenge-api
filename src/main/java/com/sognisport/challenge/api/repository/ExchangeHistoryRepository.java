package com.sognisport.challenge.api.repository;

import com.sognisport.challenge.api.model.ExchangeHistory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ExchangeHistoryRepository implements PanacheRepository<ExchangeHistory> {

    // Busca o histórico de conversões baseado na moeda base e moeda alvo
    public Optional<ExchangeHistory> findByBaseCodeAndTargetCode(String baseCode, String targetCode) {
        return find("baseCode = ?1 and targetCode = ?2", baseCode, targetCode).firstResultOptional();
    }

    // Busca o histórico de conversões baseado na moeda base
    public List<ExchangeHistory> findByBaseCode(String baseCode) {
        return list("baseCode", baseCode);
    }

    // Busca o histórico de conversões baseado na moeda alvo
    public List<ExchangeHistory> findByTargetCode(String targetCode) {
        return list("targetCode", targetCode);
    }

    // Busca o histórico de conversões de acordo com a data da última atualização
    public List<ExchangeHistory> findByTimeLastUpdateGreaterThanEqualAndTimeNextUpdateLessThanEqual(
            long timeLastUpdate, long timeNextUpdate) {
        return list("timeLastUpdate >= ?1 and timeNextUpdate <= ?2", timeLastUpdate, timeNextUpdate);
    }
}
