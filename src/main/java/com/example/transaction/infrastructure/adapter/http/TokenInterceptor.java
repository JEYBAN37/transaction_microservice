package com.example.transaction.infrastructure.adapter.http;

import com.example.transaction.application.interfaces.FeignInterceptorService;
import com.example.transaction.infrastructure.adapter.http.client.FeignClientInterceptor;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class TokenInterceptor implements FeignInterceptorService {

    private final FeignClientInterceptor feignClientInterceptor;

    @Override
    public void injectToken(String token) {
        feignClientInterceptor.setJwtToken(token);
    }
}
