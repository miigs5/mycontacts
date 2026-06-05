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
            + contato.getTelefones().stream()
                .map(Telefone::telefone)
                .collect(Collectors.joining("/")) + ";"
            + Optional.of(contato.getEmails())
                .filter(emails -> !emails.isEmpty())
                .map(emails -> emails.stream()
                                     .map(Email::email)
                                     .collect(Collectors.joining("/")))
                .orElse("null") + ";"
            + Optional.ofNullable(contato.getDescricao()).orElse("null");
    }

    public Contato desserializar(String str) {
        String[] campos = str.split(";");
        for (int i = 0; i < campos.length; i++) {
            if (campos[i].equals("null")) { campos[i] = null; }
        }

        return new Contato(
            UUID.fromString(campos[0]),
            new Nome(campos[1]),
            Arrays.stream(campos[2].split("/")).map(Telefone::new).toList(),
            Optional.ofNullable(campos[3])
                .map(emails -> Arrays.stream(emails.split("/")).map(Email::new).toList())
                .orElse(Collections.emptyList()),
            campos[4]
        );
    }
}
