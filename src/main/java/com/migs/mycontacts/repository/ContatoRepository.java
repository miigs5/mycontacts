package com.migs.mycontacts.repository;

import java.util.List;
import java.util.UUID;

import com.migs.mycontacts.model.Contato;
import com.migs.mycontacts.model.Nome;
import com.migs.mycontacts.model.Telefone;

public interface ContatoRepository extends Repository<UUID, Contato> {
    boolean existePorNomeETelefone(Nome nome, Telefone telefone);
    List<Contato> buscarPorNome(Nome nome);
}
