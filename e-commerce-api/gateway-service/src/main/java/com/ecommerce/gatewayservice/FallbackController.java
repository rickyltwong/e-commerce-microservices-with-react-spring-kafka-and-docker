package com.ecommerce.gatewayservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fallback")
public class FallbackController {

    @GetMapping
    public Mono<String> fallback() {
        return Mono.just("The service is unavailable");
    }
}
