package com.abc.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
public class TokenFilter   implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("ServerGatewayFilter filter ");

        ServerHttpRequest request = exchange.getRequest();

        //这里从url参数里获取，也可以从cookie,header里获取
        String token = request.getQueryParams().getFirst("token");
        if(StringUtils.isEmpty(token)){

            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);

            final ServerHttpResponse response = exchange.getResponse();
            byte[] bytes = "{\"code\":\"99999\",\"message\":\"非法访问,没有检测到token~~~~~~\"}".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            response.getHeaders().set("aaa", "bbb");//设置header
            log.info("TokenFilter拦截非法请求，没有检测到token............");
            return response.writeWith(Flux.just(buffer));//设置body

           // return exchange.getResponse().setComplete();
        }

        return chain.filter( exchange );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
