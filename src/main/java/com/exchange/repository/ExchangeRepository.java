package com.exchange.repository;

import com.exchange.entity.ExchangeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExchangeRepository extends CrudRepository<ExchangeEntity, Long> {

    List<ExchangeEntity> findFirstByBaseAndSymbolOrderByCreatedTime(String base, String symbol);

    List<ExchangeEntity> findAllByCreatedTimeBetween(Date endDate, Date startDate);
}
