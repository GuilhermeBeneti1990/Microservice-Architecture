package com.beneti.productapi.modules.product.rabbitmq;

import com.beneti.productapi.modules.product.dtos.ProductStockDTO;
import com.beneti.productapi.modules.product.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${api-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO product) {
        productService.updateProductStock(product);
    }

}
