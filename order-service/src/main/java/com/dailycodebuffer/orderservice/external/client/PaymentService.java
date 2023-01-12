package com.dailycodebuffer.orderservice.external.client;

import com.dailycodebuffer.orderservice.exception.OrderServiceCustomException;
import com.dailycodebuffer.orderservice.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

    @PostMapping
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    default void fallback(Exception ex) {
        throw new OrderServiceCustomException("Payment Service is down!", "UNAVAILABLE", 500);
    }

}
