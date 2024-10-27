package com.example.transaction.transactiontest;

import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.model.exception.SupplyException;
import com.example.transaction.domain.port.dao.SupplyDao;
import com.example.transaction.domain.port.publisher.SupplyPublisher;
import com.example.transaction.domain.port.repository.SupplyRepository;
import com.example.transaction.domain.service.SupplyCreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class SupplyCreateServiceTest {

    @Mock
    private SupplyRepository supplyRepository;

    @Mock
    private SupplyPublisher supplyPublisher;

    @Mock
    private SupplyDao supplyDao;

    @InjectMocks
    private SupplyCreateService supplyCreateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void execute_ShouldThrowException_WhenListIsEmpty() {
        // Arrange
        List<SupplyCreateCommand> createCommands = Collections.emptyList();

        // Act & Assert
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            supplyCreateService.execute(createCommands);
        });

        assertEquals("List Empty", exception.getErrorMessage());
    }

    @Test
     void execute_ShouldCreateSupplies_WhenCommandsAreValid() {
        // Arrange
        List<SupplyCreateCommand> createCommands = new ArrayList<>();
        SupplyCreateCommand command1 = new SupplyCreateCommand(1L,1, 10, "ESTATE" ,new BigDecimal(100.0));
        SupplyCreateCommand command2 = new SupplyCreateCommand(2L,2, 20,"ESTATE" , new BigDecimal(200.0));
        createCommands.add(command1);
        createCommands.add(command2);

        Supply supply1 = new Supply();
        Supply supply2 = new Supply();

        when(supplyDao.idExist(command1.getId())).thenReturn(false);
        when(supplyDao.idExist(command2.getId())).thenReturn(false);
        when(supplyRepository.create(any())).thenReturn(supply1, supply2);

        // Act
        List<Supply> result = supplyCreateService.execute(createCommands);

        // Assert
        assertEquals(2, result.size());
        verify(supplyRepository, times(2)).create(any());
        verify(supplyPublisher, times(2)).publishMessage(any());
    }

    @Test
     void execute_ShouldThrowException_WhenSupplyExists() {
        // Arrange
        SupplyCreateCommand command = new SupplyCreateCommand(1L,1, 10, "ESTATE" ,new BigDecimal(100.0));
        when(supplyDao.idExist(command.getId())).thenReturn(true); // Simular que el suministro ya existe

        // Act & Assert
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            supplyCreateService.execute(List.of(command));
        });

        assertEquals("Supply Exist", exception.getErrorMessage());
    }

    @Test
     void execute_ShouldCreateSupplies_WhenCommandsAreValidf() {
        // Arrange
        SupplyCreateCommand command1 = new SupplyCreateCommand(1L,1, 10, "ESTATE" ,new BigDecimal(100.0));
        SupplyCreateCommand command2 =new SupplyCreateCommand(2L,2, 10, "ESTATE" ,new BigDecimal(100.0));

        // Simulaciones de retorno de métodos
        when(supplyDao.idExist(command1.getId())).thenReturn(false);
        when(supplyDao.idExist(command2.getId())).thenReturn(false);

        // Simulación de la creación de suministros
        when(supplyRepository.create(any(Supply.class))).thenReturn(new Supply());

        // Act
        List<Supply> result = supplyCreateService.execute(List.of(command1, command2));

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(supplyPublisher, times(2)).publishMessage(any());
    }

    @Test
     void execute_ShouldPublishSupplyMessage_WhenSupplyIsCreated() {
        // Arrange
        SupplyCreateCommand command = new SupplyCreateCommand(1L,1, 10, "ESTATE" ,new BigDecimal(100.0));
        when(supplyDao.idExist(command.getId())).thenReturn(false);
        when(supplyRepository.create(any(Supply.class))).thenReturn(new Supply()); // Simulación correcta

        // Act
        supplyCreateService.execute(List.of(command));

        // Assert
        verify(supplyPublisher, times(1)).publishMessage(any());
    }
}
