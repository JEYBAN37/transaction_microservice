package com.example.transaction.transactiontest;

import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.infrastructure.adapter.entity.SupplyEntity;
import com.example.transaction.infrastructure.adapter.jpa.SupplySpringJpaAdapterRepository;
import com.example.transaction.infrastructure.adapter.jpa.dao.SupplyH2Dao;
import com.example.transaction.infrastructure.adapter.mapper.SupplyDboMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class SupplyH2DaoTest {

    @Mock
    private SupplySpringJpaAdapterRepository supplySpringJpaAdapterRepository;

    @Mock
    private SupplyDboMapper supplyDboMapper;

    @InjectMocks
    private SupplyH2Dao supplyH2Dao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void getSupply_ShouldReturnSupply_WhenSupplyExists() {
        // Arrange
        Long supplyId = 1L;
        SupplyEntity supplyEntity = new SupplyEntity(); // Suponiendo que SupplyEntity tiene un constructor vacío
        Supply supply = new Supply(); // Suponiendo que Supply tiene un constructor vacío

        when(supplySpringJpaAdapterRepository.findById(supplyId)).thenReturn(Optional.of(supplyEntity));
        when(supplyDboMapper.toDomain(supplyEntity)).thenReturn(supply);

        // Act
        Supply result = supplyH2Dao.getSupply(supplyId);

        // Assert
        assertNotNull(result);
        assertEquals(supply, result);
        verify(supplySpringJpaAdapterRepository, times(1)).findById(supplyId);
        verify(supplyDboMapper, times(1)).toDomain(supplyEntity);
    }

    @Test
     void getSupply_ShouldReturnNull_WhenSupplyDoesNotExist() {
        // Arrange
        Long supplyId = 1L;

        when(supplySpringJpaAdapterRepository.findById(supplyId)).thenReturn(Optional.empty());

        // Act
        Supply result = supplyH2Dao.getSupply(supplyId);

        // Assert
        assertNull(result);
        verify(supplySpringJpaAdapterRepository, times(1)).findById(supplyId);
        verify(supplyDboMapper, never()).toDomain(any(SupplyEntity.class)); // Verificar que no se llama toDomain
    }

    @Test
     void idExist_ShouldReturnTrue_WhenIdExists() {
        // Arrange
        Long existingId = 1L;

        when(supplySpringJpaAdapterRepository.existsById(existingId)).thenReturn(true);

        // Act
        boolean result = supplyH2Dao.idExist(existingId);

        // Assert
        assertTrue(result);
        verify(supplySpringJpaAdapterRepository, times(1)).existsById(existingId);
    }

    @Test
     void idExist_ShouldReturnFalse_WhenIdDoesNotExist() {
        // Arrange
        Long nonExistingId = 1L;

        when(supplySpringJpaAdapterRepository.existsById(nonExistingId)).thenReturn(false);

        // Act
        boolean result = supplyH2Dao.idExist(nonExistingId);

        // Assert
        assertFalse(result);
        verify(supplySpringJpaAdapterRepository, times(1)).existsById(nonExistingId);
    }

    @Test
     void dateExist_ShouldReturnFalse() {
        // Act
        boolean result = supplyH2Dao.dateExist(Timestamp.valueOf("2024-01-01 00:00:00"));

        // Assert
        assertFalse(result);
    }
}
