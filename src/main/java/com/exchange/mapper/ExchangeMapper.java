package com.exchange.mapper;

import com.exchange.dto.ExchangeRateDto;
import com.exchange.entity.ExchangeEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExchangeMapper {

    public ExchangeEntity map(ExchangeRateDto exchangeRateDto) {
        if (exchangeRateDto == null) {
            return null;
        }

        String symbol = exchangeRateDto.getRates().entrySet().iterator().next().getKey();
        BigDecimal rate = exchangeRateDto.getRates().entrySet().iterator().next().getValue();

        return ExchangeEntity.builder().base(exchangeRateDto.getBase()).
                symbol(symbol).rate(rate).
                createdTime(new Date()).build();
    }

    public List<ExchangeRateDto> map(List<ExchangeEntity> exchangeRateResponse) {
        return exchangeRateResponse.stream().map(e -> new ExchangeRateDto(e.getBase(),
                e.getCreatedTime(), e.getSymbol(), e.getRate())).collect(Collectors.toList());
    }

}
