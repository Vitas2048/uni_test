package com.unibell.uni_test.controller;

import com.unibell.uni_test.core.exception.ApplicationException;
import com.unibell.uni_test.core.exception.ValidationException;
import com.unibell.uni_test.core.message.ClientDto;
import com.unibell.uni_test.core.message.Contact;
import com.unibell.uni_test.core.message.ErrorResponse;
import com.unibell.uni_test.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "clients", description = "clients API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Add new client")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "new Client added"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ClientDto> addNew(@Valid@RequestBody ClientDto clientDto, BindingResult result) {
        if (result.hasErrors()){
            throw new ValidationException(result);
        }
        return ResponseEntity.ok(clientService.saveByClientDto(clientDto));
    }

    @Operation(summary = "Add new contacts to current client")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Added new contacts"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failure"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found"
            )
    })
    @PutMapping
    public ResponseEntity<ClientDto> addContacts(@Valid@RequestBody ClientDto clientDto, BindingResult result)
            throws ApplicationException {
        if (result.hasErrors()){
            throw new ValidationException(result);
        }
        var client = clientService.findByName(clientDto.getName());
        clientService.addNewContacts(clientDto, client);
        return ResponseEntity.ok(clientService.getClientDtoByClient(client));
    }

    @Operation(summary = "Get contacts by client name")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found"
            )
    })
    @GetMapping("/contacts")
    public ResponseEntity<Contact> getContact(@RequestParam(value = "name") String name) throws ApplicationException {
        return ResponseEntity.ok(clientService.getContactByName(name));
    }

    @Operation(summary = "get contacts by type from client by name")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failure"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found"
            )
    })
    @GetMapping("/contactType")
    public ResponseEntity<List<String>> getByTypeContact(@RequestParam(value = "name") String name,
                                                         @RequestParam(value = "type") String type)
            throws ApplicationException {
        return ResponseEntity.ok(clientService.getContactByType(name, type));
    }

    @Operation(summary = "Get All clients")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful"
            )
    })
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        return ResponseEntity.ok(clientService.getAllClientsDto());
    }

    @Operation(summary = "Get client by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Added new contacts"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable int id) throws ApplicationException {
        var client = clientService.findById(id);
        return ResponseEntity.ok(clientService.getClientDtoByClient(client));
    }

}
