package com.example.transaction.infrastructure.adapter.http.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

        private String jwtToken;

        public void setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
        }

        @Override
        public void apply(RequestTemplate requestTemplate) {
            if (jwtToken != null && !jwtToken.isEmpty()) {
                requestTemplate.header("Authorization", "Bearer " + jwtToken);
            }
        }
}

