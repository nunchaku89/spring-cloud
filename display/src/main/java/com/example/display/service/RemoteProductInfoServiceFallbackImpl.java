package com.example.display.service;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteProductInfoServiceFallbackImpl implements RemoteProductInfoService {
    @Override
    public String getProductInfo(String productId) {
        return "[ This porduct is sold out ]";
    }
}
