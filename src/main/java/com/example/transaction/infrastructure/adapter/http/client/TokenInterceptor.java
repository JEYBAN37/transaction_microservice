package com.example.transaction.infrastructure.adapter.http.client;

import com.example.transaction.application.interfaces.FeignInterceptorService;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class TokenInterceptor implements FeignInterceptorService {

    private final FeignClientInterceptor feignClientInterceptor;

    @Override
    public void injectToken(String token) {
        feignClientInterceptor.setJwtToken(token);
    }
}
