package com.example.transaction.application.command;

import com.example.transaction.application.mapper.SaleDtoMapper;
import com.example.transaction.domain.model.dto.SaleDto;
import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.domain.service.BuyArticleServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BuyArticleHandler {
    private final FeignClientInterceptorHandler feignClientInterceptorHandler;
    private final BuyArticleServices buyArticleServices;
    private final SaleDtoMapper saleDtoMapper;
    private final JwtHandler jwtHandler;

    public List<SaleDto> execute (String token, List<ArticleSaleCommand> articleCommands){
        String cleanToken = token.replace("Bearer ", "").trim();
        Long userId = Long.valueOf(jwtHandler.getUserIdFromToken(cleanToken));
        feignClientInterceptorHandler.sendToken(cleanToken);
       return buyArticleServices.execute(articleCommands,userId)
               .stream()
               .map(saleDtoMapper::toDto)
               .toList();
    }
}
