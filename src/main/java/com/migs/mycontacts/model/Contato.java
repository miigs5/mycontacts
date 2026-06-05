package com.migs.mycontacts.model;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.migs.mycontacts.exception.ContatoInvalidoException;

public class Contato {
    private UUID id;
    private Nome nome;
    private List<Telefone> telefones = Collections.emptyList();
    private List<Email> emails = Collections.emptyList();
    private String descricao;

    public Contato(UUID id, Nome nome, List<Telefone> telefones, List<Email> emails, String descricao) {
        setId(id);
        setNome(nome);
        setTelefones(telefones);
        setEmail(emails);
        setDescricao(descricao);
    }

    public UUID getId() { return id; }
    public Nome getNome() { return nome; }
    public List<Telefone> getTelefones() { return telefones; }
    public List<Email> getEmails() { return emails; }
    public String getDescricao() { return descricao; }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNome(Nome nome) {
        if (nome == null) {
            throw new ContatoInvalidoException("Nome nao deve ser vazio.");
        }

        this.nome = nome;
    }

    public void setTelefones(List<Telefone> telefones) {
        if (telefones == null || telefones.isEmpty()) {
            throw new ContatoInvalidoException("Telefone nao deve ser vazio.");
        }

        this.telefones = telefones;
    }

    public void setEmail(List<Email> emails) {
        if (emails == null) {
            throw new ContatoInvalidoException("Emails nao deve ser vazio.");
        }

        this.emails = emails;
    }

    public void setDescricao(String descricao) {
        if (descricao != null) {
            if (descricao.isBlank()) {
                throw new ContatoInvalidoException("Descricao deve possuir texto.");
            }

            this.descricao = descricao.trim().replace(';', ',');
        }
    }
}
