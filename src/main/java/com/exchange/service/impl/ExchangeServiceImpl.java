package com.exchange.service.impl;

import com.exchange.client.ExchangeClient;
import com.exchange.dto.ExchangeRateDto;
import com.exchange.entity.ExchangeEntity;
import com.exchange.exception.ExternalResourceNotFoundException;
import com.exchange.helper.DateHelper;
import com.exchange.mapper.ExchangeMapper;
import com.exchange.repository.ExchangeRepository;
import com.exchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeClient exchangeRateClient;
    private final ExchangeRepository repository;
    private final ExchangeMapper mapper;
    private final DateHelper dateHelper;

    @Autowired
    public ExchangeServiceImpl(ExchangeClient exchangeRateClient, ExchangeRepository repository,
                               ExchangeMapper mapper, DateHelper dateHelper) {
        this.exchangeRateClient = exchangeRateClient;
        this.repository = repository;
        this.mapper = mapper;
        this.dateHelper = dateHelper;
    }

    /**
     * This  method calls the external api and get the results
     * @param base      holds the value rate to be converted from
     * @param symbol    holds the value rate to be converted into
     * @return ExchangeRateDto holds the results
     * @throws ExternalResourceNotFoundException when the external api is down
     */
    @Override
    public ExchangeRateDto callExchangeApi(String base, String symbol) throws ExternalResourceNotFoundException {
        return exchangeRateClient.getCurrencyClient(base, symbol);
    }

    /**
     * This  method get the exchange rates which are stored in database
     * @param base      holds the value rate to be converted from
     * @param symbol    holds the value rate to be converted into
     * @param startDate start date of the records that to be queried
     * @param endDate   end date of the records that to be queried
     * @return List<ExchangeRateDto> holds the results
     */
    @Override
    public List<ExchangeRateDto> getCurrency(String base, String symbol, Date startDate, Date endDate) {
        List<ExchangeEntity> exchangeRateResponse;

        if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
            exchangeRateResponse = repository.findFirstByBaseAndSymbolOrderByCreatedTime(base, symbol);
        } else {
            exchangeRateResponse = repository.findAllByCreatedTimeBetween(startDate, dateHelper.getEndOfDay(endDate));
        }

        return mapper.map(exchangeRateResponse);
    }

}
