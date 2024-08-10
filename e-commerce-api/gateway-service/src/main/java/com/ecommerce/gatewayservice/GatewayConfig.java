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

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("products", r -> r.path("/api/products/**")
                        .filters(f -> f.rewritePath("/api/products/(?<segment>.*)", "/products/${segment}"))
                        .uri("lb://PRODUCTS-SERVICE"))
                .route("orders", r -> r.path("/api/orders/**")
                        .filters(f -> f.rewritePath("/api/orders/(?<segment>.*)", "/orders/${segment}"))
                        .uri("lb://ORDERS-SERVICE"))
                .route("admin", r -> r.path("/api/admin/**")
                        .filters(f -> f.rewritePath("/api/admin/(?<segment>.*)", "/admin/${segment}"))
                        .uri("lb://ADMIN-SERVICE"))
//                .route("images", r -> r.path("/api/images/**")
//                        .filters(f -> f.rewritePath("/api/images/(?<segment>.*)", "/images/${segment}"))
//                        .uri("lb://IMAGE-SERVICE"))
                .build();
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestMethod = request.getMethod().name();
            String requestPath = request.getPath().toString();
            System.out.println("Incoming request " + requestMethod + " " + requestPath);
            long startTime = System.currentTimeMillis();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                HttpStatusCode responseStatus = response.getStatusCode();
                long duration = System.currentTimeMillis() - startTime;
                System.out.println("Outgoing response with status code " + responseStatus + " processed in " + duration + " ms");
            }));
        };
    }
}
