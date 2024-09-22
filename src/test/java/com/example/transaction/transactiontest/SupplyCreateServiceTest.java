package com.example.transaction.transactiontest;

import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.model.exception.SupplyException;
import com.example.transaction.domain.port.dao.SupplyDao;
import com.example.transaction.domain.port.repository.SupplyRepository;
import com.example.transaction.domain.service.SupplyCreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


class SupplyCreateServiceTest {

    private SupplyCreateService supplyCreateService;
    private SupplyRepository supplyRepository;
    private SupplyDao supplyDao;

    @BeforeEach
    public void setUp() {
        supplyRepository = mock(SupplyRepository.class);
        supplyDao = mock(SupplyDao.class);
        supplyCreateService = new SupplyCreateService(supplyRepository, supplyDao);
    }

    @Test
     void testExecute_whenUserAlreadyExists_ShouldThrowException() {
        //arrange
        SupplyCreateCommand createCommand = new SupplyCreateCommand();
        createCommand.setId(1L);
        createCommand.setName("John");
        createCommand.setLastName("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");
        createCommand.setRole(Role.ADMIN);


        when(supplyDao.idExist(createCommand.getId())).thenReturn(true);
//act y assert
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            supplyCreateService.execute(createCommand,Role.ADMIN);
        });

        assertEquals("Supply Exist", exception.getErrorMessage());
    }
    @Test
     void whenIdExist_shouldThrowUserException() {
        //arrange
        when(supplyDao.idExist(anyLong())).thenReturn(true);

        SupplyCreateCommand createCommand = new SupplyCreateCommand();
        createCommand.setId(1L);
        createCommand.setName("John");
        createCommand.setLastName("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");
        createCommand.setRole(Role.ADMIN);

//act
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            supplyCreateService.execute(createCommand,Role.ADMIN);
        });

        String expectedMessage = "Supply Exist";
        String actualMessage = exception.getErrorMessage();
//assert
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
     void testExecute_whenNameAlreadyExists_ShouldThrowException() {
        // arrange
        SupplyCreateCommand createCommand = new SupplyCreateCommand();
        createCommand.setName("existingUser");

        when(supplyDao.emailExist(createCommand.getEmail())).thenReturn(true);
        // act
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            supplyCreateService.execute(createCommand,Role.ADMIN);
        });
        // assert
        assertEquals("Supply Exist", exception.getErrorMessage());
    }

    @Test
     void testExecute_RoleIsNull_ShouldThrowException() {
        // arrange
        SupplyCreateCommand createCommand = new SupplyCreateCommand();
        createCommand.setRole(null);
        // act
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            supplyCreateService.execute(createCommand,Role.ADMIN);
        });
        // assert
        assertEquals("Role Not Found", exception.getErrorMessage());
    }

    @Test
     void testExecute_whenRoleNotFound_shouldThrowException() {
        // arrange
        SupplyCreateCommand createCommand = new SupplyCreateCommand();
        createCommand.setId(1L);
        createCommand.setName("John");
        createCommand.setLastName("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");


        when(supplyDao.getByRole(createCommand.getRole())).thenReturn(null);
        // act
        SupplyException exception = assertThrows(SupplyException.class, () -> {
            supplyCreateService.execute(createCommand,Role.ADMIN);
        });
        // assert
        assertEquals("Role Not Found", exception.getErrorMessage());
    }

    @Test
     void testExecute_whenValidUser_shouldCreateUser() {
        // arrange

        SupplyCreateCommand createCommand = new SupplyCreateCommand(
                1L,"John","Doe","12345678","+573177722509" ,LocalDate.of(1995, 5, 15),
                "john.doe@example.com", "securePassword123@",Role.ADMIN
        );

        Role role =  Role.ADMIN;
        when(supplyDao.getByRole(createCommand.getRole())).thenReturn(role);

        Supply supply = new Supply();
        when(supplyRepository.create(any(Supply.class))).thenReturn(supply);

        // act
        Supply createdSupply = supplyCreateService.execute(createCommand,Role.ADMIN);

        // assert
        assertNotNull(createdSupply);
        verify(supplyRepository, times(1)).create(any(Supply.class));
    }
}
