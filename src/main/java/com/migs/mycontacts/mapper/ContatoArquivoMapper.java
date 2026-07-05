package com.migs.mycontacts.mapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.migs.mycontacts.model.Contato;
import com.migs.mycontacts.model.Email;
import com.migs.mycontacts.model.Nome;
import com.migs.mycontacts.model.Telefone;

public enum ContatoArquivoMapper implements MapperPersistence<Contato, String> {
    INSTANCE;

    public String serializar(Contato contato) {
        return contato.getId().toString() + ";"
            + contato.getNome().nome() + ";"
            + contato.getTelefone().toString() + ";"
            + Optional.of(contato.getEmail()).map(Email::toString).orElse("null") + ";"
            + Optional.of(contato.getDescricao()).orElse("null");
    }

    public Contato desserializar(String str) {
        String[] campos = str.split(";");
        for (int i = 0; i < campos.length; i++) {
            if (campos[i].equals("null")) { campos[i] = null; }
        }

        return new Contato(
            UUID.fromString(campos[0]),
            new Nome(campos[1]),
            new Telefone(campos[2]),
            Optional.ofNullable(campos[3]).map(Email::new).orElse(null),
            campos[4]
        );
    }
}
