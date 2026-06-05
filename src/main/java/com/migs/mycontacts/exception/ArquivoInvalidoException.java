package com.migs.mycontacts.exception;

public class ArquivoInvalidoException extends RuntimeException {
    public ArquivoInvalidoException(String msg) {
        super("[ERRO] Arquivo Invalido: " + msg);
    }
}