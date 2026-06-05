package com.migs.mycontacts.exception;

public class ContatoInvalidoException extends RuntimeException {
    public ContatoInvalidoException(String msg) {
        super("[ERRO] Contato Invalido: " + msg);
    }
}
