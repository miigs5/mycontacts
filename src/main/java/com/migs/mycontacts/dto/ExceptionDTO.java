package com.migs.mycontacts.dto;

public record ExceptionDTO(
    Integer status,
    String titulo,
    String mensagem,
    Object detalhes,
    String data
) {}
