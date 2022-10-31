package com.example.display.api;

import com.example.display.service.RemoteProductInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/displays")
public class DisplayController {

    private final RemoteProductInfoService remoteProductInfoService;

    public DisplayController(RemoteProductInfoService remoteProductInfoService) {
        this.remoteProductInfoService = remoteProductInfoService;
    }

    @GetMapping("/{displayId}")
    public String getDisplayInfo(@PathVariable String displayId) {
//        String productInfo = remoteProductInfoService.getProductInfo("8987");
        String productInfo = getProductInfo();
        return "[displayId = " + displayId + "]" + productInfo;
    }

    private String getProductInfo() {
        return remoteProductInfoService.getProductInfo("3431");
    }
}
