package com.migs.mycontacts.dto.request.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginRequestDTO(

    @Schema(description = "Nome simples para login. Deve iniciar com uma letra ou um sublinhado (\"_\") e conter mais de três caracteres.", example = "usuario123")
    @NotBlank(message = "Login ou senha invalidos.")
    String username,

    @Schema(description = "Senha de acesso. Deve conter de 8 a 20 caracteres com: Uma letra maiuscula, uma letra minuscula, um numero, um simbolo especial e sem repetir um mesmo caractere 4 vezes.", example = "#senhaSuperSegura123")
    @NotBlank(message = "Login ou senha invalidos.")
    String password
) {}
