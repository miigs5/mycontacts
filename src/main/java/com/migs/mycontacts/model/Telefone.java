package com.migs.mycontacts.model;

import com.migs.mycontacts.exception.ContatoInvalidoException;

public record Telefone(String telefone) {
    public Telefone {
        if (telefone != null) { telefone = telefone.trim(); }

        if (telefone == null || !telefone.matches("\\d{10,13}")) {
            throw new ContatoInvalidoException("Telefone.");
        }
    }
}
