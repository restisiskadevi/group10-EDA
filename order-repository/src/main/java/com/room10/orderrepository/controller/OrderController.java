package com.room10.orderrepository.controller;

import com.room10.basedomains.dto.*;
import com.room10.orderrepository.kafka.OrderProducer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequestMapping("order")
@RestController
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/sent-order")
    @Transactional
    public ResponseEntity<String> sentOrder(@RequestBody Order order) {
        String uuid = UUID.randomUUID().toString();
        order.setOrderId(uuid);

        OrderEvent orderEvent = OrderEvent.builder()
                .message("Order Status with ID : "+uuid+" is in pending status")
                .status("PENDING")
                .order(order)
                .build();
        orderProducer.sendMessage(orderEvent);
        return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<Boolean> order(@RequestBody Order order) {
        callAPI(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static void callAPI(Order order) {
        String requestParam = "?skuCode="+order.getOrderId()+"&stock="+order.getQty();
        final String uri = "http://localhost:8001/item/item-taken" + requestParam;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, null);

        System.out.println("API is success");
    }
}
