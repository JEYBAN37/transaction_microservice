package com.example.transaction.transactiontest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.transaction.application.mapper.SupplyDtoMapper;
import com.example.transaction.domain.model.dto.AuthenticationRequest;
import com.example.transaction.domain.model.dto.SupplyDto;

import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.port.dao.SupplyDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

class SupplyLoginTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SupplyDao supplyDao;

    @Mock
    private JwtPort jwtService;

    @Mock
    private SupplyDtoMapper supplyDtoMapper;

    @Mock
    private LoginAttemptService loginAttemptService;

    @InjectMocks
    private UserLogin userLogin;

    private AuthenticationRequest request;
    private SupplyDto supplyDto;
    private Supply supply;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new AuthenticationRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("securePassword123");

        supplyDto = new SupplyDto();
        supplyDto.setEmail("john.doe@example.com");
    }

    @Test
    void test_execute_whenLoginIsSuccessful_shouldReturnAuthenticationResponse() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
        when(supplyDao.getUser(request.getEmail())).thenReturn(supply);
        when(supplyDtoMapper.toDto(supply)).thenReturn(supplyDto);
        when(jwtService.generate(supplyDto)).thenReturn("jwtToken123");

        // Act
        AuthenticationResponse response = userLogin.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken123", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(supplyDao).getUser(request.getEmail());
        verify(supplyDtoMapper).toDto(supply);
        verify(jwtService).generate(supplyDto);
    }

    @Test
    void test_execute_whenAuthenticationFails_shouldThrowException() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Authentication failed") {});

        // Act & Assert
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            userLogin.execute(request);
        });

        // Assert
        assertEquals("Authentication failed", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(supplyDao, never()).getUser(anyString());
    }

}
