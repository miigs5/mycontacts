package com.migs.mycontacts.dto.request.contato;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CriarContatoRequestDTO(
    @Schema(description = "Nome do contato.", example = "João Fulano")
    @NotBlank(message = "Nome nao pode ser vazio.")
    String nome,

    @Schema(description = "Telefone para contato.", example = "85912345678")
    @NotBlank(message = "Telefone nao pode ser vazio.")
    @Pattern(regexp = "^[0-9]{10,12}$")
    String telefone,

    @Schema(description = "E-mail do contato.", example = "joaofulano@email.com")
    @Email
    String email,

    @Schema(description = "Descrição simples.", example = "João gosta de morango.")
    String descricao
) {}
