package com.abc.config;

import com.abc.filter.TokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GatewayConfig {

    /**
     * 配置了一个id为route-name的路由规则
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route("path_route_eiletxie",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei")).build();
        routes.route("path_route_game",
                r -> r.path("/game")
                        .uri("http://news.baidu.com/game")).build();

        routes.route("path_route_timeout",
                r -> r.path("/payment/feign/timeou**")
                        //.uri("http://localhost:8001")).build();   //负载均衡
                          .uri("lb://CLOUD-PAYMENT-SERVICE")).build();



        routes.route("route_product",
                r ->r.path("/product/**")
                                .filters(
                                        f -> f.filters(new TokenFilter())
                                       // f -> f.stripPrefix(1).filters(new TokenFilter())
                                )
                                .uri("lb://PRODUCT/product")
                ).build();

        return routes.build();


    }
}