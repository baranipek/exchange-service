package com.exchange.service;


import com.exchange.dto.ExchangeRateDto;
import com.exchange.exception.ExternalResourceNotFoundException;

import java.util.Date;
import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRateDto> getCurrency(String base, String symbol, Date startDate, Date endDate);

    ExchangeRateDto callExchangeApi(String base, String symbol) throws ExternalResourceNotFoundException;

}
