package com.example.transaction.transactiontest;

import com.example.transaction.application.command.SupplyCreateHandler;
import com.example.transaction.infrastructure.rest.controller.SupplyCommandController;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



class SupplyCommandControllerTest {

    @InjectMocks
    private SupplyCommandController supplyCommandController;

    @Mock
    private SupplyCreateHandler supplyCreateHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(supplyCommandController).build();
    }

}
