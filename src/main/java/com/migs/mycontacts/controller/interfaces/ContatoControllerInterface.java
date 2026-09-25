package com.migs.mycontacts.controller.interfaces;

import com.migs.mycontacts.dto.ExceptionDTO;
import com.migs.mycontacts.dto.request.contato.AtualizarContatoRequestDTO;
import com.migs.mycontacts.dto.request.contato.CriarContatoRequestDTO;
import com.migs.mycontacts.dto.response.ContatoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Tag(name = "Contatos")
public interface ContatoControllerInterface {

    @Operation(summary = "Cadastra um contato.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Contato criado com sucesso."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Contato não criado por campos inválidos.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuário não está autenticado.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Contato não criado por contato similar existente.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        )
    })
    ResponseEntity<ContatoResponseDTO> salvarContato(
        @Valid @RequestBody CriarContatoRequestDTO dto,
        UriComponentsBuilder uriBuilder
    );

    @Operation(summary = "Lista todos os contatos ou retorna uma lista vazia.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Os contatos foram listados."
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
    ResponseEntity<List<ContatoResponseDTO>> buscarTodosContatos();

    @Operation(summary = "Busca algum contato com o ID repassado.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Contato encontrado."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "ID inválido.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuário não está autenticado.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Contato não encontrado.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        )
    })
    ResponseEntity<ContatoResponseDTO> buscarContatoPorId(@PathVariable UUID id);

    @Operation(summary = "Lista contatos com o nome repassado ou retorna uma lista vazia.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Os contatos foram listados."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Nome inválido.",
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
    ResponseEntity<List<ContatoResponseDTO>> buscarContatoPorNome(
        @PathVariable @RequestParam(required = false) String nome
    );

    @Operation(summary = "Lista contatos com o telefone repassado ou retorna uma lista vazia.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Os contatos foram listados."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Telefone inválido.",
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
    ResponseEntity<List<ContatoResponseDTO>> buscarContatoPorTelefone(
        @PathVariable @RequestParam(required = false) String telefone
    );

    @Operation(summary = "Altera os campos de um contato existente.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Contato atualizado com sucesso."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Campos inválidos.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuário não autenticado.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "ID de contato inexistente.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Contato não atualizado por conta de contato similar existente.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        )
    })
    ResponseEntity<ContatoResponseDTO> atualizarContato(
        @PathVariable UUID id,
        @Valid @RequestBody AtualizarContatoRequestDTO requestDto
    );

    @Operation(summary = "Remove um contato salvo.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Contato removido."
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Usuário não autenticado.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "ID de contato inexistente.",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor",
            content = @Content(schema = @Schema(implementation = ExceptionDTO.class))
        )
    })
    ResponseEntity<Void> removerContato(@PathVariable UUID id);
}
