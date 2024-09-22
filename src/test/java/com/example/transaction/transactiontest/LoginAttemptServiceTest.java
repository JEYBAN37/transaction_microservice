package com.example.transaction.transactiontest;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.port.dao.SupplyDao;
import com.example.transaction.domain.port.repository.SupplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginAttemptServiceTest {

    private SupplyDao supplyDao;
    private SupplyRepository supplyRepository;
    private LoginAttemptService loginAttemptService;

    @BeforeEach
    void setUp() {
        supplyDao = mock(SupplyDao.class);
        supplyRepository = mock(SupplyRepository.class);
        loginAttemptService = new LoginAttemptService(supplyDao, supplyRepository);
    }

    @Test
    void testLoginSucceeded() {
        String email = "test@example.com";
        Supply supply = new Supply();
        supply.setFails(2);
        supply.setLocked(true);

        when(supplyDao.getUser(email)).thenReturn(supply);

        loginAttemptService.loginSucceeded(email);

        assertEquals(0, supply.getFails());
        assertFalse(supply.isLocked());
        assertNull(supply.getLockTime());
        verify(supplyRepository).update(supply);
    }

    @Test
    void testLoginFailed() {
        String email = "test@example.com";
        Supply supply = new Supply();
        supply.setFails(1);
        supply.setLocked(false);

        when(supplyDao.getUser(email)).thenReturn(supply);

        loginAttemptService.loginFailed(email);

        assertEquals(2, supply.getFails());
        assertFalse(supply.isLocked());
        verify(supplyRepository).update(supply);
    }

    @Test
    void testLoginFailedMaxAttempts() {
        String email = "test@example.com";
        Supply supply = new Supply();
        supply.setFails(2);
        supply.setLocked(false);

        when(supplyDao.getUser(email)).thenReturn(supply);

        loginAttemptService.loginFailed(email);

        assertTrue(supply.isLocked());
        assertNotNull(supply.getLockTime());
        verify(supplyRepository).update(supply);
    }

    @Test
    void testIsBlockedWhenLocked() {
        String email = "test@example.com";
        Supply supply = new Supply();
        supply.setLocked(true);
        supply.setLockTime(new Timestamp(System.currentTimeMillis()));

        when(supplyDao.getUser(email)).thenReturn(supply);

        assertTrue(loginAttemptService.isBlocked(email));
    }

    @Test
    void testIsBlockedWhenNotLocked() {
        String email = "test@example.com";
        Supply supply = new Supply();
        supply.setLocked(false);

        when(supplyDao.getUser(email)).thenReturn(supply);

        assertFalse(loginAttemptService.isBlocked(email));
    }

    @Test
    void testIsBlockedWhenLockExpired() {
        String email = "test@example.com";
        Supply supply = new Supply();
        supply.setLocked(true);
        supply.setLockTime(new Timestamp(System.currentTimeMillis() - (16 * 60 * 1000))); // 16 minutos atrás

        when(supplyDao.getUser(email)).thenReturn(supply);

        boolean isBlocked = loginAttemptService.isBlocked(email);

        assertFalse(isBlocked);
        assertFalse(supply.isLocked());
        assertEquals(0, supply.getFails());
        assertNull(supply.getLockTime());
        verify(supplyRepository).update(supply);
    }
}
