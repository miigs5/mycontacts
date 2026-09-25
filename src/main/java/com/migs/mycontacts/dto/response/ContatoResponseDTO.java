package com.migs.mycontacts.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ContatoResponseDTO(
    UUID id,

    @Schema(description = "Nome do contato.", example = "João Fulano")
    String nome,

    @Schema(description = "Telefone para contato.", example = "85912345678")
    String telefone,

    @Schema(description = "E-mail do contato.", example = "joaofulano@email.com")
    String email,

    @Schema(description = "Descrição simples.", example = "João gosta de morango.")
    String descricao
) {}
