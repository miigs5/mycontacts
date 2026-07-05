package com.migs.mycontacts.exception;

public class SQLInvalidoException extends RuntimeException {
    public SQLInvalidoException(String message) {
        super("[ERRO] Conexao ao banco: " + message);
    }
}
