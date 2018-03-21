package com.exchange.job;

import com.exchange.dto.ExchangeRateDto;
import com.exchange.entity.ExchangeChangeEntity;
import com.exchange.exception.ExternalResourceNotFoundException;
import com.exchange.mapper.ExchangeRateMapper;
import com.exchange.repository.ExchangeRateRepository;
import com.exchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class ExchangeScheduler {

    private final ExchangeRateService exchangeRateService;

    private final ExchangeRateMapper exchangeRateMapper;

    private final ExchangeRateRepository repository;

    @Value("${exchange.rate.base}")
    private String base;

    @Value("${exchange.rate.symbol}")
    private String symbol;

    @Autowired
    public ExchangeScheduler(ExchangeRateService exchangeRateService, ExchangeRateMapper exchangeRateMapper,
                             ExchangeRateRepository repository) {
        this.exchangeRateService = exchangeRateService;
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
        ExchangeRateDto exchangeRateDto = exchangeRateService.callExchangeApi(base, symbol);
        ExchangeChangeEntity entity = exchangeRateMapper.map(exchangeRateDto);

        if (ObjectUtils.isEmpty(entity)) {
            throw new ExternalResourceNotFoundException("External api is down");
        }

        repository.save(entity);
    }

}
