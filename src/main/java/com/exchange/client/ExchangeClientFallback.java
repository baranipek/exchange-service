package com.exchange.client;

import com.exchange.dto.ExchangeRateDto;
import com.exchange.exception.ExternalResourceNotFoundException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;


@Slf4j
class ExchangeClientFallback implements ExchangeClient {

    private final Throwable cause;

    ExchangeClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ExchangeRateDto getCurrencyClient(String base, String symbols)  {

        if (cause instanceof FeignException && ((FeignException) cause).status() == HttpStatus.NOT_FOUND.value()) {
            log.error("Exchange api is down");
            throw new ExternalResourceNotFoundException("Exchange api is down");
        }

        return null;
    }

}
