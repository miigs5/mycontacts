package com.migs.mycontacts.dto;

import java.util.List;

public record ContatoDTO(
    String id,
    String nome,
    List<String> telefones,
    List<String> emails,
    String descricao
) {
    public List<String> telefones() {
        return telefones == null ? List.of() : telefones;
    }

    public List<String> emails() {
        return emails == null ? List.of() : emails;
    }
}
