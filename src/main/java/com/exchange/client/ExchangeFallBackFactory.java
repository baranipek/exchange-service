package com.exchange.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ExchangeFallBackFactory implements FallbackFactory<ExchangeRateClient> {

    @Override
    public ExchangeRateClient create(Throwable throwable) {
        return new ExchangeRateClientFallback(throwable);
    }

}
