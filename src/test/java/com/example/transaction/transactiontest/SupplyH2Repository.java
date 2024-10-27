package com.example.transaction.transactiontest;

import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.infrastructure.adapter.entity.SupplyEntity;
import com.example.transaction.infrastructure.adapter.jpa.SupplySpringJpaAdapterRepository;
import com.example.transaction.infrastructure.adapter.jpa.respository.SupplyH2Repository;
import com.example.transaction.infrastructure.adapter.mapper.SupplyDboMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplyH2RepositoryTest {

    @Mock
    private SupplySpringJpaAdapterRepository supplySpringJpaAdapterRepository;

    @Mock
    private SupplyDboMapper supplyDboMapper;

    @InjectMocks
    private SupplyH2Repository supplyH2Repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void create_ShouldReturnSupply_WhenSupplyIsCreated() {
        // Arrange
        Supply supplyToCreate = new Supply(1L,2,5,"e",new BigDecimal(54));
        SupplyEntity supplyEntityToSave = new SupplyEntity();
        SupplyEntity savedSupplyEntity = new SupplyEntity();
        Supply expectedSupply = new Supply();

        when(supplyDboMapper.toDatabase(supplyToCreate)).thenReturn(supplyEntityToSave);
        when(supplySpringJpaAdapterRepository.save(supplyEntityToSave)).thenReturn(savedSupplyEntity);
        when(supplyDboMapper.toDomain(savedSupplyEntity)).thenReturn(expectedSupply);

        // Act
        Supply result = supplyH2Repository.create(supplyToCreate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedSupply, result);
        verify(supplyDboMapper, times(1)).toDatabase(supplyToCreate);
        verify(supplySpringJpaAdapterRepository, times(1)).save(supplyEntityToSave);
        verify(supplyDboMapper, times(1)).toDomain(savedSupplyEntity);
    }

    @Test
     void update_ShouldReturnUpdatedSupply_WhenSupplyIsUpdated() {
        // Arrange
        Supply supplyToUpdate = new Supply();
        SupplyEntity supplyEntityToUpdate = new SupplyEntity();
        SupplyEntity updatedSupplyEntity = new SupplyEntity();
        Supply expectedSupply = new Supply();

        when(supplyDboMapper.toDatabase(supplyToUpdate)).thenReturn(supplyEntityToUpdate);
        when(supplySpringJpaAdapterRepository.save(supplyEntityToUpdate)).thenReturn(updatedSupplyEntity);
        when(supplyDboMapper.toDomain(updatedSupplyEntity)).thenReturn(expectedSupply);

        // Act
        Supply result = supplyH2Repository.update(supplyToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedSupply, result);
        verify(supplyDboMapper, times(1)).toDatabase(supplyToUpdate);
        verify(supplySpringJpaAdapterRepository, times(1)).save(supplyEntityToUpdate);
        verify(supplyDboMapper, times(1)).toDomain(updatedSupplyEntity);
    }
}
