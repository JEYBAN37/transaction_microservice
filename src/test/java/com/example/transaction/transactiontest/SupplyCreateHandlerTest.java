package com.example.transaction.transactiontest;

import com.example.transaction.application.command.SupplyCreateHandler;
import com.example.transaction.application.mapper.SupplyDtoMapper;
import com.example.transaction.domain.model.dto.SupplyDto;
import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.service.SupplyCreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class SupplyCreateHandlerTest {

    @Mock
    private SupplyCreateService supplyCreateService;

    @Mock
    private SupplyDtoMapper supplyDtoMapper;

    @InjectMocks
    private SupplyCreateHandler supplyCreateHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void execute_ShouldReturnSupplyDtoList_WhenCreateCommandsAreValid() {
        // Arrange
        SupplyCreateCommand command1 = new SupplyCreateCommand(1L,1, 10, "ESTATE" ,new BigDecimal(100.0));
        SupplyCreateCommand command2 = new SupplyCreateCommand(2L,2, 10, "ESTATE" ,new BigDecimal(100.0));
        Supply supply1 = new Supply(); // Suponiendo que Supply tiene un constructor vacío
        Supply supply2 = new Supply();

        // Simulación de retorno de servicios
        when(supplyCreateService.execute(List.of(command1, command2))).thenReturn(List.of(supply1, supply2));
        when(supplyDtoMapper.toDto(supply1)).thenReturn(new SupplyDto());
        when(supplyDtoMapper.toDto(supply2)).thenReturn(new SupplyDto());

        // Act
        List<SupplyDto> result = supplyCreateHandler.execute(List.of(command1, command2));

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(supplyCreateService, times(1)).execute(List.of(command1, command2));
        verify(supplyDtoMapper, times(2)).toDto(any(Supply.class)); // Verificar que toDto fue llamado
    }

    @Test
     void execute_ShouldHandleEmptySupplyList() {
        // Arrange
        List<SupplyCreateCommand> createCommands = List.of();

        // Act
        List<SupplyDto> result = supplyCreateHandler.execute(createCommands);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(supplyCreateService, times(1)).execute(createCommands);
    }

}
