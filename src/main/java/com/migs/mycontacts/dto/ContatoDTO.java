package com.migs.mycontacts.dto;

import java.util.List;

public record ContatoDTO(
    String id,
    String nome,
    String telefone,
    String email,
    String descricao
) {}
