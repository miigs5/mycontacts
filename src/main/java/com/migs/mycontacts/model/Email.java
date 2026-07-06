package com.migs.mycontacts.model;

import com.migs.mycontacts.exception.ContatoInvalidoException;

public record Email(String email) {
    public Email {
        if (email != null && !email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            throw new ContatoInvalidoException("E-mail.");
        }
    }
} 