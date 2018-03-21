package com.exchange.job;

import com.exchange.dto.ExchangeRateDto;
import com.exchange.entity.ExchangeEntity;
import com.exchange.exception.ExternalResourceNotFoundException;
import com.exchange.mapper.ExchangeMapper;
import com.exchange.repository.ExchangeRepository;
import com.exchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class ExchangeScheduler {

    private final ExchangeService exchangeService;

    private final ExchangeMapper exchangeRateMapper;

    private final ExchangeRepository repository;

    @Value("${exchange.rate.base}")
    private String base;

    @Value("${exchange.rate.symbol}")
    private String symbol;

    @Autowired
    public ExchangeScheduler(ExchangeService exchangeService, ExchangeMapper exchangeRateMapper,
                             ExchangeRepository repository) {
        this.exchangeService = exchangeService;
        this.exchangeRateMapper = exchangeRateMapper;
        this.repository = repository;
    }


    /**
     * This scheduler method calls the http://api.fixer.io/latest external api, with the interval
     * time is given in config file .
     * @throws ExternalResourceNotFoundException when the external api is down
     */

    @Scheduled(cron = "${exchange.rate.check.interval}")
    public void scheduleExchangeRate() {
        ExchangeRateDto exchangeRateDto = exchangeService.callExchangeApi(base, symbol);
        ExchangeEntity entity = exchangeRateMapper.map(exchangeRateDto);

        if (ObjectUtils.isEmpty(entity)) {
            throw new ExternalResourceNotFoundException("External api is down");
        }

        repository.save(entity);
    }

}
