package com.example.display.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product", fallbackFactory = RemoteProductInfoServiceFallbackFactory.class)
public interface RemoteProductInfoService {
    @GetMapping("/products/{productId}")
    String getProductInfo(@PathVariable String productId);
}
