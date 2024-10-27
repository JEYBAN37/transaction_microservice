package com.example.transaction.transactiontest;

import com.example.transaction.application.command.BuyArticleHandler;
import com.example.transaction.application.command.FeignClientInterceptorHandler;
import com.example.transaction.application.command.JwtHandler;
import com.example.transaction.application.mapper.SaleDtoMapper;
import com.example.transaction.domain.model.dto.SaleDto;
import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.domain.model.entity.Sale;
import com.example.transaction.domain.model.entity.salesvalidates.State;
import com.example.transaction.domain.model.exception.SupplyException;
import com.example.transaction.domain.service.BuyArticleServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuyArticleHandlerTest {

    @Mock
    private FeignClientInterceptorHandler feignClientInterceptorHandler;

    @Mock
    private BuyArticleServices buyArticleServices;

    @Mock
    private SaleDtoMapper saleDtoMapper;

    @Mock
    private JwtHandler jwtHandler;

    @InjectMocks
    private BuyArticleHandler buyArticleHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessfulTransaction() {
        // Given
        String token = "Bearer some.jwt.token";
        Long userId = 1L;
        List<ArticleSaleCommand> articleCommands = List.of(
                new ArticleSaleCommand(1L, 2),
                new ArticleSaleCommand(2L, 1)
        );

        List<Sale> sales = List.of(
                new Sale(1L, userId, 2, State.AVAILABLE, BigDecimal.valueOf(200)),
                new Sale(2L, userId, 1, State.AVAILABLE, BigDecimal.valueOf(50))
        );

        List<SaleDto> expectedDtos = sales.stream()
                .map(sale -> new SaleDto(sale.getId(), sale.getQuantity(),State.AVAILABLE, sale.getPrice(), sale.getDate()))
                .collect(Collectors.toList());

        // Mock behavior
        when(jwtHandler.getUserIdFromToken("some.jwt.token")).thenReturn(userId.intValue());
        when(buyArticleServices.execute(articleCommands, userId)).thenReturn(sales);

        for (int i = 0; i < sales.size(); i++) {
            when(saleDtoMapper.toDto(sales.get(i))).thenReturn(expectedDtos.get(i));
        }

        // When
        List<SaleDto> result = buyArticleHandler.execute(token, articleCommands);

        // Then
        assertEquals(expectedDtos, result);
        verify(feignClientInterceptorHandler).sendToken("some.jwt.token");
        verify(buyArticleServices).execute(articleCommands, userId);
        verify(saleDtoMapper, times(sales.size())).toDto(any(Sale.class));
    }

    @Test
    void testExecute_WithInvalidToken_ThrowsException() {
        // Given
        String token = "Bearer invalid.token";
        List<ArticleSaleCommand> articleCommands = List.of(new ArticleSaleCommand(1L, 2));

        // Mock behavior for invalid token
        when(jwtHandler.getUserIdFromToken("invalid.token")).thenThrow(new RuntimeException("Invalid token"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buyArticleHandler.execute(token, articleCommands);
        });

        assertEquals("Invalid token", exception.getMessage());
        verify(feignClientInterceptorHandler, never()).sendToken(anyString());
        verify(buyArticleServices, never()).execute(anyList(), anyLong());
    }


    @Test
    void testExecute_WithPartialTransaction() {
        // Given
        String token = "Bearer some.jwt.token";
        Long userId = 1L;
        List<ArticleSaleCommand> articleCommands = List.of(
                new ArticleSaleCommand(1L, 5),
                new ArticleSaleCommand(2L, 1)
        );

        List<Sale> sales = List.of(
                new Sale(1L, userId, 5, State.FAILED, BigDecimal.ZERO), // Partial failed
                new Sale(2L, userId, 1, State.AVAILABLE, BigDecimal.valueOf(50))
        );

        List<SaleDto> expectedDtos = sales.stream()
                .map(sale -> new SaleDto(sale.getId(), sale.getQuantity(),State.AVAILABLE, sale.getPrice(), sale.getDate()))
                .collect(Collectors.toList());

        // Mock behavior
        when(jwtHandler.getUserIdFromToken("some.jwt.token")).thenReturn(userId.intValue());
        when(buyArticleServices.execute(articleCommands, userId)).thenReturn(sales);

        for (int i = 0; i < sales.size(); i++) {
            when(saleDtoMapper.toDto(sales.get(i))).thenReturn(expectedDtos.get(i));
        }

        // When
        List<SaleDto> result = buyArticleHandler.execute(token, articleCommands);

        // Then
        assertEquals(expectedDtos, result);
        verify(feignClientInterceptorHandler).sendToken("some.jwt.token");
        verify(buyArticleServices).execute(articleCommands, userId);
        verify(saleDtoMapper, times(sales.size())).toDto(any(Sale.class));
    }
}
