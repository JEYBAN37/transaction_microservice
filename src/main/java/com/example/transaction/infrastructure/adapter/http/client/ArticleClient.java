package com.example.transaction.infrastructure.adapter.http.client;


import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.infrastructure.adapter.json.SaleJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@FeignClient(name = "stock-microservice", url = "http://localhost:8085")
public interface ArticleClient {

    @PostMapping("/secure/sales/articles/")
    Optional<List<SaleJson>> articles(@RequestBody List<ArticleSaleCommand> articleSaleCommands);


}
