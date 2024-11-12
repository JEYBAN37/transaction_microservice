package com.example.transaction.infrastructure.rest.controller;

import com.example.transaction.application.command.BuyArticleHandler;
import com.example.transaction.application.command.SupplyCreateHandler;
import com.example.transaction.domain.model.dto.SaleDto;
import com.example.transaction.domain.model.dto.SupplyDto;
import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Tag(name ="ArticleSaleCommand Command Controller")
public class SupplyCommandController {
    private final SupplyCreateHandler supplyCreateHandler;
    private final BuyArticleHandler buyArticleHandler;

    @Operation(summary = "Create Supplies Aux Bodega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply created", content = @Content),
            @ApiResponse(responseCode = "409", content = @Content)
    })
    @PostMapping("company/addSupply/")
    public List<SupplyDto> addSupply (@RequestBody  List<SupplyCreateCommand>  createCommand){
        return supplyCreateHandler.execute(createCommand);
    }

    @Operation(summary = "Create Supplies Aux Bodega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply created", content = @Content),
            @ApiResponse(responseCode = "409", content = @Content)
    })
    @PostMapping("secure/sale/articles/")
    public List<SaleDto> saleArticles (@RequestBody  List<ArticleSaleCommand>  createCommands,
                                       @RequestHeader("Authorization") String token){
        return buyArticleHandler.execute(token,createCommands);
    }
}
