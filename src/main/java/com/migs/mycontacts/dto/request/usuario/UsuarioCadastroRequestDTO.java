package com.migs.mycontacts.dto.request.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema
public record UsuarioCadastroRequestDTO(

    @Schema(description = "Nome simples para login. Deve iniciar com uma letra ou um sublinhado (\"_\") e conter mais de três caracteres.", example = "usuario123")
    @NotBlank(message = "Login nao deve ser vazio! Deve iniciar com uma letra ou um sublinhado (\"_\") e conter mais de três caracteres.")
    @Pattern(
        regexp = "(?!^[\\d_]+$)^[A-Za-z_]\\w{2,}$",
        message = "Login invalido! Deve iniciar com uma letra ou um sublinhado (\"_\") e conter mais de três caracteres."
    )
    String username,

    @Schema(description = "Senha de acesso. Deve conter de 8 a 20 caracteres com: Uma letra maiuscula, uma letra minuscula, um numero, um simbolo especial e sem repetir um mesmo caractere 4 vezes.", example = "#senhaSuperSegura123")
    @NotBlank(message = "Senha nao deve ser vazia! Deve conter de 8 a 20 caracteres com: Uma letra maiuscula, uma letra minuscula, um numero, um simbolo especial e sem repetir um mesmo caractere 4 vezes.")
    @Pattern(
        regexp = "^(?!.*(.)\\1{3,})(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)(?=.*?\\W)\\S{8,20}$",
        message = "Senha invalida! Deve conter de 8 a 20 caracteres com: Uma letra maiuscula, uma letra minuscula, um numero, um simbolo especial e sem repetir um mesmo caractere 4 vezes."
    )
    String password,

    @Schema(description = "Nome para a conta. Deve conter apenas letras e 3 a 30 caracteres.", example = "Usuário")
    @NotBlank(message = "Nome invalido! Deve conter apenas letras e 3 a 30 caracteres.")
    @Pattern(
        regexp = "^[\\p{L}]{3,30}$",
        message = "Nome invalido! Deve conter apenas letras e 3 a 30 caracteres."
    )
    String nome
) {}
