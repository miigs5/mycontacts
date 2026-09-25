package com.migs.mycontacts.controller.interfaces;

import com.migs.mycontacts.dto.ExceptionDTO;
import com.migs.mycontacts.dto.request.usuario.UsuarioCadastroRequestDTO;
import com.migs.mycontacts.dto.request.usuario.UsuarioLoginRequestDTO;
import com.migs.mycontacts.dto.response.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Autenticação")
public interface AuthControllerInterface {

    @Operation(summary = "Cria uma nova conta.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuario cadastrado com sucesso."
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não cadastrado por campos inválidos.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Usuário não cadastrado por existir usuário com credenciais similares.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        )
    })
    ResponseEntity<UsuarioResponseDTO> cadastrar(
        @Valid @RequestBody UsuarioCadastroRequestDTO dto,
        UriComponentsBuilder uriBuilder
    );

    @Operation(summary = "Autentica usuário por login e senha.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário autenticado com sucesso."
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuário não autenticado por credenciais inválidas.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        )
    })
    ResponseEntity<UsuarioResponseDTO> login(@Valid @RequestBody UsuarioLoginRequestDTO dto);
}
