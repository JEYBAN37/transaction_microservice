package com.example.transaction.transactiontest;

import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.domain.model.exception.SupplyException;
import com.example.transaction.domain.port.repository.SaleRepository;
import com.example.transaction.domain.port.services.ArticleService;
import com.example.transaction.domain.service.BuyArticleServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;


class BuyArticleServicesTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private BuyArticleServices buyArticleServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testExecute_EmptyArticleList_ThrowsException() {
        // Given
        List<ArticleSaleCommand> articleSaleCommands = Collections.emptyList();

        // When & Then
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            buyArticleServices.execute(articleSaleCommands, 1L);
        });

        assertEquals("List articles sale empty", exception.getErrorMessage());
    }

    @Test
    void testExecute_ZeroOrNegativeQuantity_ThrowsException() {
        // Given
        List<ArticleSaleCommand> articleSaleCommands = List.of(
                new ArticleSaleCommand(1L, 0)
        );

        // When & Then
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            buyArticleServices.execute(articleSaleCommands, 1L);
        });

        assertEquals("List articles sale empty", exception.getErrorMessage());
    }


}
