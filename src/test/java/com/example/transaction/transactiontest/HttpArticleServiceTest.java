package com.example.transaction.transactiontest;

import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.domain.model.entity.Sale;
import com.example.transaction.domain.model.entity.salesvalidates.State;
import com.example.transaction.infrastructure.adapter.http.HttpArticleService;
import com.example.transaction.infrastructure.adapter.http.client.ArticleClient;
import com.example.transaction.infrastructure.adapter.json.BrandJson;
import com.example.transaction.infrastructure.adapter.json.CategoriesJson;
import com.example.transaction.infrastructure.adapter.json.SaleJson;
import com.example.transaction.infrastructure.adapter.mapper.SaleDboMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HttpArticleServiceTest {

    @Mock
    private ArticleClient articleClient;

    @Mock
    private SaleDboMapper articleDboMapper;

    @InjectMocks
    private HttpArticleService httpArticleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetArticlesOnlyIds_WithEmptyResponse() {
        // Given
        List<ArticleSaleCommand> ids = List.of(
                new ArticleSaleCommand(1L, 2),
                new ArticleSaleCommand(2L, 3)
        );

        when(articleClient.articles(ids)).thenReturn(Optional.empty());

        // When
        List<Sale> result = httpArticleService.getArticlesOnlyIds(ids);

        // Then
        assertEquals(List.of(), result); // Expected empty list
        verify(articleClient).articles(ids);
        verify(articleDboMapper, never()).toDomainJson(any(SaleJson.class), any(State.class), anyInt());
    }

    @Test
    void testGetArticlesOnlyIds_WithPartialResponse() {
        // Given
        List<ArticleSaleCommand> ids = List.of(
                new ArticleSaleCommand(1L, 2),
                new ArticleSaleCommand(3L, 1)  // ID 3 not present in SaleJson list
        );

        BrandJson brands = new BrandJson();

        List<SaleJson> saleJsons = List.of(

                new SaleJson(1L,5,"xxxxx",brands, BigDecimal.valueOf(100.0),"nulll",List.of(new CategoriesJson())
                ));

        List<Sale> expectedSales = List.of(
                new Sale(1L, 1L, 2, State.AVAILABLE, BigDecimal.valueOf(100.0))
        );

        when(articleClient.articles(ids)).thenReturn(Optional.of(saleJsons));
        when(articleDboMapper.toDomainJson(saleJsons.get(0), State.AVAILABLE, 2)).thenReturn(expectedSales.get(0));

        // When
        List<Sale> result = httpArticleService.getArticlesOnlyIds(ids);

        // Then
        assertEquals(expectedSales, result);
        verify(articleClient).articles(ids);
        verify(articleDboMapper).toDomainJson(saleJsons.get(0), State.AVAILABLE, 2);
        verify(articleDboMapper, never()).toDomainJson(any(SaleJson.class), any(State.class), eq(1));
    }
}
