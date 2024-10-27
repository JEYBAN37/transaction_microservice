package com.example.transaction.infrastructure.adapter.http;

import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.domain.model.entity.Sale;
import com.example.transaction.domain.model.entity.salesvalidates.State;
import com.example.transaction.domain.port.services.ArticleService;
import com.example.transaction.infrastructure.adapter.http.client.ArticleClient;
import com.example.transaction.infrastructure.adapter.json.SaleJson;
import com.example.transaction.infrastructure.adapter.mapper.SaleDboMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class HttpArticleService implements ArticleService {
    private final ArticleClient articleClient;
    private final SaleDboMapper articleDboMapper;
    @Override
    public List<Sale> getArticlesOnlyIds(List<ArticleSaleCommand> ids, Long id) {

        Map<Long, Integer> idQuantityMap = ids.stream()
                .collect(Collectors.toMap(ArticleSaleCommand::getId, ArticleSaleCommand::getQuantity));

        Optional<List<SaleJson>> optionalArticles = articleClient.articles(ids);

        return optionalArticles.map(saleJsons -> saleJsons.
                stream()
                .filter(saleJson -> idQuantityMap.containsKey(saleJson.getId()))
                .map(saleJson -> {
                    int quantity = idQuantityMap.get(saleJson.getId());
                    return articleDboMapper.toDomainJson(saleJson, id, State.AVAILABLE, quantity);
                })
                .toList()).orElseGet(List::of);

    }

}
