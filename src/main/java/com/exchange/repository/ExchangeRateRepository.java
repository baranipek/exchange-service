package com.exchange.repository;

import com.exchange.entity.ExchangeChangeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeChangeEntity, Long> {

    List<ExchangeChangeEntity> findFirstByBaseAndSymbolOrderByCreatedTime(String base, String symbol);

    List<ExchangeChangeEntity> findAllByCreatedTimeBetween(Date endDate, Date startDate);
}
