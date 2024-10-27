package com.example.transaction.transactiontest;

import com.example.transaction.application.command.BuyArticleHandler;
import com.example.transaction.application.command.SupplyCreateHandler;
import com.example.transaction.domain.model.dto.SaleDto;
import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.domain.model.entity.salesvalidates.State;
import com.example.transaction.infrastructure.adapter.securityconfig.JwtClient;
import com.example.transaction.infrastructure.adapter.securityconfig.RoleInterceptor;
import com.example.transaction.infrastructure.rest.controller.SupplyCommandController;
import org.springframework.test.web.servlet.MockMvc;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SupplyCommandController.class)
class SupplyCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupplyCreateHandler supplyCreateHandler;

    @MockBean
    private BuyArticleHandler buyArticleHandler;

    @MockBean
    private JwtClient jwtClient;



    @Test
    void testAddSupply_Failure_WhenSupplyCreateHandlerThrowsException() throws Exception {
        // Arrange
        when(supplyCreateHandler.execute(any(List.class))).thenThrow(new RuntimeException("Supply creation failed"));

        // Act & Assert
        mockMvc.perform(post("/admin/addSupply/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\": 1, \"quantity\": 10}]"))  // reemplaza el contenido con el JSON real de SupplyCreateCommand
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testSaleArticles_Failure_WhenNoAuthorizationHeader() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/admin/sale/articles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\": 1, \"quantity\": 10}]"))  // reemplaza el contenido con el JSON real de ArticleSaleCommand
                .andExpect(status().isUnauthorized());
    }
}

