package com.example.display.service;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteProductInfoServiceFallbackFactory implements FallbackFactory<RemoteProductInfoService> {
    @Override
    public RemoteProductInfoService create(Throwable cause) {
        System.out.println("cause = " + cause);
        return productId -> "[this product is sold out]";
    }
}
