package com.ecommerce.gatewayservice;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("products", r -> r.path("/api/products/**")
//                        .filters(f -> f.rewritePath("/api/products/(?<segment>.*)", "/products/${segment}"))
//                        .uri("lb://product-service"))
//                .route("orders", r -> r.path("/api/orders/**")
//                        .filters(f -> f.rewritePath("/api/orders/(?<segment>.*)", "/orders/${segment}"))
//                        .uri("lb://order-service"))
//                .route("admin", r -> r.path("/api/admin/**")
//                        .filters(f -> f.rewritePath("/api/admin/(?<segment>.*)", "/admin/${segment}"))
//                        .uri("lb://admin-service"))
//                .route("images", r -> r.path("/api/images/**")
//                        .filters(f -> f.rewritePath("/api/images/(?<segment>.*)", "/images/${segment}"))
//                        .uri("lb://image-service"))
//                .build();
//    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("products", r -> r.path("/api/products/**")
                        .filters(f -> f.rewritePath("/api/products/(?<remainingPath>.*)", "/api/products/${remainingPath}"))
                        .uri("lb://product-service"))
                .route("orders", r -> r.path("/api/orders/**")
                        .filters(f -> f.rewritePath("/api/orders/(?<remainingPath>.*)", "/api/orders/${remainingPath}"))
                        .uri("lb://order-service"))
                .route("admin", r -> r.path("/api/admin/**")
                        .filters(f -> f.rewritePath("/api/admin/(?<remainingPath>.*)", "/api/admin/${remainingPath}"))
                        .uri("lb://admin-service"))
                .route("images", r -> r.path("/api/images/**")
                        .filters(f -> f.rewritePath("/api/images/(?<remainingPath>.*)", "/api/images/${remainingPath}"))
                        .uri("lb://image-service"))
                .build();
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String originalPath = request.getPath().toString();
            System.out.println("Original incoming request: " + originalPath);

            long startTime = System.currentTimeMillis();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpRequest rewrittenRequest = exchange.getRequest();
                String rewrittenPath = rewrittenRequest.getPath().toString();
                ServerHttpResponse response = exchange.getResponse();
                HttpStatusCode responseStatus = response.getStatusCode();
                long duration = System.currentTimeMillis() - startTime;
                System.out.println("Rewritten request: " + rewrittenPath);
                System.out.println("Outgoing response with status code " + responseStatus + " processed in " + duration + " ms");
            }));
        };
    }
}
