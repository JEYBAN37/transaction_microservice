package com.example.transaction.transactiontest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.transaction.application.mapper.SupplyDtoMapper;
import com.example.transaction.domain.model.dto.SupplyDto;
import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.model.exception.SupplyException;
import com.example.transaction.domain.service.SupplyCreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SupplyAuxCreateHandlerTest {

    @Mock
    private SupplyCreateService supplyCreateService;

    @Mock
    private SupplyDtoMapper supplyDtoMapper;

    @InjectMocks
    private UserAuxCreateHandler userAuxCreateHandler;

    private SupplyCreateCommand createCommand;
    private SupplyDto supplyDto;
    private Supply supply;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        createCommand = new SupplyCreateCommand();
        createCommand.setName("Jane");
        createCommand.setLastName("Doe");
        createCommand.setDni("87654321");
        createCommand.setTelephone("+573177722508");
        createCommand.setEmail("jane.doe@example.com");
        createCommand.setPassword("securePassword123");

        supplyDto = new SupplyDto();
        supplyDto.setName("Jane");
        supplyDto.setLastName("Doe");
        supplyDto.setEmail("jane.doe@example.com");
    }

    @Test
    void test_execute_whenUserCreatedSuccessfully_shouldReturnUserDto() {
        // Arrange
        when(supplyCreateService.execute(createCommand,Role.ADMIN)).thenReturn(supply);
        when(supplyDtoMapper.toDto(supply)).thenReturn(supplyDto);

        // Act
        SupplyDto result = userAuxCreateHandler.execute(createCommand);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.getName());
        assertEquals("Doe", result.getLastName());
        assertEquals("jane.doe@example.com", result.getEmail());
        assertEquals(Role.AUX_BODEGA, createCommand.getRole()); // Verify role is set to AUX_BODEGA
        verify(supplyCreateService).execute(createCommand,Role.ADMIN);
        verify(supplyDtoMapper).toDto(supply);
    }

    @Test
    void test_execute_whenUserCreationFails_shouldThrowException() {
        // Arrange
        when(supplyCreateService.execute(createCommand,Role.ADMIN)).thenThrow(new SupplyException("Supply creation failed"));

        // Act & Assert
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            userAuxCreateHandler.execute(createCommand);
        });

        // Assert
        assertEquals("Supply creation failed", exception.getErrorMessage());
        verify(supplyCreateService).execute(createCommand,Role.ADMIN);
    }

}
