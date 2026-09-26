package com.migs.mycontacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ExceptionDTO(
    @Schema(description = "Status HTTP.", example = "400")
    Integer status,

    @Schema(description = "Título do erro.", example = "Dados Inválidos")
    String titulo,

    @Schema(description = "Descrição geral.", example = "Usuário ou senha incorretos.")
    String mensagem,

    @Schema(description = "Especificação sobre a localidade do erro.", example = "null")
    Object detalhes,

    @Schema(description = "Horário do erro.", example = "2000-01-01T00:00:00.000000000")
    String data
) {}
