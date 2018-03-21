package com.exchange.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ExchangeFallBackFactory implements FallbackFactory<ExchangeClient> {

    @Override
    public ExchangeClient create(Throwable throwable) {
        return new ExchangeClientFallback(throwable);
    }

}
