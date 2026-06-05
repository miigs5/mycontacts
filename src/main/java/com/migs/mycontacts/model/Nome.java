package com.migs.mycontacts.model;

import com.migs.mycontacts.exception.ContatoInvalidoException;

public record Nome(String nome) {
    public Nome {
        if (nome == null || !nome.matches("\\p{L}{2,}( \\p{L}{2,})*")) {
            throw new ContatoInvalidoException("Nome.");
        }
        
        nome = nome.trim().toUpperCase();
    }
}
