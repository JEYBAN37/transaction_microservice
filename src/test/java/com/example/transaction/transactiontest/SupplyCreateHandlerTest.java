package com.example.transaction.transactiontest;

import com.example.transaction.application.command.SupplyCreateHandler;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplyCreateHandlerTest {

    @Mock
    private SupplyCreateService supplyCreateService;

    @Mock
    private SupplyDtoMapper supplyDtoMapper;

    @Mock
    private JwtPort jwtService;

    @InjectMocks
    private SupplyCreateHandler supplyCreateHandler;

    private SupplyCreateCommand createCommand;
    private Supply supply;
    private SupplyDto supplyDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        createCommand = new SupplyCreateCommand();
        createCommand.setName("John");
        createCommand.setLastName("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");
        createCommand.setRole(Role.ADMIN);

        supply = new Supply();
        supplyDto = new SupplyDto();
    }

    // Test positivo: Usuario creado correctamente
    @Test
    void test_whenCreateAndReturnUserDto_shouldReturnAuthenticationResponse() {
        // Arrange
        when(supplyCreateService.execute(createCommand,Role.ADMIN)).thenReturn(supply);
        when(supplyDtoMapper.toDto(supply)).thenReturn(supplyDto);
        when(jwtService.generateToken(supplyDto)).thenReturn("jwt_token");

        // Act
        AuthenticationResponse result = supplyCreateHandler.execute(createCommand);

        // Assert
        assertNotNull(result);
        assertEquals("jwt_token", result.getToken());
        verify(supplyCreateService).execute(createCommand,Role.ADMIN);
        verify(supplyDtoMapper).toDto(supply);
        verify(jwtService).generateToken(supplyDto);
    }

    // Test negativo: Usuario ya existe (lanzar excepción)
    @Test
    void test_whenUserAlreadyExists_shouldThrowUserException() {
        // Arrange
        when(supplyCreateService.execute(createCommand,Role.ADMIN)).thenThrow(new SupplyException("Supply already exists"));

        // Act & Assert
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            supplyCreateHandler.execute(createCommand);
        });
        assertEquals("Supply already exists", exception.getErrorMessage());
        verify(supplyCreateService).execute(createCommand,Role.ADMIN);
        verifyNoMoreInteractions(jwtService); // El jwtService no debe ser llamado si falla la creación
    }

    // Test negativo: Error al generar el token
    @Test
    void test_whenTokenGenerationFails_shouldThrowException() {
        // Arrange
        when(supplyCreateService.execute(createCommand,Role.AUX_BODEGA)).thenReturn(supply);
        when(supplyDtoMapper.toDto(supply)).thenReturn(supplyDto);
        when(jwtService.generateToken(supplyDto)).thenThrow(new RuntimeException("Token generation failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            supplyCreateHandler.execute(createCommand);
        });
        assertEquals("Token generation failed", exception.getMessage());
        verify(supplyCreateService).execute(createCommand,Role.AUX_BODEGA);
        verify(jwtService).generateToken(supplyDto);
    }

    // Test negativo: Error durante el mapeo de Supply a SupplyDto
    @Test
    void test_whenUserDtoMappingFails_shouldThrowException() {
        // Arrange
        when(supplyCreateService.execute(createCommand,Role.ADMIN)).thenReturn(supply);
        when(supplyDtoMapper.toDto(supply)).thenThrow(new RuntimeException("SupplyDto mapping failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            supplyCreateHandler.execute(createCommand);
        });
        assertEquals("SupplyDto mapping failed", exception.getMessage());
        verify(supplyCreateService).execute(createCommand,Role.ADMIN);
        verify(supplyDtoMapper).toDto(supply);
        verifyNoMoreInteractions(jwtService); // No debe llamar a jwtService si falla el mapeo
    }

    // Test positivo: Usuario creado con rol diferente
    @Test
    void test_whenUserCreatedWithDifferentRole_shouldReturnAuthenticationResponse() {
        // Arrange
        createCommand.setRole(Role.CLIENT); // Cambiar el rol
        when(supplyCreateService.execute(createCommand,Role.ADMIN)).thenReturn(supply);
        when(supplyDtoMapper.toDto(supply)).thenReturn(supplyDto);
        when(jwtService.generateToken(supplyDto)).thenReturn("jwt_token_user");

        // Act
        AuthenticationResponse result = supplyCreateHandler.execute(createCommand);

        // Assert
        assertNotNull(result);
        assertEquals("jwt_token_user", result.getToken());
        verify(supplyCreateService).execute(createCommand,Role.ADMIN);
        verify(supplyDtoMapper).toDto(supply);
        verify(jwtService).generateToken(supplyDto);
    }

}
