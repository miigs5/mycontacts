package com.migs.mycontacts.dto.request.contato;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record AtualizarContatoRequestDTO(
    @Schema(description = "Nome do contato.", example = "João Fulano")
    String nome,

    @Schema(description = "Telefone para contato.", example = "85912345678")
    @Pattern(regexp = "^[0-9]{10,12}$")
    String telefone,

    @Schema(description = "E-mail do contato.", example = "joaofulano@email.com")
    @Email
    String email,

    @Schema(description = "Descrição simples.", example = "João gosta de morango.")
    String descricao
) {}
