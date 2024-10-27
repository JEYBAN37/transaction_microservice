package com.example.transaction.domain.service;

import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.domain.model.entity.Sale;
import com.example.transaction.domain.model.entity.salesvalidates.State;
import com.example.transaction.domain.model.exception.SupplyException;
import com.example.transaction.domain.port.repository.SaleRepository;
import com.example.transaction.domain.port.services.ArticleService;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.transaction.domain.model.constant.SupplyConstant.LIST_ARTICLES_SALE_EMPTY;
import static com.example.transaction.domain.model.constant.SupplyConstant.TRASACTION_FAILED;


@AllArgsConstructor
public class BuyArticleServices {
     private SaleRepository saleRepository;
     private ArticleService articleService;

    public List<Sale> execute(List<ArticleSaleCommand> articleSaleCommands, Long idUser) {
        if (articleSaleCommands == null || articleSaleCommands.isEmpty()) {
            throw new SupplyException(LIST_ARTICLES_SALE_EMPTY);
        }

        if (articleSaleCommands.stream().anyMatch(command -> command.getQuantity() <= 0)) {
            throw new SupplyException(LIST_ARTICLES_SALE_EMPTY);
        }

        List<Sale> sales = new ArrayList<>();

        try {
            List<Sale> serviceArticles = articleService.getArticlesOnlyIds(articleSaleCommands);

            if (serviceArticles.isEmpty()) {
                throw new SupplyException(TRASACTION_FAILED);
            }

            Map<Long, Integer> idQuantityMap = serviceArticles.stream()
                    .collect(Collectors.toMap(Sale::getIdArticle, Sale::getQuantity));

            List<Sale> validSales = serviceArticles.stream()
                    .filter(sale -> articleSaleCommands.stream()
                            .anyMatch(command -> command.getId().equals(sale.getIdArticle()) &&
                                    command.getQuantity() <= idQuantityMap.get(sale.getIdArticle())))
                    .map(sale -> {
                        BigDecimal total = sale.getPrice().multiply(BigDecimal.valueOf(sale.getQuantity()));
                        return new Sale(sale.getIdArticle(), idUser, sale.getQuantity(), State.AVAILABLE, total);
                    })
                    .toList();

            List<ArticleSaleCommand> cancelCommands = articleSaleCommands.stream()
                    .filter(command -> !idQuantityMap.containsKey(command.getId()) ||
                            command.getQuantity() > idQuantityMap.getOrDefault(command.getId(), 0))
                    .toList();

            List<Sale> failedSales = createSales(cancelCommands, idUser, State.FAILED);

            // Agregar todas las ventas
            sales.addAll(validSales);
            sales.addAll(failedSales);

        } catch (Exception e) {
            sales.addAll(createSales(articleSaleCommands, idUser, State.FAILED));
        }

        return saleRepository.create(sales);
    }

    private List<Sale> createSales(List<ArticleSaleCommand> buyArticleCommands, Long idUser,State state) {
        return buyArticleCommands.stream()
                .map(buyArticleCommand -> Sale
                        .canceled(
                                buyArticleCommand.getId(),
                                idUser,
                                buyArticleCommand.getQuantity(),
                                state
                        ))
                .toList();
    }
}
