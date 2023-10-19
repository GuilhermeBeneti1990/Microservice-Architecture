package com.beneti.productapi.modules.sales.client;

import com.beneti.productapi.modules.sales.dtos.SalesProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "salesClient", contextId = "salesClient", url = "${api-config.services.sales}")
public interface SalesClient {

    @GetMapping("products/{productId}")
    Optional<SalesProductResponse> findSalesByProductId(@PathVariable Integer productId);

}
