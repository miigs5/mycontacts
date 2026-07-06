package com.migs.mycontacts.model;

import java.util.UUID;

import com.migs.mycontacts.exception.ContatoInvalidoException;

public class Contato {
    private UUID id;
    private Nome nome;
    private Telefone telefone;
    private Email email;
    private String descricao;

    public Contato(UUID id, Nome nome, Telefone telefone, Email email, String descricao) {
        setId(id);
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
        setDescricao(descricao);
    }

    public UUID getId() { return id; }
    public Nome getNome() { return nome; }
    public Telefone getTelefone() { return telefone; }
    public Email getEmail() { return email; }
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

    public void setTelefone(Telefone telefone) {
        if (telefone == null) {
            throw new ContatoInvalidoException("Telefone nao deve ser vazio.");
        }

        this.telefone = telefone;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setDescricao(String descricao) {
        if (descricao != null) { this.descricao = descricao.trim(); }
    }
}
