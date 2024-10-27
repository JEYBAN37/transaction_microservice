package com.example.transaction.domain.service;

import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.domain.model.entity.Sale;
import com.example.transaction.domain.model.entity.salesvalidates.State;
import com.example.transaction.domain.model.exception.SupplyException;
import com.example.transaction.domain.port.repository.SaleRepository;
import com.example.transaction.domain.port.services.ArticleService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.transaction.domain.model.constant.SupplyConstant.LIST_ARTICLES_SALE_EMPTY;
import static com.example.transaction.domain.model.constant.SupplyConstant.TRASACTION_FAILED;


@AllArgsConstructor
public class BuyArticleServices {
     private SaleRepository saleRepository;
     private ArticleService articleService;

    public List<Sale> execute(List<ArticleSaleCommand> articleSaleCommands, Long idUser) {

        if(articleSaleCommands == null || articleSaleCommands.isEmpty()){
            throw new SupplyException(LIST_ARTICLES_SALE_EMPTY);
        }

        if (articleSaleCommands.stream().anyMatch(buyArticleCommand -> buyArticleCommand.getQuantity() <= 0)) {
            throw new SupplyException(LIST_ARTICLES_SALE_EMPTY);
        }

        List<Sale> sales = new ArrayList<>();


        try {

            List<Sale> serviceArticle = articleService.getArticlesOnlyIds(articleSaleCommands,idUser);

            if (serviceArticle.isEmpty()) {
                throw new SupplyException(TRASACTION_FAILED);
            }

            List<Sale> validate = serviceArticle.stream()
                    .filter(Objects::nonNull)
                    .toList();

            Map<Long, Integer> idQuantityMap = serviceArticle.stream()
                    .collect(Collectors.toMap(Sale::getIdArticle, Sale::getQuantity));

            List<ArticleSaleCommand> cancel = articleSaleCommands.stream().filter(articleSaleCommand ->
                    !idQuantityMap.containsKey(articleSaleCommand.getId()) ||
                idQuantityMap.get(articleSaleCommand.getId()) < articleSaleCommand.getQuantity())
                .toList();


            List<Sale> cancelSale = createSales(cancel, idUser);

            sales.addAll(validate);
            sales.addAll(cancelSale);

        } catch (Exception e) {
            sales.addAll(createSales(articleSaleCommands, idUser));
        }

        return saleRepository.create(sales);
    }

    private List<Sale> createSales(List<ArticleSaleCommand> buyArticleCommands, Long idUser) {
        return buyArticleCommands.stream()
                .map(buyArticleCommand -> Sale
                        .canceled(
                                buyArticleCommand.getId(),
                                idUser,
                                buyArticleCommand.getQuantity(),
                                State.FAILED
                        ))
                .toList();
    }
}
