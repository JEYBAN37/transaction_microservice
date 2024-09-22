package com.example.transaction.infrastructure.rest.controller;

import com.example.transaction.application.command.SupplyCreateHandler;
import com.example.transaction.domain.model.dto.SupplyDto;
import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
@AllArgsConstructor
@Tag(name ="Article Command Controller")
public class SupplyCommandController {
    private final SupplyCreateHandler supplyCreateHandler;

    @Operation(summary = "Create Supply Aux Bodega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply created", content = @Content),
            @ApiResponse(responseCode = "409", content = @Content)
    })
    @PostMapping("addSupply/")
    public SupplyDto addSupply (@RequestBody SupplyCreateCommand createCommand){
        return supplyCreateHandler.execute(createCommand);
    }
}
