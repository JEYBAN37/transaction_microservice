package com.example.transaction.transactiontest;

import com.example.transaction.domain.model.dto.SupplyDto;
import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.infrastructure.rest.controller.SupplyCommandController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SupplyCommandControllerTest {

    @InjectMocks
    private SupplyCommandController supplyCommandController;

    @Mock
    private UserAuxCreateHandler userAuxCreateHandler;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(supplyCommandController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_createAux_whenUserIsCreatedSuccessfully_shouldReturn200() throws Exception {
        // Arrange
        SupplyCreateCommand createCommand = new SupplyCreateCommand();
        // Set required fields on createCommand
        createCommand.setName("John");
        createCommand.setLastName("Doe");
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");

        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setEmail("john.doe@example.com"); // Set appropriate fields on supplyDto

        // Mock the behavior of userAuxCreateHandler
        when(userAuxCreateHandler.execute(any(SupplyCreateCommand.class))).thenReturn(supplyDto);

        // Act & Assert
        mockMvc.perform(post("/users/register/aux_bodega")
                        .contentType(MediaType.APPLICATION_JSON) // Ensure content type is set
                        .content(objectMapper.writeValueAsString(createCommand)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Expect JSON response
                .andExpect(jsonPath("$.email").value(supplyDto.getEmail())); // Adjust based on SupplyDto fields
    }

}
