package com.example.transaction.transactiontest;
import com.example.transaction.application.command.SupplyCreateHandler;
import com.example.transaction.domain.model.dto.AuthenticationRequest;
import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SupplySecurityControllerTest {

    @InjectMocks
    private UserSecurityController userSecurityController;

    @Mock
    private SupplyCreateHandler supplyCreateHandler;

    @Mock
    private UserLogin userLogin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password123");

        AuthenticationResponse expectedResponse = new AuthenticationResponse("token123");

        when(userLogin.execute(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<AuthenticationResponse> response = userSecurityController.login(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(userLogin, times(1)).execute(request);
    }

    @Test
    void testRegister_Success() {
        // Arrange
        SupplyCreateCommand createCommand = new SupplyCreateCommand();
        createCommand.setEmail("test@example.com");
        createCommand.setPassword("Password123");

        AuthenticationResponse expectedResponse = new AuthenticationResponse("token123");

        when(supplyCreateHandler.execute(createCommand)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<AuthenticationResponse> response = userSecurityController.registerAdmin(createCommand);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(supplyCreateHandler, times(1)).execute(createCommand);
    }
}
