package com.migs.mycontacts.controller.interfaces;

import com.migs.mycontacts.dto.ExceptionDTO;
import com.migs.mycontacts.dto.request.usuario.AtualizarUsuarioRequestDTO;
import com.migs.mycontacts.dto.request.usuario.UsuarioCadastroRequestDTO;
import com.migs.mycontacts.dto.response.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuário")
public interface UsuarioControllerInterface {
    @Operation(summary = "Busca os dados do usuário autenticado.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário encontrado."
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuário não está autenticado.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        )
    })
    ResponseEntity<UsuarioResponseDTO> buscarUsuario();

    @Operation(summary = "Altera os campos do usuário autenticado.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário alterado com sucesso."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Usuário não alterado por campos inválidos.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuário não está autenticado.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        )
    })
    ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@Valid @RequestBody AtualizarUsuarioRequestDTO dto);

    @Operation(summary = "Remove a conta do usuário autenticado.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Usuário deletado com sucesso."
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuário não autenticado."
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado."
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        )
    })
    ResponseEntity<Void> deletarUsuario();
}
