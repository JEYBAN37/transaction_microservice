package com.example.transaction.transactiontest;

import com.example.transaction.domain.model.entity.Sale;
import com.example.transaction.domain.model.entity.salesvalidates.State;
import com.example.transaction.infrastructure.adapter.entity.SaleEntity;
import com.example.transaction.infrastructure.adapter.jpa.SaleSpringJpaAdapterRepository;
import com.example.transaction.infrastructure.adapter.jpa.respository.SaleH2Repository;
import com.example.transaction.infrastructure.adapter.mapper.SaleDboMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SaleH2RepositoryTest {

    @Mock
    private SaleSpringJpaAdapterRepository saleSpringJpaAdapterRepository;

    @Mock
    private SaleDboMapper saleDboMapper;

    @InjectMocks
    private SaleH2Repository saleH2Repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_WithValidSales() {
        // Given
        List<Sale> requestSales = List.of(
                new Sale(1L, 1L, 2, State.AVAILABLE, BigDecimal.valueOf(100.0)),
                new Sale(2L, 1L, 3, State.AVAILABLE, BigDecimal.valueOf(200.0))
        );

        List<SaleEntity> entitiesToSave = List.of(
                new SaleEntity(1L, 1L, 2L,5, State.AVAILABLE, BigDecimal.valueOf(100.0),LocalDateTime.now()),
                new SaleEntity(2L, 1L, 3L,5, State.AVAILABLE, BigDecimal.valueOf(200.0),LocalDateTime.now())
        );

        List<SaleEntity> savedEntities = List.of(
                new SaleEntity(1L, 1L, 2L,5, State.AVAILABLE, BigDecimal.valueOf(100.0),LocalDateTime.now()),
                new SaleEntity(2L, 1L, 3L,5, State.AVAILABLE, BigDecimal.valueOf(200.0),LocalDateTime.now())
        );

        List<Sale> expectedSales = List.of(
                new Sale(1L, 1L, 2, State.AVAILABLE, BigDecimal.valueOf(100.0)),
                new Sale(2L, 1L, 3, State.AVAILABLE, BigDecimal.valueOf(200.0))
        );

        when(saleDboMapper.toDatabase(requestSales.get(0))).thenReturn(entitiesToSave.get(0));
        when(saleDboMapper.toDatabase(requestSales.get(1))).thenReturn(entitiesToSave.get(1));
        when(saleSpringJpaAdapterRepository.saveAll(entitiesToSave)).thenReturn(savedEntities);
        when(saleDboMapper.toDomain(savedEntities.get(0))).thenReturn(expectedSales.get(0));
        when(saleDboMapper.toDomain(savedEntities.get(1))).thenReturn(expectedSales.get(1));

        // When
        List<Sale> result = saleH2Repository.create(requestSales);

        // Then
        assertEquals(expectedSales, result);
        verify(saleDboMapper).toDatabase(requestSales.get(0));
        verify(saleDboMapper).toDatabase(requestSales.get(1));
        verify(saleSpringJpaAdapterRepository).saveAll(entitiesToSave);
        verify(saleDboMapper).toDomain(savedEntities.get(0));
        verify(saleDboMapper).toDomain(savedEntities.get(1));
    }

}
